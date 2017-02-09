package insta.project.Comment;

import java.util.List;

public interface CommentRepo {

    List<Comment> findBypicture_id(Long id);
}
