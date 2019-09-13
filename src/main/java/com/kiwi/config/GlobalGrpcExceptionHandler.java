package com.kiwi.config;

import io.grpc.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@GRpcGlobalInterceptor
public  class GlobalGrpcExceptionHandler implements ServerInterceptor {
    private final Log LOGGER = LogFactory.getLog(getClass());

    public static final Metadata.Key<String> TRACE_ID_KEY = Metadata.Key.of("traceId", ASCII_STRING_MARSHALLER);

    /**
     * Intercept {@link ServerCall} dispatch by the {@code next} {@link ServerCallHandler}. General
     * semantics of {@link ServerCallHandler#startCall} apply and the returned
     * {@link ServerCall.Listener} must not be {@code null}.
     *
     * <p>If the implementation throws an exception, {@code call} will be closed with an error.
     * Implementations must not throw an exception if they started processing that may use {@code
     * call} on another thread.
     *
     * @param call    object to receive response messages
     * @param headers which can contain extra call metadata from {@link ClientCall#start},
     *                e.g. authentication credentials.
     * @param next    next processor in the interceptor chain
     * @return listener for processing incoming messages for {@code call}, never {@code null}.
     */
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        ServerCall.Listener<ReqT> delegate = next.startCall(call, headers);
        String traceId = headers.get(TRACE_ID_KEY);
        LOGGER.warn("traceId from client: " + traceId + ". TODO: Add traceId to sleuth ");

        GrpcServerCall grpcServerCall = new GrpcServerCall(call);

        ServerCall.Listener listener = next.startCall(grpcServerCall, headers);

        return new GrpcForwardingServerCallListener<ReqT>(call.getMethodDescriptor(), listener) {
            @Override
            public void onMessage(ReqT message) {
                LOGGER.info("Method: " + methodName + ", Message: " + message);
                super.onMessage(message);
            }
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception e) {
                    call.close(Status.INTERNAL
                            .withDescription(e.getMessage()), new Metadata());
                }
            }
        };
    }

    private class GrpcServerCall<M, R> extends ServerCall<M, R> {

        ServerCall<M, R> serverCall;

        protected GrpcServerCall(ServerCall<M, R> serverCall) {
            this.serverCall = serverCall;
        }

        @Override
        public void request(int numMessages) {
            serverCall.request(numMessages);
        }

        @Override
        public void sendHeaders(Metadata headers) {
            serverCall.sendHeaders(headers);
        }

        @Override
        public void sendMessage(R message) {
            LOGGER.info("Method: " + serverCall.getMethodDescriptor().getFullMethodName() + ", Response: " + message);
            serverCall.sendMessage(message);
        }

        @Override
        public void close(Status status, Metadata trailers) {
            serverCall.close(status, trailers);
        }

        @Override
        public boolean isCancelled() {
            return serverCall.isCancelled();
        }

        @Override
        public MethodDescriptor<M, R> getMethodDescriptor() {
            return serverCall.getMethodDescriptor();
        }
    }

    private class GrpcForwardingServerCallListener<M> extends io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener<M> {

        String methodName;

        protected GrpcForwardingServerCallListener(MethodDescriptor method, ServerCall.Listener<M> listener) {
            super(listener);
            methodName = method.getFullMethodName();
        }
    }
}
