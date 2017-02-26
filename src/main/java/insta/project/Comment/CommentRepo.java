package insta.project.Comment;

import java.util.List;

public interface CommentRepo {

    List<Comment> findBypicture_id(Long id);
    Comment findNewComments(Long id, Comment lastComment);
    Comment findLastComment(Long id);
    Boolean checkIfNewComment(Long id, Comment lastComment);
}
