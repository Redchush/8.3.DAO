package root.dao.mysql.impl;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.MySqlDaoFactory;
import root.dao.mysql.impl.helper.ReflectionHelper;
import root.model.Entity;
import root.model.Role;
import root.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class AbstractDaoMySqlTest {


    private static User userTested;
    private static Connection connectionExpected;
    private static AbstractDao dao;
    private static AbstractDaoTestedProtected daoTestedProtected;
    private static List<AbstractDaoMySql> instanseList = new ArrayList<>();

    @BeforeClass
    public static void login() throws ConnectionPoolException, DaoException {
        connectionExpected = ConnectionPool.getInstance().takeConnection();
        dao = MySqlDaoFactory.getInstance().getDaoByClass(User.class, connectionExpected);
        daoTestedProtected = new AbstractDaoTestedProtected(connectionExpected);
        int id = 1;
        String login = "lara";
        String password = "m23kujj";
        String email = "lara@mail.ru";
        Role role = new Role(3);
        String lastName = null;
        String firstName = null;
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 05, 14, 14, 33, 04);
        cal.set(Calendar.MILLISECOND, 0);

        Timestamp createdDate = new Timestamp(cal.getTimeInMillis());
        Timestamp udatedDate = null;
        boolean isBanned = false;
        userTested = new User(id, role, login, password, email, lastName,
                firstName, createdDate, udatedDate, isBanned);
        List<Class> classes = ReflectionHelper.findAllSublasses(root.dao.mysql.impl.AbstractDaoMySql.class);
        ReflectionHelper.createIntanses(instanseList, classes, connectionExpected);

    }

    @Test
    public void getConnection() throws Exception {
        for ( AbstractDaoMySql daoMySql : instanseList){
            Connection actual = daoMySql.getConnection();
            assertEquals(connectionExpected, actual);
        }
    }

    @Test
    public void setConnection() throws Exception {
        Connection newConnection = null;
        for ( AbstractDaoMySql daoMySql : instanseList) {
            try {
                newConnection = ConnectionPool.getInstance().takeConnection() ;
                daoMySql.setConnection(newConnection);
                Connection actual = daoMySql.getConnection();
                assertEquals(newConnection, actual);
            } finally {
                daoMySql.close(newConnection);
                daoMySql.setConnection(connectionExpected);
            }
        }
    }

    @Test
    public void findAll1() throws Exception {
        for ( AbstractDaoMySql daoMySql : instanseList) {
            List<Entity> list =  daoMySql.findAll();
        }
    }

    @Test
    public void fillLastParameterWithId() throws SQLException {
        String query = " UPDATE id, login, password, email, role_id, last_name, banned, first_name, created_date, " +
                "updated_date  SET " +
                "id = DEFAULT,  login = ?,  password = ?,  email = ?,  role_id = ?,  last_name = ?,  banned = ?,  " +
                "first_name = ?,  created_date = ?,  updated_date  = ?  where id = ? ";
        PreparedStatement statement = connectionExpected.prepareStatement(query);
        daoTestedProtected.fillLastParameterWithId(statement, userTested);
        assertTrue(statement.toString().contains("where id = 1"));
     }

    @Test
    public void fillStatementWithFullAttributesSet() throws SQLException {
        String querty = " UPDATE id, login, password, email, role_id, last_name, banned, first_name, created_date, " +
                "updated_date  SET " +
                "id = DEFAULT,  login = ?,  password = ?,  email = ?,  role_id = ?,  last_name = ?,  banned = ?,  " +
                "first_name = ?,  created_date = ?,  updated_date  = ?  where id = ? ";
        PreparedStatement statement = connectionExpected.prepareStatement(querty);
        daoTestedProtected.fillLastParameterWithId(statement, userTested);
        System.out.println(statement);
        assertTrue(statement.toString().contains("where id = 1"));
        String email = userTested.getEmail();
        statement.setString(3, email);
        System.out.println(querty);
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connectionExpected.close();
    }


}