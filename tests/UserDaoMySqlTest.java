import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.MySqlDaoFactory;
import root.model.Role;
import root.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class UserDaoMySqlTest {
    private static User userTested;
    private static Connection connection;
    private static AbstractDao dao;

    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException {
        connection = ConnectionPool.getInstanse().takeConnection();
        dao =  MySqlDaoFactory.getInstance().getDaoByClass(User.class, connection);
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
        boolean isBanned = true;
        userTested = new User(id, role, login, password, email, lastName,
                firstName, createdDate, udatedDate, isBanned);
    }

    public UserDaoMySqlTest() throws ConnectionPoolException, DaoException {}

    @Test
    public void findAllTest() throws ConnectionPoolException, DaoException, SQLException {
        List<User> users = dao.findAll();
        User user = users.get(0);
        assertEquals(userTested, user);
    }

    @Test
    public void deleteTestById() throws DaoException {
        int id = 1;
        List<User> users = dao.findAll();
        User user = users.get(0);
        root.dao.mysql.util.ResourceManager.PARTS.getString("delete.user");
    }

    @Test
    public void deleteByUserTest() {
        // delete.userByBan
        int id = userTested.getId();
    }

    @Test
    public boolean create(User entity) {
        return false;
    }

    @Test
    public User update(User entity) {
        return null;
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }
}
