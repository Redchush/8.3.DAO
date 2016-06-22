package root.dao.mysql.util;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.MySqlDaoFactory;
import root.model.User;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class QueryMakerTest {

    private static Connection connection;
    private static AbstractDao dao;
    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException {
        connection = ConnectionPool.getInstanse().takeConnection();
        dao =  MySqlDaoFactory.getInstance().getDaoByClass(User.class, connection);
    }

    @Test
    public void getSelectQueryAll() throws Exception {

    }

    @Test
    public void getSelectQueryById() throws Exception {

    }

    @Test
    public void getDeleteQueryById() throws Exception {

    }

    @Test
    public void getDeleteByBan() throws Exception {

    }

    @Test
    public void getUpdate() throws Exception {
        String query = QueryMaker.getUpdate(dao);
        PreparedStatement statement = connection.prepareStatement(query);
        ParameterMetaData metaData = statement.getParameterMetaData();
        int countActual = metaData.getParameterCount();
        int countExpected = Integer.parseInt( ResourceManager.STRUCTURE.getString("users.num"));
        Assert.assertEquals(countExpected, countActual);
        System.out.println(query);

    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }

}