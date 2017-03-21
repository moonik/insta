package insta.project.user.exceptions;

public class UserFollowException extends RuntimeException {
    public UserFollowException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFollowException(String message) {
        super(message);
    }

    public UserFollowException() {
    }
}