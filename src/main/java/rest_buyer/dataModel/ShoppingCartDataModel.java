package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ShoppingCartDataModel {
    @Id
    int userID;

    public ShoppingCartDataModel(int userID, int itemID, int quantity) {
        this.userID = userID;
        this.itemID = itemID;
        this.quantity = quantity;
    }

    int itemID;
    int quantity;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ShoppingCartDataModel() {

    }
}
