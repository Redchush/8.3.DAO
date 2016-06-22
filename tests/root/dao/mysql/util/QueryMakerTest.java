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
import root.model.Tag;
import root.model.User;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class QueryMakerTest {


    private static Connection connection;
    private static AbstractDao userDao;
    private static AbstractDao tagDao;
    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException {
        connection = ConnectionPool.getInstanse().takeConnection();
        userDao =  MySqlDaoFactory.getInstance().getDaoByClass(User.class, connection);
        tagDao = MySqlDaoFactory.getInstance().getDaoByClass(Tag.class, connection);
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
        String query = QueryMaker.getUpdate(userDao);
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
    @Test
    public void getCreate() throws Exception {
        String query = QueryMaker.getCreate(userDao);
        PreparedStatement statement = connection.prepareStatement(query);
        ParameterMetaData metaData = statement.getParameterMetaData();
        int countActual = metaData.getParameterCount();
        int countExpected = Integer.parseInt(ResourceManager.STRUCTURE.getString("users.num")) - 1;
        Assert.assertEquals(countExpected, countActual);
        System.out.println(query);

        String queryTag = QueryMaker.getCreate(tagDao);
        PreparedStatement statementTag = connection.prepareStatement(queryTag);
        ParameterMetaData metaDataTag = statement.getParameterMetaData();
        int countActualTags = metaData.getParameterCount();
        int countExpectedTags = Integer.parseInt(ResourceManager.STRUCTURE.getString("users.num")) - 1;
        Assert.assertEquals(countActualTags, countExpectedTags);
        System.out.println(queryTag);
    }
}