package insta.project.Comment;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Роман on 03.02.2017.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

//    @OneToOne
//    private Account owner;

    private String content;
    private String owner;
    private Long picture_id;

    public Comment() {

    }

    public Comment(String content, String owner, Long picture_id) {
        this.content = content;
        this.owner = owner;
        this.picture_id = picture_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Account getOwner() {
//        return owner;
//    }
//
//    public void setOwner(Account owner) {
//        this.owner = owner;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
