package tutorial.Test.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Роман on 27.01.2017.
 */
@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}
