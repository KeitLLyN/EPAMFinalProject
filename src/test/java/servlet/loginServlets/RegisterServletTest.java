package servlet.loginServlets;

import entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utils.AppUtils;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class RegisterServletTest extends Mockito {
    final HttpServletRequest REQUEST = mock(HttpServletRequest.class);
    final HttpServletResponse RESPONSE = mock(HttpServletResponse.class);
    final HttpSession SESSION = mock(HttpSession.class);
    final RequestDispatcher DISPATCHER = mock(RequestDispatcher.class);

    final String REGISTER_PATH = "/register.jsp";
    final RegisterServlet REGISTER_SERVLET = new RegisterServlet();

    DaoFactory factory;
    Connection connection;
    GenericDao<User> userDao;

    @Before
    public void setUp() {
        factory = new Factory();
        connection = factory.getConnection();
        userDao = new UserDao(connection);

    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(REQUEST.getParameter("userName")).thenReturn("test");
        when(REQUEST.getParameter("email")).thenReturn("test");
        when(REQUEST.getParameter("password")).thenReturn("test");
        when(REQUEST.getSession()).thenReturn(SESSION);
        Assert.assertNull(userDao.findBy("test","test"));

        REGISTER_SERVLET.doPost(REQUEST,RESPONSE);
        Assert.assertNotNull(userDao.findBy("test","test"));
        verify(REQUEST,atLeast(3)).getParameter(anyString());
        verify(SESSION,atLeast(1)).setAttribute(anyString(),any());
        userDao.delete(userDao.findBy("test","test").get(0));
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(REQUEST.getRequestDispatcher(REGISTER_PATH)).thenReturn(DISPATCHER);
        REGISTER_SERVLET.doGet(REQUEST,RESPONSE);

        verify(REQUEST,times(1)).getRequestDispatcher(anyString());
        verify(DISPATCHER,times(1)).forward(REQUEST,RESPONSE);
    }
}