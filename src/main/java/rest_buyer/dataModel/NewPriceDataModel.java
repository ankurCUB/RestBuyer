package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NewPriceDataModel {
    @Id
    private int sellerID;
    private int itemID;

    public NewPriceDataModel(int sellerID, int itemID, float newPrice) {
        this.sellerID = sellerID;
        this.itemID = itemID;
        this.newPrice = newPrice;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }

    private float newPrice;

    public NewPriceDataModel() {

    }
}
