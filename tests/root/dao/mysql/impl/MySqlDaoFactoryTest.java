package root.dao.mysql.impl;

import org.junit.Assert;
import org.junit.Test;
import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.MySqlDaoFactory;
import root.model.User;

import java.sql.Connection;



public class MySqlDaoFactoryTest {

    @Test
    public void getDaoByClassTest() throws ConnectionPoolException, DaoException {
        Connection connection = ConnectionPool.getInstanse().takeConnection();
        AbstractDao dao =  MySqlDaoFactory.getInstance().getDaoByClass(User.class, connection);
        Class expected = UserDaoMySql.class;
        Assert.assertEquals(dao.getClass(), expected);
    }
}
