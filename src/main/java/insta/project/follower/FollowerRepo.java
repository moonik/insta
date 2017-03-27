package insta.project.follower;

import java.util.List;

public interface FollowerRepo {

    /**
     * searches followers for logged user
     * @param currentUser - logged user
     * @return followers
     */
    List<Follower> findFollowersByName(String currentUser);

    /**
     * searches followings for logged user
     * @param currentUser - logged user
     * @return followings
     */
    List<Follower> findFollowingByName(String currentUser);

    /**
     * checks if you're already following user
     * @param currentUser - logged user
     * @param following - user that you want to follow
     * @return true if you're already following user false if you're not
     */
    Boolean ifFollowed(String currentUser, String following);
}
