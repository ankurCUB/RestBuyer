package rest_buyer;

import java.util.List;

public interface BuyersInterface {
    String createAccount(String username, String password, String name);
    String login(String username, String password);
    String  logout(int sellerID);
    String getSellerRating(int sellerID);
    String addItemToShoppingCart(int userID, int itemID, int quantity);
    String removeItemFromShoppingCart(int userID, int itemID, int quantity);
    String clearShoppingCart(int buyerID);
    List<SaleItemPojo> displayShoppingCart(int buyerID);
    String makePurchase(int userID);
    List<PurchaseHistoryPojo> getBuyerPurchaseHistory(int buyerID);
    String provideFeedback(int purchaseID, int itemID, int feedback);
    List<SaleItemPojo> searchItemsForSale(int category, String keywords);
}