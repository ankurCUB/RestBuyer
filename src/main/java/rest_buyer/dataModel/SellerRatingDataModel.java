package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SellerRatingDataModel {
    @Id
    private Long id;

    public SellerRatingDataModel() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private float rating;

    public SellerRatingDataModel(Long id, float rating) {
        this.id = id;
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
