package rest_buyer.RPCExceptions;

public class UnknownHostError extends RuntimeException {
    public UnknownHostError() {
        super("Unknown host. Please log in");
    }
}
