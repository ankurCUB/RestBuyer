package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PurchaseHistoryDataModel {
    @Id@GeneratedValue
    int itemID;
    int quantity;
    long timestamp;
    int purchaseID;
    int feedback;

    public PurchaseHistoryDataModel(int itemID, int quantity, long timestamp, int purchaseID, int feedback) {
        this.itemID = itemID;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.purchaseID = purchaseID;
        this.feedback = feedback;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public PurchaseHistoryDataModel() {

    }
}
