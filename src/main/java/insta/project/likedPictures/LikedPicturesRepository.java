package insta.project.likedPictures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT pl from likedPictures pl where pl.picture_id=?1 AND pl.owner=?2")
    LikedPictures findByPicIdAndOwner(Long id, String owner);
}
