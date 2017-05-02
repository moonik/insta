package insta.project.user.exceptions;

public class UserNotExistsFoundException extends RuntimeException {
    public UserNotExistsFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotExistsFoundException(String message) {
        super(message);
    }

    public UserNotExistsFoundException() {
    }
}