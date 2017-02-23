package insta.project.storage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class PictureRepositoryImpl implements PictureRepo{

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Picture> findByOwner(String owner) {
        Query query = em.createQuery("SELECT p from Picture p where p.owner!=?1 ORDER BY id DESC");
        query.setParameter(1, owner);

        return query.getResultList();
    }
}
