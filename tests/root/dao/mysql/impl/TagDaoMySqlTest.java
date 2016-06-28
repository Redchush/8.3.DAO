package root.dao.mysql.impl;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.MySqlDaoFactory;
import root.dao.mysql.impl.helper.DBRestorer;
import root.model.Tag;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TagDaoMySqlTest {

    private static Connection connection;
    private static AbstractDao dao;
    private static int initialSizeOftable = 7;

    private Tag tagToCreate;
    private Tag tagTested;

    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException, SQLException {
        connection = ConnectionPool.getInstanse().takeConnection();
        dao =  MySqlDaoFactory.getInstance().getDaoByClass(Tag.class, connection);
    }

    @Before
    public void setUp() throws Exception {
        DBRestorer.truncateAll(connection);
        DBRestorer.restoreAll(connection);
        tagTested = new Tag(1, "java");
    }

    @Test
    public void findEntityById() throws ConnectionPoolException, DaoException, SQLException {
        Tag tag = (Tag) dao.findEntityById(1);
        assertEquals(tagTested, tag);
    }

    @Test
    public void findAllTest() throws ConnectionPoolException, DaoException, SQLException {
        List<Tag> tags = dao.findAll();
        assertEquals(tags.size(), initialSizeOftable);
    }
    @Test
    public void deleteTestById() throws DaoException, SQLException {
        int id = tagTested.getId();
        boolean boo = dao.delete(id);
        assertTrue(boo);
        // showTableState();
    }

    @Test
    public void createTest() throws DaoException, SQLException {
        Tag tagExpected = new Tag(10, "testedTag");
        tagToCreate = new Tag();
        tagToCreate.setName("testedTag");
        boolean f = dao.create(tagToCreate);
        Tag tagActual = (Tag) dao.findEntityById(10);
        assertEquals(tagExpected, tagActual);
    }

    @Test
    public void update() throws DaoException {
        Tag tagExpected = new Tag(1, "testedTag");
        dao.update(tagExpected);
        Tag tagActual = (Tag)  dao.findEntityById(1);
        assertEquals(tagExpected, tagActual);
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }

//    @Test
//    public void reset(){ }

    public void showTableState() {
        String query = "SELECT id, NAME  FROM tags";
        try (Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(query)) {
            ResultSetMetaData meta = set.getMetaData();
            while (set.next()) {
                int length = meta.getColumnCount();
                System.out.print("{");
                for (int i = 1; i <= length; i++) {
                    System.out.print( set.getObject(i) + " ");
                }
                System.out.print("}");
            }
        } catch (SQLException e) {
            System.err.println("exception in show table" );
        }
    }

}
