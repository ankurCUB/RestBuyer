package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CreateAccountDataModel {
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    private @Id @GeneratedValue Long id;
    private String username;
    private String password;
    private String name;

    public CreateAccountDataModel(String username, String password, String name){
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public CreateAccountDataModel() {

    }
}
