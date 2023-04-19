package rest_buyer.dataModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoginDataModel {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    private String username;
    private String password;

    public LoginDataModel(String username, String password){
        this.username = username;
        this.password = password;
    }

    public LoginDataModel() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
