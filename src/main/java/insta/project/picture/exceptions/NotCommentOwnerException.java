package insta.project.picture.exceptions;

public class NotCommentOwnerException extends RuntimeException{
    public NotCommentOwnerException(){}
    public NotCommentOwnerException(String message){
        super(message);
    }
}
