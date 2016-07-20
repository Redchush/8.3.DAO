package root.dao.mysql.util;

import org.junit.AfterClass;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class QueryMakerTest {

    private static Connection connection;
    private static AbstractDao userDao;
    private static AbstractDao tagDao;
    private static ConnectionPool pool;

    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException {
        pool = ConnectionPool.getInstance();
        connection = pool.takeConnection();
        userDao =  MySqlDaoFactory.getInstance().getDaoByClass(User.class, connection);
        tagDao = MySqlDaoFactory.getInstance().getDaoByClass(Tag.class, connection);
    }


    @AfterClass
    public static void logOut() throws SQLException {
        pool.dispose();
    }


    @Test
    public void getSelectQueryById() throws Exception {
        String expected = "DELETE FROM like_it.tags" +
        " WHERE id = ?";
        String queryActual = QueryMaker.getDeleteById(tagDao.getClass());
        queryActual = normalize(queryActual);
        boolean isEquals = queryActual.equalsIgnoreCase(expected);
        assertTrue(isEquals);
     }

    @Test
    public void getDeleteByBan() throws Exception {
        String queryExp = "UPDATE like_it.users\nset banned = true\nwhere id = ?";
        String queryActual = QueryMaker.getDeleteByBan(userDao.getClass());
        boolean isEqual = queryActual.equalsIgnoreCase(queryExp);
        assertTrue(isEqual);
    }

    @Test
    public void getUpdate() throws Exception {
        String queryActualTag = QueryMaker.getUpdate(tagDao.getClass());
        System.out.println(queryActualTag);
        String queryExpTag = "UPDATE like_it.tags\n" +
        "SET  name = ?\n" +
        "WHERE id = ?";
        assertEquals(queryExpTag, queryActualTag);


        String queryActual = QueryMaker.getUpdate(userDao.getClass());
        PreparedStatement statement = connection.prepareStatement(queryActual);
        ParameterMetaData metaData = statement.getParameterMetaData();
        int countActual = metaData.getParameterCount();
        int countExpected = Integer.parseInt( ResourceManager.STRUCTURE.getString("users.num"));
        String queryExp = "UPDATE like_it.users\n" +
        "SET id = DEFAULT,  role_id = ?,  login = ?,  password = ?,  email = ?,  last_name = ?,  first_name = ?,  " +
                "created_date = ?,  updated_date = ?,  banned = ?\n" +
        "where id = ?";
        boolean b = queryActual.trim().equalsIgnoreCase(queryExp);
     //   assertEquals(countExpected, countActual);
        assertTrue(b);
    }

    @Test
    public void getCreate() throws Exception {
        String query = QueryMaker.getCreate(userDao.getClass());
        PreparedStatement statement = connection.prepareStatement(query);
        ParameterMetaData metaData = statement.getParameterMetaData();
        int countActual = metaData.getParameterCount();
        int countExpected = Integer.parseInt(ResourceManager.STRUCTURE.getString("users.num")) - 1;
        assertEquals(countExpected, countActual);


        String queryTag = QueryMaker.getCreate(tagDao.getClass());
        PreparedStatement statementTag = connection.prepareStatement(queryTag);
        ParameterMetaData metaDataTag = statement.getParameterMetaData();
        int countActualTags = metaData.getParameterCount();
        int countExpectedTags = Integer.parseInt(ResourceManager.STRUCTURE.getString("users.num")) - 1;
        assertEquals(countActualTags, countExpectedTags);

    }

    private String normalize(String string){
        String result = string.replaceAll("\n"," ").replace("  ", " ");
        return result;
    }
}