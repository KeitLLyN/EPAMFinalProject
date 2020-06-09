package utils.dao;

import entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.UserDao;

import java.sql.Connection;
import java.util.List;

public class JDBCDaoTest {
    Connection connection;
    DaoFactory factory;
    GenericDao<User> userDao;

    @Before
    public void setUp() {
        factory = new Factory();
        connection = factory.getConnection();
        userDao = new UserDao(connection);
    }

    @Test
    public void shouldReturnListOfAllUsers_WhenGetAll() {
        List<User> users;
        users = userDao.getAll();
        Assert.assertNotNull(users);
        Assert.assertTrue(users.size() > 0);
    }

    @Test
    public void shouldInsertUpdateDeleteUser(){
        String expected = "test@test.com";

        User user = new User("test@test.com", "cfcd208495d565ef66e7dff9f98764da");
        userDao.insert(user);
        String actual = userDao.findBy(new String[]{expected, "0"}).get(0).getEmail();
        Assert.assertEquals(expected, actual);

        expected = "test@test.ru";
        user = userDao.findBy(new String[]{"test@test.com", "0"}).get(0);
        Assert.assertNotNull(user);
        user.setEmail(expected);
        userDao.update(user);
        actual = userDao.findBy(new String[]{"test@test.ru", "0"}).get(0).getEmail();
        Assert.assertEquals(expected, actual);

        int oldSize = userDao.getAll().size();
        Assert.assertTrue(oldSize > 0);

        user = userDao.findBy(new String[]{"test@test.ru", "0"}).get(0);
        Assert.assertNotNull(user);
        userDao.delete(user);

        int newSize = userDao.getAll().size();
        Assert.assertEquals(oldSize - 1, newSize);
    }

    @After
    public void clear() {
        factory.closeConnection(connection);
    }
}
