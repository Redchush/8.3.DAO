package root.dao.exception;

public class NotUniqueUserEmailException extends DaoException {
    public NotUniqueUserEmailException() {}

    public NotUniqueUserEmailException(String message) {
        super(message);
    }

    public NotUniqueUserEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
