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
    private String owner;
    private String comment;

    public  Picture(){

    }

    public Picture(String name, String owner, String token) {
        this.name = name;
        this.owner = owner;
        this.token = token;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
