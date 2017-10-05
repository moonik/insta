package insta.project.comment;

import java.util.List;

public interface CommentRepo {
    /**
     * checks if there are new comments
     * @param id picture id
     * @param commentId last comment id
     * @return false if there wasn't new comments true if there were new comments
     */
    Boolean checkIfNewComment(Long id, Long commentId);
}
