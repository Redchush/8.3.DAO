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
import root.model.Role;
import root.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UserDaoMySqlTest {
    private static User userTestedWithBan;
    private static User userTestedWithoutBan;
    private static User userToCreate;
    private static User userToUpdate;

    private static ConnectionPool pool;
    private static Connection connection;
    private static AbstractDao dao;

    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException, SQLException {
        pool = ConnectionPool.getInstance();
        connection = pool.takeConnection();
        dao =  MySqlDaoFactory.getInstance().getDaoByClass(User.class, connection);
        prepareUsers();
        loginNormalise();
    }

    private static void prepareUsers(){
        int id = 1;
        String login = "lara";
        String password = "m23kujj";
        String email = "lara@mail.ru";
        Role role = new Role(3);

        String lastName = null;
        String firstName = null;

        Calendar cal = Calendar.getInstance();
        cal.set(2016, 05, 14, 14,33,04);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp createdDate = new Timestamp(cal.getTimeInMillis());
        Timestamp udatedDate = null;
        boolean isBanned = false;

        userTestedWithBan = new User(id, role, login, password, email, lastName,
                firstName, createdDate, udatedDate, true);
        userTestedWithoutBan = new User(id, role, login, password, email, lastName,
                firstName, createdDate, udatedDate, false);

        id = 2;
        login = "kennni";
        password = "nskofkjal";
        email = "kenni@yahoo.com";
        //month counted from 0!!!!
        cal.set(2016, 02, 15, 14,33,04);
        createdDate = new Timestamp(cal.getTimeInMillis());
        userToUpdate  = new User(id, role, login, password, email, lastName,
                firstName, createdDate, udatedDate, false);
    }

    private static void loginNormalise() throws ConnectionPoolException, DaoException, SQLException {
        DBRestorer.truncateAll(connection);
        DBRestorer.restoreAll(connection);
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
        pool.dispose();
    }

    @Test
    public void findAllTest() throws ConnectionPoolException, DaoException, SQLException {
        List<User> users = dao.findAll();
        User user = users.get(0);
        assertEquals(userTestedWithoutBan, user);
    }

    @Test
    public void deleteTestById() throws DaoException {
        int id = userTestedWithoutBan.getId();
        boolean isDelated = dao.delete(id);
        User userActual = (User) dao.findEntityById(id);
        assertTrue(isDelated);
        assertEquals(userTestedWithBan, userActual);
    }

    @Test
    public void deleteByUserTest() {
        // delete.userByBan
        int id = userTestedWithoutBan.getId();
    }

    @Test
    public void create() {
        User user = new User();

    }

    @Test
    public void update() throws DaoException {
        User user = (User) dao.findEntityById(2);
        assertEquals(userToUpdate, user);
        user.setFirstName("a");
        dao.update(user);
        User userActual = (User) dao.findEntityById(2);
        assertEquals(user,  userActual);
    }
}


