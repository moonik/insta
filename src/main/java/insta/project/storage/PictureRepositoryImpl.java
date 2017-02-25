package insta.project.storage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
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
    public List<Picture> findById(List<Long>id) {
        List<Picture> pictures = new ArrayList<>();
        Collections.sort(id);
        Collections.reverse(id);
        for(int i = 0; i < id.size(); i++)
        {
            Query query = em.createQuery("SELECT p from Picture p where p.id=?1");
            query.setParameter(1, id.get(i));
            pictures.addAll(query.getResultList());
        }
        return pictures;
    }

}
