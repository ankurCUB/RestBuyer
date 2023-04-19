package rest_buyer.RPCExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SellerNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(SellerNotFoundError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String sellerNotFoundHandler(SellerNotFoundError sellerNotFoundError){
        return sellerNotFoundError.getMessage();
    }
}
