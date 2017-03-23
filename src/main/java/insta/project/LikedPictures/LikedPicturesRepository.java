package insta.project.LikedPictures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedPicturesRepository extends JpaRepository<LikedPictures, Long> {

    /**
     * searches picture by owner
     * @param currentUser - logged user
     * @return list of pictures
     */
    List<LikedPictures> findAllByOwnerOrderByIdDesc(String currentUser);

}
