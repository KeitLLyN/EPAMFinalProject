package servlet.loginServlets;

import entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class LoginServletTest extends Mockito {
    final HttpServletRequest REQUEST = mock(HttpServletRequest.class);
    final HttpServletResponse RESPONSE = mock(HttpServletResponse.class);
    final RequestDispatcher DISPATCHER = mock(RequestDispatcher.class);

    final String ERROR_PATH = "/errorLogin.jsp";
    final String LOGIN_PATH = "/login.jsp";
    final LoginServlet LOGIN_SERVLET = new LoginServlet();

    DaoFactory factory;
    Connection connection;
    GenericDao<User> userDao;

    @Before
    public void setUp(){
        factory = new Factory();
        connection = factory.getConnection();
        userDao = new UserDao(connection);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(REQUEST.getRequestDispatcher(ERROR_PATH)).thenReturn(DISPATCHER);
        when(REQUEST.getParameter("userName")).thenReturn("test");
        when(REQUEST.getParameter("psw")).thenReturn("test");
        Assert.assertNull(userDao.findBy("test", "test"));

        LOGIN_SERVLET.doPost(REQUEST,RESPONSE);

        verify(REQUEST, atLeast(2)).getParameter(anyString());
        verify(REQUEST).setAttribute("errorMessage","Check your user name and password");
        verify(DISPATCHER).forward(REQUEST,RESPONSE);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(REQUEST.getRequestDispatcher(LOGIN_PATH)).thenReturn(DISPATCHER);
        LOGIN_SERVLET.doGet(REQUEST,RESPONSE);

        verify(REQUEST,times(1)).getRequestDispatcher(anyString());
        verify(DISPATCHER,times(1)).forward(REQUEST,RESPONSE);
    }

    @After
    public void clear(){
        factory.closeConnection(connection);
    }
}