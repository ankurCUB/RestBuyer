package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CreditCardDetailsDataModel {
    @Id
    private String cardNumber;
    private String name;
    private String expirationDate;

    private int userID;

    public CreditCardDetailsDataModel(String cardNumber, String name, String expirationDate, int userID) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.expirationDate = expirationDate;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CreditCardDetailsDataModel() {

    }


    public int getUserID() {
        return userID;
    }
}
