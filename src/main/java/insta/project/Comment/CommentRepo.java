package insta.project.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Роман on 03.02.2017.
 */
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
