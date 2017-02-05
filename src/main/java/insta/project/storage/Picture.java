package insta.project.storage;

/**
 * Created by Роман on 27.01.2017.
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Picture {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String token;

    public  Picture(){

    }

    public Picture(String name, String token) {
        this.name = name;
        this.token = token;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
