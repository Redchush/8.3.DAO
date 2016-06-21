package root.connection_pool;

import root.connection_pool.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static root.connection_pool.util.DBParameters.*;
import static root.connection_pool.util.ResourceManager.DATABASE;


final class ConnectionPoolDefinition {

    private String url;
    private String driver;
    private String user;
    private String password;
    private int poolSize;

    private static ConnectionPoolDefinition instance;

    private ConnectionPoolDefinition() throws ConnectionPoolException {
        url = DATABASE.getString(DB_URL);
        user =  DATABASE.getString(DB_USER);
        password =  DATABASE.getString(DB_PASSWORD);
        try{
            String size = DATABASE.getString(DB_POOL_SIZE);
            poolSize = Integer.parseInt(size);
        } catch (NumberFormatException e){
            poolSize = getDefaultPoolSize(); 
        }
        driver =  DATABASE.getString(DB_DRIVER);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't load the driver by path " + driver, e);
        }
    }     
   
    protected static ConnectionPoolDefinition getInstance() throws ConnectionPoolException {
        if (instance == null){
            instance = new ConnectionPoolDefinition();
        }
        return instance;
    }

    protected String getUrl() {
        return url;
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    protected String getDriver() {
        return driver;
    }

    protected void setDriver(String driver) {
        this.driver = driver;
    }

    protected String getUser() {
        return user;
    }

    protected void setUser(String user) {
        this.user = user;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected int getPoolSize() {
        return poolSize;
    }

    protected void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    private int getDefaultPoolSize(){
        return 5; 
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,user, password);
        } catch (SQLException e) {
            throw new ConnectionPoolException("ConnectionPoolDefinition can't create connection with " +
            "url = " + url + ", user = " + user + ", password = " + password, e);
        }
        return connection;
    }
}
