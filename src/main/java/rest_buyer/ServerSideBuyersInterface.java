package rest_buyer;

import com.example.DistributedAssignment.services.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerSideBuyersInterface implements BuyersInterface, GRPCClient {

    ShoppingCartItemServicesGrpc.ShoppingCartItemServicesBlockingStub shoppingCartItemServicesStub= ShoppingCartItemServicesGrpc.newBlockingStub(customerDBChannel);
    BuyerItemServicesGrpc.BuyerItemServicesBlockingStub buyerItemServicesStub = BuyerItemServicesGrpc.newBlockingStub(customerDBChannel);

    SaleItemServicesGrpc.SaleItemServicesBlockingStub saleItemServicesBlockingStub = SaleItemServicesGrpc.newBlockingStub(productDBChannel);

    @Override
    public String createAccount(String username, String password, String name) {
        CreateAccountRequest createAccountRequest = CreateAccountRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .setName(name)
                .setUserType(UserType.BUYER)
                .build();
        UserID value = credentialsStub.createAccount(createAccountRequest);
        return value.getUserId() + "";
    }

    @Override
    public String login(String username, String password) {
        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
        UserID value = credentialsStub.login(loginRequest);
        return value.getUserId() + "";
    }

    @Override
    public String logout(int buyerID) {
        return "{}";
    }

    @Override
    public String getSellerRating(int sellerID) {
        UserID userID = UserID.newBuilder().setUserId(sellerID).build();
        SellerRating value = getSellerRatingStub.getSellerRating(userID);
        return value.getSellerRating()+"";
    }

    @Override
    public String addItemToShoppingCart(int userID, int itemID, int quantity) {

        ItemToCartRequest itemToCartRequest = ItemToCartRequest.newBuilder()
                .setUserID(userID)
                .setItemID(itemID)
                .setQuantity(quantity)
                .build();
        shoppingCartItemServicesStub.addItemToShoppingCart(itemToCartRequest);
        return "{}";
    }

    @Override
    public String removeItemFromShoppingCart(int userID, int itemID, int quantity) {
        ItemToCartRequest itemToCartRequest = ItemToCartRequest.newBuilder()
                .setItemID(itemID)
                .setUserID(userID)
                .setQuantity(quantity)
                .build();
        shoppingCartItemServicesStub.removeItemFromShoppingCart(itemToCartRequest);
        return "{}";
    }

    @Override
    public String clearShoppingCart(int buyerID) {
        UserID userID = UserID.newBuilder()
                .setUserId(buyerID)
                .build();
        shoppingCartItemServicesStub.clearShoppingCart(userID);
        return "{}";
    }

    @Override
    public List<SaleItemPojo> displayShoppingCart(int buyerID) {
        UserID userID = UserID.newBuilder().setUserId(buyerID).build();
        Iterator<ItemToCartRequest> values = shoppingCartItemServicesStub.displayShoppingCart(userID);

        List<SaleItemPojo> saleItems = new ArrayList<>();

        while (values.hasNext()) {
            ItemToCartRequest itemToCartRequest = values.next();

            SaleItem value = saleItemServicesBlockingStub.getItemDetails(
                    ItemID.newBuilder()
                            .setItemId(itemToCartRequest.getItemID())
                            .build());
            SaleItemPojo saleItem = new SaleItemPojo();
            saleItem.setSellerID(value.getSellerID());
            saleItem.setItemID(value.getItemID());
            saleItem.setItemName(value.getItemName());
            saleItem.setItemPrice(value.getItemPrice());
            saleItem.setQuantity(value.getQuantity());
            saleItem.setIsNew(value.getIsNew());
            saleItem.setKeyWords(value.getKeyWords());
            saleItems.add(saleItem);
        }

        return saleItems;
    }

    @Override
    public String makePurchase(int userID) {
        List<SaleItemPojo> saleItems = displayShoppingCart(userID);
        clearShoppingCart(userID);
        for (SaleItemPojo saleItem: saleItems) {
            RemoveItemFromSaleRequest removeItemFromSaleRequest = RemoveItemFromSaleRequest
                    .newBuilder()
                    .setItemID(saleItem.getItemID())
                    .setSellerID(saleItem.getSellerID())
                    .setQuantity(saleItem.getQuantity())
                    .build();
            saleItemServicesBlockingStub.removeItemFromSale(removeItemFromSaleRequest);
            PurchaseHistoryResponse request = PurchaseHistoryResponse.newBuilder()
                    .setTimestamp(System.currentTimeMillis())
                    .setItemID(saleItem.getItemID())
                    .setQuantity(saleItem.getQuantity())
                    .setUserID(userID)
                    .build();
            buyerItemServicesStub.addToPurchaseHistory(request);
        }

        return "{}";
    }

    @Override
    public List<PurchaseHistoryPojo> getBuyerPurchaseHistory(int buyerID) {
        UserID userID = UserID.newBuilder().setUserId(buyerID).build();
        Iterator<PurchaseHistoryResponse> values = buyerItemServicesStub.getBuyerPurchaseHistory(userID);
        List<PurchaseHistoryPojo> purchaseHistoryPojos = new ArrayList<>();
        while (values.hasNext()){
            PurchaseHistoryResponse value = values.next();
            PurchaseHistoryPojo purchaseHistoryPojo = new PurchaseHistoryPojo();
            purchaseHistoryPojo.setItemID(value.getItemID());
            purchaseHistoryPojo.setPurchaseID(value.getPurchaseID());
            purchaseHistoryPojo.setFeedback(value.getFeedback());
            purchaseHistoryPojo.setTimestamp(value.getTimestamp());
            purchaseHistoryPojo.setQuantity(value.getQuantity());
            purchaseHistoryPojos.add(purchaseHistoryPojo);
        }
        return purchaseHistoryPojos;
    }

    @Override
    public String provideFeedback(int purchaseID,int itemID, int feedback) {
        UserID sellerID = saleItemServicesBlockingStub.getSellerIDForItem(ItemID.newBuilder().setItemId(itemID).build());
        FeedbackRequest feedbackRequest = FeedbackRequest.newBuilder()
                .setPurchaseID(purchaseID)
                .setSellerId(sellerID.getUserId())
                .setLikeOrDislike(feedback)
                .build();
        buyerItemServicesStub.provideFeedback(feedbackRequest);
        return "{}";
    }

    @Override
    public List<SaleItemPojo> searchItemsForSale(int category, String keywords) {
        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setCategory(category)
                .setKeywords(keywords)
                .build();
        Iterator<SaleItem> values = saleItemServicesBlockingStub.searchItemsForSale(searchRequest);
        return Utils.getSaleItemsForRestResponse(values);
    }

    public void addSession(int userID, String hostname){
        SessionData sessionData = SessionData.newBuilder().setHostname(hostname).setUserId(userID).build();
        credentialsStub.addSession(sessionData);
    }

    public String getSession(int userID, String hostname){
        SessionData sessionData = SessionData.newBuilder().setHostname(hostname).setUserId(userID).build();
        return credentialsStub.getSession(sessionData).getHostname();
    }

    public void removeSession(int userID, String hostname){
        SessionData sessionData = SessionData.newBuilder().setHostname(hostname).setUserId(userID).build();
        credentialsStub.removeSession(sessionData);
    }

}
