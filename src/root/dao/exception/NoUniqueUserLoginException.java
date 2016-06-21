package root.dao.exception;

import root.zadded.DaoInterface.DBException;

/**
 * Created by user on 27.02.2016.
 */
public class NoUniqueUserLoginException extends DaoException{
    public NoUniqueUserLoginException() {
    }

    public NoUniqueUserLoginException(String message) {
        super(message);
    }

    public NoUniqueUserLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
