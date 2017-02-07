package insta.project.Comment;


/**
 * Created by Роман on 03.02.2017.
 */
public class CommentDTO {

    private String content;
    private String owner;
    private Long picture_id;

    //private Account owner;

    public CommentDTO(){

    }

    public CommentDTO(String content, String owner, Long picture_id) {
        this.content = content;
        this.owner = owner;
        this.picture_id = picture_id;
    }

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
