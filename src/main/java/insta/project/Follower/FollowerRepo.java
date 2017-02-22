package insta.project.Follower;

import java.util.List;

public interface FollowerRepo {

    List<Follower> findFollowersByName(String currentUser);
    List<Follower> findFollowingByName(String currentUser);

    Boolean ifFollowed(String currentUser, String following);
}
