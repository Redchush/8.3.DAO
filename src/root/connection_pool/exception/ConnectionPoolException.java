package root.connection_pool.exception;

/**
 * Created by user on 20.06.2016.
 */
public class ConnectionPoolException extends Exception {

    private static final long serialVesionUID = 1L;

    public ConnectionPoolException() {}

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
