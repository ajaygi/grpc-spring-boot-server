package com.kiwi.config;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;

//@GRpcGlobalInterceptor
//public  class MyInterceptor implements ServerInterceptor {
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
//    @Override
//    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
//        return null;
//    }
//    // ommited
//}
