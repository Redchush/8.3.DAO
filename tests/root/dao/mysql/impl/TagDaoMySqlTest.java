package root.dao.mysql.impl;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.MySqlDaoFactory;
import root.dao.mysql.impl.helper.DBRestorer;
import root.model.Tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class TagDaoMySqlTest {

    private static Connection connection;
    private static ConnectionPool pool;
    private static AbstractDao dao;
    private static int initialSizeOftable = 7;

    private Tag tagToCreate;
    private static Tag tagTested;

    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException, SQLException {
        pool = ConnectionPool.getInstance();
        connection = pool.takeConnection();
        dao =  MySqlDaoFactory.getInstance().getDaoByClass(Tag.class, connection);
        tagTested = new Tag(1, "java");
        DBRestorer.truncateAll(connection);
        DBRestorer.restoreAll(connection);
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
        pool.dispose();
    }

//    @Before
//    public void setUp() throws Exception {
//
//    }

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
        int id = 3;
        boolean boo = dao.delete(id);
        assertTrue(boo);
        // showTableState();
    }

    @Test
    public void createTest() throws DaoException, SQLException {
        Tag tagNotExcpected = new Tag(10, "tagToCreate");
        tagToCreate = new Tag();
        tagToCreate.setName("tagToCreate");
        boolean f = dao.create(tagToCreate);
        Tag tagNotActual = (Tag) dao.findEntityById(10);
    //    showTableState();
        Tag tagActual = (Tag) dao.findEntityById(8);
        assertNotEquals(tagNotExcpected, tagNotActual);

        Tag tagExcpected = new Tag(8, "tagToCreate");
        assertEquals(tagExcpected, tagActual);

    }

    @Test
    public void update() throws DaoException {
        Tag tagExpected = new Tag(2, "tagToUpdate");
        dao.update(tagExpected);
        Tag tagActual = (Tag)  dao.findEntityById(2);
        assertEquals(tagExpected, tagActual);
    }

    /*
     * not sure that it is "legal" method because of sytem.out.printl presents
     */
//    public void showTableState() {
//        String query = "SELECT id, NAME  FROM tags";
//        try (Statement statement = connection.createStatement();
//            ResultSet set = statement.executeQuery(query)) {
//            ResultSetMetaData meta = set.getMetaData();
//            while (set.next()) {
//                int length = meta.getColumnCount();
//                System.out.print("{");
//                for (int i = 1; i <= length; i++) {
//                    System.out.print( set.getObject(i) + " ");
//                }
//                System.out.print("}");
//            }
//        } catch (SQLException e) {
//            System.err.println("exception in show table" );
//        }
//    }

}
