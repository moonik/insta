package insta.project.follower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerService {

    @Autowired
    private FollowerRepo followerRepo;

    @Autowired
    private FollowerRepository followerRepository;

    public Follower saveFollowers(FollowerDTO followerDTO) {

        Follower follower = new Follower(followerDTO.getFollower(), followerDTO.getFollowing());

        return followerRepository.save(follower);
    }

}
