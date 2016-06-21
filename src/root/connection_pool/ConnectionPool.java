 package root.connection_pool;

import root.connection_pool.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public final class ConnectionPool {

    private BlockingQueue<Connection> freeConnections;
    private BlockingQueue<Connection> givenConnections;

    private ConnectionPool(){}

    public static ConnectionPool getInstanse() throws ConnectionPoolException {
        ConnectionPool pool = new ConnectionPool();
        pool.initPoolData();
        return pool;
    }

    private void initPoolData() throws ConnectionPoolException{
        ConnectionPoolDefinition definitor = ConnectionPoolDefinition.getInstance();

        int size = definitor.getPoolSize();
        givenConnections = new ArrayBlockingQueue<Connection>(size);
        freeConnections = new ArrayBlockingQueue<Connection>(size);
        Connection connection = null;
        try {
            for (int i = 0; i < size; i++) {
                connection = definitor.getConnection();
                PooledConnection pooledConnection = new PooledConnection(connection);
                freeConnections.add(pooledConnection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Can't create PooledConnection by " + connection +
                    " with data : url = " + definitor.getUrl() + ", user = " + definitor.getUser() + ", password = " +
                    definitor.getPassword());
        }
    }

    public void dispose(){
        emptyConnectionQuies();
    }

    public Connection takeConnection() throws ConnectionPoolException{
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenConnections.add(connection);
        } catch (InterruptedException e) {
           throw new ConnectionPoolException("Can't take connection from queue", e);
        }
        return connection;
    }



    private void emptyConnectionQuies() {
        try {
            closeConnectionQueue(freeConnections);
            closeConnectionQueue(givenConnections);
        } catch (SQLException e) {
           //supressedException //
        }
    }

    public void closeConnectionQueue(BlockingQueue<Connection> queque) throws SQLException {
        Connection connection;
        while ((connection = queque.poll()) !=null){
             if (!connection.getAutoCommit()){
                    connection.commit();
             }
            ((PooledConnection) connection).closeNaturally();
        }
    }

    public void closeConnection(Connection connection, Statement statment, ResultSet set){
        try {
            set.close();
        } catch (SQLException e) {

        }
        try {
            statment.close();
        } catch (SQLException e) {

        }
        closeConnection(connection);
    }

    public void closeConnection(Connection connection, Statement statment){
        try {
            statment.close();
        } catch (SQLException e) {

        }
        closeConnection(connection);

    }

    public void closeConnection(Connection connection)  {
       try{
            connection.close();
            if (!givenConnections.remove(connection)){
                throw new SQLException("Connection pool can't remove connection "
                        + connection + "from given connections " );
            }

            if (!freeConnections.offer(connection)){
                throw new SQLException("Connection pool can't add connection "
                        + connection + "to existing connections " );
            }

        } catch (SQLException e) {

        }
    }
}
