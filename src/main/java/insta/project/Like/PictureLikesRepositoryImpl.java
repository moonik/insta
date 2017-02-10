package insta.project.Like;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class PictureLikesRepositoryImpl implements PictureLikesRepo {

        @PersistenceContext
        private EntityManager em;

        public List<PictureLikes> findBypicture_id(Long id){

            Query query = em.createQuery("SELECT pl from PictureLikes pl where pl.picture_id=?1");
            query.setParameter(1, id);
            return query.getResultList();

        }


}
