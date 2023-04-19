package rest_buyer;

import com.example.consumingwebservice.wsdl.CreditCardDetails;
import com.example.consumingwebservice.wsdl.TransactionRequest;
import com.example.consumingwebservice.wsdl.TransactionResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class TransactionsClient extends WebServiceGatewaySupport {

    private TransactionResponse processTransaction(CreditCardDetails cardDetails, String uri){
        TransactionRequest request = new TransactionRequest();
        request.setCreditCardDetails(cardDetails);
        TransactionResponse response = (TransactionResponse) getWebServiceTemplate().marshalSendAndReceive(
                uri,
                request,
                new SoapActionCallback("http://spring.io/guides/transactions/TransactionRequest")
        );
        return response;
    }

    public Jaxb2Marshaller getJaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.consumingwebservice.wsdl");
        return marshaller;
    }

    public TransactionResponse sendTransactionRequest(CreditCardDetails creditCardDetails){
        TransactionsClient client = new TransactionsClient();
        String uri = "http://localhost:8083/ws";
        client.setDefaultUri(uri);
        client.setMarshaller(client.getJaxb2Marshaller());
        client.setUnmarshaller(client.getJaxb2Marshaller());
        return client.processTransaction(creditCardDetails, uri);
    }


}
