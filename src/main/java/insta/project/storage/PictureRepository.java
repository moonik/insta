package insta.project.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Роман on 27.01.2017.
 */
@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    List<Picture> findAllByOwnerOrderByIdDesc(String owner);


}
