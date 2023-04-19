package rest_buyer;

import com.example.DistributedAssignment.services.CredentialsGrpc;
import com.example.DistributedAssignment.services.GetSellerRatingGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import static rest_buyer.Utils.CUSTOMER_DB_PORT;
import static rest_buyer.Utils.PRODUCT_DB_PORT;

public interface GRPCClient {

    String address = "34.106.248.248";
    ManagedChannel customerDBChannel = ManagedChannelBuilder.forAddress(address, CUSTOMER_DB_PORT).usePlaintext().build();;
    ManagedChannel productDBChannel = ManagedChannelBuilder.forAddress("localhost", PRODUCT_DB_PORT).usePlaintext().build();
    CredentialsGrpc.CredentialsBlockingStub credentialsStub = CredentialsGrpc.newBlockingStub(customerDBChannel);
    GetSellerRatingGrpc.GetSellerRatingBlockingStub getSellerRatingStub = GetSellerRatingGrpc.newBlockingStub(customerDBChannel);
}
