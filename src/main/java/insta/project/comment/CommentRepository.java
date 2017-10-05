package insta.project.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Роман on 03.02.2017.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("Select c from Comment c where c.id = ?1")
    void deleteComment(Long commentId);

    @Query("SELECT c from Comment c where c.picture_id=?1")
    List<Comment> findBypicture_id(Long id);

    @Query("SELECT c from Comment c where c.picture_id=?1 AND c.id>?2 ORDER BY id DESC")
    List<Comment> findNewComments(Long id, Long commentId);
}
