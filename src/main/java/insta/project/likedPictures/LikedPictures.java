package insta.project.likedPictures;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class LikedPictures {

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat
    private Date date;

    private String token; // picture path
    private String owner; // picture owner
    private Long picture_id;

    public LikedPictures(){}

    public LikedPictures(Date date, String token, String owner, Long picture_id) {
        this.date = date;
        this.token = token;
        this.owner = owner;
        this.picture_id = picture_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(Long picture_id) {
        this.picture_id = picture_id;
    }
}


