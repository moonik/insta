package insta.project.Follower;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class FollowerRepositoryImpl implements FollowerRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Follower> findFollowersByName(String currentUser) {
        Query query = em.createQuery("SELECT fr from Follower fr where fr.following=?1");
        query.setParameter(1, currentUser);
        List<Follower> followers = query.getResultList();
        return followers;
    }

    @Override
    public List<Follower> findFollowingByName(String currentUser) {
        Query query = em.createQuery("SELECT fr from Follower fr where fr.follower=?1");
        query.setParameter(1, currentUser);
        List<Follower> following = query.getResultList();
        return following;
    }

    @Override
    public Boolean ifFollowed(String currentUser, String following) {
        Query query = em.createQuery("SELECT fr from Follower fr where fr.follower=?1 AND fr.following=?2");
        query.setParameter(1, currentUser);
        query.setParameter(2, following);
        List<Follower> followers = query.getResultList();
        if (followers.size() != 0) {
            return true;
        } else {
            return false;
        }
    }
}
