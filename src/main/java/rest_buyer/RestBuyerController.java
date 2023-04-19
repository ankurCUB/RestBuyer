package rest_buyer;

import com.example.consumingwebservice.wsdl.CreditCardDetails;
import com.example.consumingwebservice.wsdl.TransactionResponse;
import com.example.consumingwebservice.wsdl.TransactionsStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rest_buyer.RPCExceptions.SellerNotFoundError;
import rest_buyer.RPCExceptions.UnknownHostError;
import rest_buyer.dataModel.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RestBuyerController {
    private final ServerSideBuyersInterface serverSideBuyersInterface;

    RestBuyerController() {
        serverSideBuyersInterface = new ServerSideBuyersInterface();
    }

    @PostMapping("/buyers")
    UserIDDataModel newSellerAccount(@RequestBody CreateAccountDataModel createAccountDataModel) {
        int userID = Integer.parseInt(serverSideBuyersInterface.createAccount(
                createAccountDataModel.getUsername(),
                createAccountDataModel.getPassword(),
                createAccountDataModel.getName()));
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            serverSideBuyersInterface.addSession(userID, request.getRemoteHost());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new UserIDDataModel(userID);
    }

    @PostMapping("/buyers/login")
    UserIDDataModel login(@RequestBody LoginDataModel loginDataModel) {
        int userID = Integer.parseInt(serverSideBuyersInterface.login(
                loginDataModel.getUsername(),
                loginDataModel.getPassword()
        ));
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            serverSideBuyersInterface.addSession(userID, request.getRemoteHost());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new UserIDDataModel(userID);
    }

    @PostMapping("/buyers/logout/{userID}")
    void logout(@PathVariable int userID) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            serverSideBuyersInterface.removeSession(userID, request.getRemoteHost());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @GetMapping("/rating/{sellerID}")
    SellerRatingDataModel sellerRating(@PathVariable Long sellerID) {
        float rating = Float.parseFloat(serverSideBuyersInterface.getSellerRating(sellerID.intValue()));
        if (rating == -1) {
            throw new SellerNotFoundError(sellerID);
        } else {
            return new SellerRatingDataModel(sellerID, rating);
        }
    }

    @PostMapping("/shoppingcart")
    void addItemToShoppingCart(@RequestBody ShoppingCartDataModel shoppingCartDataModel) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String hostName = serverSideBuyersInterface.getSession(shoppingCartDataModel.getUserID(), request.getRemoteHost());
            if(hostName.isEmpty()){
                throw new UnknownHostError();
            } else {
                serverSideBuyersInterface.addItemToShoppingCart(shoppingCartDataModel.getUserID(),
                        shoppingCartDataModel.getItemID(),
                        shoppingCartDataModel.getQuantity());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @DeleteMapping("/shoppingcart")
    void deleteItemFromShoppingCart(@RequestBody ShoppingCartDataModel shoppingCartDataModel) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String hostName = serverSideBuyersInterface.getSession(shoppingCartDataModel.getUserID(), request.getRemoteHost());
            if(hostName.isEmpty()){
                throw new UnknownHostError();
            } else {
                serverSideBuyersInterface.removeItemFromShoppingCart(shoppingCartDataModel.getUserID(),
                        shoppingCartDataModel.getItemID(), shoppingCartDataModel.getQuantity());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @DeleteMapping("/shoppingcart/{userID}")
    void clearShoppingCart(@PathVariable int userID) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String hostName = serverSideBuyersInterface.getSession(userID, request.getRemoteHost());
            if (hostName.isEmpty()) {
                throw new UnknownHostError();
            } else {
                serverSideBuyersInterface.clearShoppingCart(userID);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @GetMapping("/shoppingcart/{userID}")
    List<SaleItemPojo> displayShoppingCart(@PathVariable Long userID) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String hostName = serverSideBuyersInterface.getSession(userID.intValue(), request.getRemoteHost());
            if (hostName.isEmpty()) {
                throw new UnknownHostError();
            } else {
                return serverSideBuyersInterface.displayShoppingCart(userID.intValue());
            }
    }

    @GetMapping("/items/search")
    List<SaleItemPojo> searchForItemsOnSale(@RequestBody SearchItemDataModel searchItemDataModel){
        return serverSideBuyersInterface.searchItemsForSale(searchItemDataModel.getCategory(), searchItemDataModel.getKeywords());
    }

    @GetMapping("/buyers/purchase_history/{userID}")
    List<PurchaseHistoryPojo> getPurchaseHistory(@PathVariable int userID) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String hostName = serverSideBuyersInterface.getSession(userID, request.getRemoteHost());
        if (hostName.isEmpty()) {
            throw new UnknownHostError();
        } else {
            return serverSideBuyersInterface.getBuyerPurchaseHistory(userID);
        }
    }

    @PostMapping("/buyers/feedback/{purchaseID}/{itemID}/{feedback}")
    void provideFeedback(@PathVariable int feedback, @PathVariable int purchaseID, @PathVariable int itemID) {
        serverSideBuyersInterface.provideFeedback(purchaseID, itemID, feedback);
    }

    @PostMapping("buyers/transaction")
    void makePurchase(@RequestBody CreditCardDetailsDataModel creditCardDetailsDataModel) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String hostName = serverSideBuyersInterface.getSession(creditCardDetailsDataModel.getUserID(), request.getRemoteHost());
        if (hostName.isEmpty()) {
            throw new UnknownHostError();
        } else {
            CreditCardDetails details = new CreditCardDetails();
            details.setName(creditCardDetailsDataModel.getName());
            details.setNumber(creditCardDetailsDataModel.getCardNumber());
            details.setExpirationDate(creditCardDetailsDataModel.getExpirationDate());
            TransactionsClient client = new TransactionsClient();
            TransactionResponse response = client.sendTransactionRequest(details);
            if (response.getTransactionStatus() == TransactionsStatus.YES) {
                serverSideBuyersInterface.makePurchase(creditCardDetailsDataModel.getUserID());
            }
        }
    }

}
