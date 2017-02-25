package insta.project.Follower;

public class FollowerDTO {

    private String follower;
    private String following;

    public FollowerDTO() {
    }

    public FollowerDTO(String follower, String following) {
        this.follower = follower;
        this.following = following;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
