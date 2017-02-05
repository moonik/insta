package tutorial.Test.Comment;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

    public Comment(){

    }

    public Comment(String content)
    {
        this.content = content;
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
}
