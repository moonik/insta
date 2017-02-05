package tutorial.Test.Comment;


/**
 * Created by Роман on 03.02.2017.
 */
public class CommentDTO {

    private String content;

    //private Account owner;

    public CommentDTO(){

    }

    public CommentDTO(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public Account getOwner() {
//        return owner;
//    }
//
//    public void setOwner(Account owner) {
//        this.owner = owner;
//    }
}
