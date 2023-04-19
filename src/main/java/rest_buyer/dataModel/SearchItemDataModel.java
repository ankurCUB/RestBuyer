package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SearchItemDataModel {
    @Id
    @GeneratedValue
    int category;
    String keywords;



    public SearchItemDataModel(int category, String keywords) {

        this.category = category;
        this.keywords = keywords;
    }

    public SearchItemDataModel() {

    }

    public int getCategory() {
        return category;
    }

    public String getKeywords() {
        return keywords;
    }
}
