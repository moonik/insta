package insta.project.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Роман on 03.02.2017.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("Select c from Comment c where c.id = ?1")
    void deleteComment(Long commentId);
}
