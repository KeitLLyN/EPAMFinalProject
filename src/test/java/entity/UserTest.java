package entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    User user;

    @Before
    public void setUp(){
        user = new User();
    }

    @Test
    public void shouldReturnIdOfUser(){
        user.setId(0);
        int expected = 0;
        int actual = user.getId();
        Assert.assertEquals(expected,actual);
    }
}
