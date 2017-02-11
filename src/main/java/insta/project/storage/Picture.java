package insta.project.storage;

/**
 * Created by Роман on 27.01.2017.
 */

import insta.project.Like.PictureLikes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Picture {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String token;
    private String owner;
    private String comment;

    @OneToMany(mappedBy = "picture_id")
    private List<PictureLikes> pictureLikes;

    public  Picture(){

    }

    public Picture(String name, String owner, String token) {
        this.name = name;
        this.token = token;
        this.owner = owner;
    }

    public Picture(String name, String token, String owner, List<PictureLikes> pictureLikes) {
        this.name = name;
        this.token = token;
        this.owner = owner;
        this.pictureLikes = pictureLikes;
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

    public List<PictureLikes> getPictureLikes() {
        return pictureLikes;
    }

    public void setPictureLikes(List<PictureLikes> pictureLikes) {
        this.pictureLikes = pictureLikes;
    }
}
