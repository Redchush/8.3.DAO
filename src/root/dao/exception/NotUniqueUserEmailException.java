package root.dao.exception;

import root.zadded.DaoInterface.DBException;

/**
 * Created by user on 27.02.2016.
 */
public class NotUniqueUserEmailException extends DaoException {
    public NotUniqueUserEmailException() {}

    public NotUniqueUserEmailException(String message) {
        super(message);
    }

    public NotUniqueUserEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
