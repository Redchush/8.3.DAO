package root.dao.mysql.impl;

import org.junit.*;
import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.MySqlDaoFactory;
import root.model.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TagDaoMySqlTest {

    private static Connection connection;
    private static AbstractDao dao;
    private static int initialSizeOftable = 7;

    private Tag tagToCreate;
    private Tag tagTested;



    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException {
        connection = ConnectionPool.getInstanse().takeConnection();
        dao =  MySqlDaoFactory.getInstance().getDaoByClass(Tag.class, connection);
    }

    @Before
    public void setUp() throws Exception {
        tagTested = new Tag(1, "java");
        tagToCreate = new Tag();
        tagToCreate.setName("testedTag");
    }

    @After
    public void tearDown() throws Exception {

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
    public void deleteTestById() throws DaoException {
        int id = tagTested.getId();
        boolean boo = dao.delete(id);
        List<Tag> tags = dao.findAll();
        assertEquals(tags.size(), initialSizeOftable - 1);
        System.out.println(boo);
        showTableState();

    }

    @Test
    public void delete() {

    }

    @Test
    public void createTest() throws DaoException, SQLException {
        String undoQuery = "DELETE FROM `like_it`.`tags` WHERE `id` = '?'";
        PreparedStatement statement = connection.prepareStatement(undoQuery);
        connection.commit();
        showTableState();
        boolean f = dao.create(tagToCreate);
        showTableState();
        System.out.println(f);
    }

    @Test
    public void update() {
        Tag entity;
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }
    public void showTableState() throws DaoException {
        List<Tag> tags = dao.findAll();
        System.out.println(tags);
    }

}
