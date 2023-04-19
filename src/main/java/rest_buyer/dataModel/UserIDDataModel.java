package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserIDDataModel {

    public UserIDDataModel(int id){
        this.id = id;
    }
    @Id
    private int id;

    public UserIDDataModel() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
