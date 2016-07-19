package root.dao.mysql;

import org.junit.Assert;
import org.junit.Test;
import root.connection_pool.ConnectionPool;
import root.dao.AbstractDao;
import root.dao.mysql.impl.UserDaoMySql;
import root.model.User;

import java.sql.Connection;


public class MySqlDaoFactoryTest {

    @Test
    public void getInstance() throws Exception {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        AbstractDao dao =  MySqlDaoFactory.getInstance().getDaoByClass(User.class, connection);
        Class expected = UserDaoMySql.class;
        Assert.assertEquals(dao.getClass(), expected);
        connection.close();
    }
}