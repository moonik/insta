package tutorial.Test.user.model;


import tutorial.Test.user.domain.UserAccount;

public class UserDTO {

    private final UserAccount user;

    private Boolean isMyself = null;

    public UserDTO(UserAccount user) {
        this.user = user;
    }

    public long getId() {
        return user.getId();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public Boolean getMyself() {
        return isMyself;
    }

    public void setMyself(Boolean myself) {
        isMyself = myself;
    }
}
