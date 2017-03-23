package insta.project.storage;

/**
 * Created by Роман on 27.01.2017.
 */

import insta.project.Like.PictureLikes;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Picture {

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat
    private Date date;

    private String name; // picture name
    private String token; // picture path
    private String owner; // picture owner

    @OneToMany(mappedBy = "picture_id")
    @Cascade(CascadeType.DELETE)
    private List<PictureLikes> pictureLikes; // picture likes

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

    public Picture(Date date, String name, String token, String owner) {
        this.date = date;
        this.name = name;
        this.token = token;
        this.owner = owner;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
