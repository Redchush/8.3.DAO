package root.dao.exception.jdbc_exception;

import root.dao.exception.DaoException;

/**
 * Created by user on 21.06.2016.
 */
public class JdbcDaoException extends DaoException {

    public JdbcDaoException() {
    }

    public JdbcDaoException(String message) {
        super(message);
    }

    public JdbcDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
