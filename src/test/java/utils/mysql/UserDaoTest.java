package utils.mysql;

import entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;

import java.sql.Connection;

public class UserDaoTest {
    GenericDao<User> userDao;
    DaoFactory factory;
    Connection connection;

    @Before
    public void setUp(){
        factory = new Factory();
        connection = factory.getConnection();
        userDao = new UserDao(connection);
    }

    @Test
    public void shouldReturnAdminUser_WhenFindBy(){
        String email = "admin.admin@gmail.com";
        String password = "20583007";
        User user = userDao.findBy(email, password).get(0);
        Assert.assertNotNull(user);
    }

    @Test
    public void shouldReturnNull_WhenFindBy(){
        String email = "admin.admin@gmail.com";
        String password = "20583008";
        Assert.assertNull(userDao.findBy(email, password));
    }

    @After
    public void clear(){
        factory.closeConnection(connection);
    }
}
