package insta.project.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OnlineUsers {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private UserAccount userId;

    public OnlineUsers(){}

    public OnlineUsers(UserAccount userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getUserId() {
        return userId;
    }

    public void setUserId(UserAccount userId) {
        this.userId = userId;
    }
}
