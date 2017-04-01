package insta.project.picture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Роман on 27.01.2017.
 */
@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    /**
     * searches pictures by owner
     * @param owner - picture owner
     * @return list of pictures
     */
    List<Picture> findAllByOwnerOrderByIdDesc(String owner);
    List<Picture> findById(Long id);


}
