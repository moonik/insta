package insta.project.comment;

import java.util.List;

public interface CommentRepo {

    /**
     * searches comments for picture
     * @param id picture id
     * @return comments
     */
    List<Comment> findBypicture_id(Long id);

    /**
     * searches new comments
     * @param id picture id
     * @param commentId last comment
     * @return list of new comments
     */
    List<Comment> findNewComments(Long id, Long commentId);

    /**
     * checks if there are new comments
     * @param id picture id
     * @param commentId last comment id
     * @return false if there wasn't new comments true if there were new comments
     */
    Boolean checkIfNewComment(Long id, Long commentId);
}
