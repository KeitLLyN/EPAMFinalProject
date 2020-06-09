package utils.mysql;

import entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;

import java.sql.Connection;

public class FactoryTest {
    DaoFactory factory;
    Connection connection;

    @Before
    public void setUp(){
        factory = new Factory();
    }

    @Test
    public void shouldReturnUserDaoClass_WhenGetDao(){
        Class<UserDao> expectedUserDaoClass = UserDao.class;
        Class<?> actual = factory.getDao(User.class).getClass();
        Assert.assertEquals(expectedUserDaoClass,actual);
    }

    @Test
    public void shouldReturnConnection_WhenGetConnection(){
        connection = factory.getConnection();
        Assert.assertNotNull(connection);
    }

    @After
    public void clear(){
        factory.closeConnection(connection);
    }
}
