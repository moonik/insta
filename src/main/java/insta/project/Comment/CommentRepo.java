package insta.project.Comment;

import java.util.List;

public interface CommentRepo {

    List<Comment> findBypicture_id(Long id);
    List<Comment> findNewComments(Long id, Long commentId);
    Boolean checkIfNewComment(Long id, Long commentId);
}
