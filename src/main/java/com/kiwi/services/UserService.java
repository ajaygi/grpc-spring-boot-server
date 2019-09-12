package com.kiwi.services;

import com.kiwi.grpc.User.LoginRequest;
import com.kiwi.grpc.User.Response;
import com.kiwi.grpc.userGrpc.userImplBase;
import io.grpc.stub.StreamObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lognet.springboot.grpc.GRpcService;

import java.util.logging.Logger;

@GRpcService
public class UserService extends userImplBase {

    private final Log LOGGER = LogFactory.getLog(getClass());

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void login(LoginRequest request, StreamObserver<Response> responseObserver) {
//        super.login(request, responseObserver);

        LOGGER.info("Inside login method...");

        System.out.println("Username :: " + request.getUsername());
        System.out.println("Password :: " + request.getPassword());

        Response.Builder response = Response.newBuilder();
        response.setResult("Sucess....!");

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
