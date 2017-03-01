package insta.project.storage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class PictureRepositoryImpl implements PictureRepo{

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Picture> findPictures(String currentUser) {
        Query query = em.createQuery("SELECT p from Picture p where p.owner!=?1 ORDER BY id DESC");
        query.setParameter(1, currentUser);

        return query.getResultList();
    }

    @Override
    public List<Picture> findByFollowing(String owner) {
        Query query = em.createQuery("SELECT p from Picture p where p.owner=?1 ORDER BY id DESC");
        query.setParameter(1, owner);

        return query.getResultList();
    }

    @Override
    public List<Picture> findSavedPictures(String currentUser) {
        Query query = em.createQuery("SELECT p from Picture p where p.owner=?1 AND p.name LIKE 'saved%' ORDER BY id DESC");
        query.setParameter(1, currentUser);

        return query.getResultList();
    }

}
