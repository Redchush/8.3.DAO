package root.connection_pool;

import commonUtil.ClassName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import root.connection_pool.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static root.connection_pool.util.DBParameters.*;
import static root.connection_pool.util.ResourceManager.DATABASE;


final class ConnectionPoolDefinition {

    private final String url;
    private final String driver;
    private final String user;
    private final String password;

    private final int poolSize;
    private final int defaultPoolSize =5;

    private static ConnectionPoolDefinition instance;

    private static Logger logger = LogManager.getLogger(ClassName.getClassName());

    private ConnectionPoolDefinition() throws ConnectionPoolException {
        int tempPoolSize;
        url = DATABASE.getString(DB_URL);
        user =  DATABASE.getString(DB_USER);
        password =  DATABASE.getString(DB_PASSWORD);
        try{
            String size = DATABASE.getString(DB_POOL_SIZE);
            tempPoolSize = Integer.parseInt(size);
        } catch (NumberFormatException e){
            tempPoolSize = defaultPoolSize;
        }
        poolSize = tempPoolSize;
        driver =  DATABASE.getString(DB_DRIVER);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't load the driver by path " + driver, e);
        }
        logger.info("Definitor created " + this);
    }
   
    protected static ConnectionPoolDefinition getInstance() throws ConnectionPoolException {
        if (instance == null){
            instance = new ConnectionPoolDefinition();
        }
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,user, password);
        } catch (SQLException e) {
            logger.error("ConnectionPoolDefinition can't create connection with " +
                    "url = " + url + ", user = " + user + ", password = " + password, e);
            throw new ConnectionPoolException("ConnectionPoolDefinition can't create connection with this data");
        }
        return connection;
    }

    @Override
    public String toString() {
        return "ConnectionPoolDefinition{" +
                "url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", poolSize=" + poolSize +
                ", defaultPoolSize=" + defaultPoolSize +
                '}';
    }
}
