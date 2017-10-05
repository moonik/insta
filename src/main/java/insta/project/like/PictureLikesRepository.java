package insta.project.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PictureLikesRepository extends JpaRepository<PictureLikes, Long> {

    @Query("SELECT pl from PictureLikes pl where pl.picture_id=?1")
    List<PictureLikes> findBypicture_id(Long id);

    @Query("SELECT pl from PictureLikes pl where pl.picture_id=?1 AND pl.owner=?2")
    PictureLikes findByPicIdAndOwner(Long id, String owner);
}
