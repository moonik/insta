package insta.project.LikedPictures;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class LikedPicturesRepositoryImpl implements LikedPicturesRepo{


    @PersistenceContext
    private EntityManager em;

    @Override
    public LikedPictures findByPicIdAndOwner(Long id, String owner) {

        Query query = em.createQuery("SELECT pl from LikedPictures pl where pl.picture_id=?1 AND pl.owner=?2");
        query.setParameter(1, id);
        query.setParameter(2, owner);
        List<LikedPictures> pictureLikes = query.getResultList();
        return pictureLikes.get(0);
    }

}
