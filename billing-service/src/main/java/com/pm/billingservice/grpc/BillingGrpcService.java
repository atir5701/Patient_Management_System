package com.pm.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {
    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
                                     StreamObserver<billing.BillingResponse> responseStreamObserver){
        System.out.println("Request Received for the billing account creation");

        BillingResponse res = BillingResponse.newBuilder().setAccountId("123").setStatus("Active").build();
        responseStreamObserver.onNext(res);
        responseStreamObserver.onCompleted();
    }
}
