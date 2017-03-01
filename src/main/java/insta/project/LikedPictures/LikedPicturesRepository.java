package insta.project.LikedPictures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedPicturesRepository extends JpaRepository<LikedPictures, Long> {

    List<LikedPictures> findAllByOwnerOrderByIdDesc(String currentUser);

}
