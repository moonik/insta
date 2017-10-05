package insta.project.like;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PictureLikesRepositoryImpl implements PictureLikesRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean findByPicId(Long id, String owner) {
        Query query = em.createQuery("SELECT pl from PictureLikes pl where pl.picture_id=?1 AND pl.owner=?2");
        query.setParameter(1, id);
        query.setParameter(2, owner);
        List<PictureLikes> pictureLikes = query.getResultList();
        return pictureLikes.size() != 0;
    }
}
