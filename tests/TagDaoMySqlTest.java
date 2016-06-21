import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.MySqlDaoFactory;
import root.model.Role;
import root.model.Tag;
import root.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TagDaoMySqlTest {
    private static Tag tagTested;
    private static Connection connection;
    private static AbstractDao dao;

    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException {
        connection = ConnectionPool.getInstanse().takeConnection();
        dao =  MySqlDaoFactory.getInstance().getDaoByClass(Tag.class, connection);
        tagTested = new Tag(1, "java");
     }

    public TagDaoMySqlTest() throws ConnectionPoolException, DaoException {}

    @Test
    public void findAllTest() throws ConnectionPoolException, DaoException, SQLException {
        List<Tag> tags = dao.findAll();
        Tag tag = tags.get(0);
        assertEquals(tagTested, tag);
    }

    @Test
    public static void deleteTestById() throws DaoException {


    }

    @Test
    public void deleteByUserTest() {

    }

    @Test
    public void createTest() {
        Tag entity;
    }

    @Test
    public void update() {

        Tag entity;

    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }

}
