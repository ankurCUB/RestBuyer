package rest_buyer.RPCExceptions;

public class SellerNotFoundError extends RuntimeException {
    public SellerNotFoundError(Long sellerID) {
        super("Couldn't find seller "+sellerID);
    }
}
