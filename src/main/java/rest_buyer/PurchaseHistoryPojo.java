package rest_buyer;

public class PurchaseHistoryPojo {
    int itemID;
    int quantity;
    long timestamp;
    int purchaseID;
    float feedback;

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

    public float getFeedback() {
        return feedback;
    }

    public void setFeedback(float feedback) {
        this.feedback = feedback;
    }
}
