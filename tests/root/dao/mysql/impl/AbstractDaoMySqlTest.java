package root.dao.mysql.impl;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.MySqlDaoFactory;
import root.dao.mysql.util.QueryMaker;
import root.model.Role;
import root.model.User;

import java.sql.*;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AbstractDaoMySqlTest {

    private static User userTested;
    private static Connection connection;
    private static AbstractDao dao;
    private static AbstractDaoTestedProtected daoTestedProtected;

    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException {
        connection = ConnectionPool.getInstanse().takeConnection();
        dao =  MySqlDaoFactory.getInstance().getDaoByClass(User.class, connection);
        daoTestedProtected = new AbstractDaoTestedProtected(connection);
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

    @Test
    public void getConnection() throws Exception {

    }

    @Test
    public void setConnection() throws Exception {

    }

    @Test
    public void findAll() throws Exception {
        List<User> users = dao.findAll();
        User user = users.get(0);
        assertEquals(userTested, user);
    }

    @Test
    public void findEntityById() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void delete1() throws Exception {

    }

    @Test
    public void create() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void close() throws Exception {

    }

    @Test
    public void createSimpleEntity() throws Exception {

    }

    @Test
    public void createEntityList() throws Exception {

    }

    @Test
    public void updateDbRecord() throws Exception {

    }

    @Test
    public void close1() throws Exception {

    }

    @Test
    public void close2() throws Exception {

    }
    @Test
    public void fillLastParameterWithId() throws SQLException {
        String querty = " UPDATE id, login, password, email, role_id, last_name, banned, first_name, created_date, " +
                "updated_date  SET " +
                "id = DEFAULT,  login = ?,  password = ?,  email = ?,  role_id = ?,  last_name = ?,  banned = ?,  " +
                "first_name = ?,  created_date = ?,  updated_date  = ?  where id = ? ";
        PreparedStatement statement = connection.prepareStatement(querty);
        daoTestedProtected.fillLastParameterWithId(statement, userTested);
        assertTrue(statement.toString().contains("where id = 1"));
     }

    @Test
    public void fillStatementWithFullAttributesSet() throws SQLException {
        String querty = " UPDATE id, login, password, email, role_id, last_name, banned, first_name, created_date, " +
                "updated_date  SET " +
                "id = DEFAULT,  login = ?,  password = ?,  email = ?,  role_id = ?,  last_name = ?,  banned = ?,  " +
                "first_name = ?,  created_date = ?,  updated_date  = ?  where id = ? ";
        PreparedStatement statement = connection.prepareStatement(querty);
        daoTestedProtected.fillLastParameterWithId(statement, userTested);
        System.out.println(statement);
        assertTrue(statement.toString().contains("where id = 1"));
        String email = userTested.getEmail();
        statement.setString(3, email);
        System.out.println(querty);
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }

    private void findAll(AbstractDao dao) throws DaoException {
        String query =  QueryMaker.getSelectQueryAll(dao);
        System.out.println(query);
        ResultSet set;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            set = statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't execute " + query);
        } finally {

        }
    }


}