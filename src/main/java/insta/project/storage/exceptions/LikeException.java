package insta.project.storage.exceptions;

public class LikeException extends RuntimeException {
    public LikeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LikeException(String message) {
        super(message);
    }

    public LikeException() {
    }
}
