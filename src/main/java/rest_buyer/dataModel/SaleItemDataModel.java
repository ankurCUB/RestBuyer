package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SaleItemDataModel {
    private String itemName;
    private int category;
    private String keyWords;
    private int isNew;
    private float itemPrice;
    private int sellerID;
    private int quantity;

    @Id
    private int itemID;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public SaleItemDataModel(String itemName, int category, String keyWords, int isNew, float itemPrice, int sellerID, int quantity, int itemID) {
        this.itemName = itemName;
        this.category = category;
        this.keyWords = keyWords;
        this.isNew = isNew;
        this.itemPrice = itemPrice;
        this.sellerID = sellerID;
        this.quantity = quantity;
        this.itemID = itemID;
    }

    public SaleItemDataModel() {

    }
}
