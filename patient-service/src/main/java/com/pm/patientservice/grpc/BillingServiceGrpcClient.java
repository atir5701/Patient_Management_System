package com.pm.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub stub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ){
        System.out.println("Connecting to Billing Service");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,serverPort)
                .usePlaintext().build();
        stub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId,String name,String email){
        BillingRequest request = BillingRequest.newBuilder().setEmail(email).setName(name).setPatientId(patientId).build();
        BillingResponse response = stub.createBillingAccount(request);
        System.out.println("Account Created");
        return response;
    }
}
