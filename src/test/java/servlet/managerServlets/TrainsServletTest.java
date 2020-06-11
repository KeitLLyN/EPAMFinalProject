package servlet.managerServlets;

import entity.Train;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.TrainDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class TrainsServletTest extends Mockito {
    final HttpServletRequest REQUEST = mock(HttpServletRequest.class);
    final HttpServletResponse RESPONSE = mock(HttpServletResponse.class);
    final RequestDispatcher DISPATCHER = mock(RequestDispatcher.class);

    final String PATH = "/manageMainPage";

    final TrainsServlet TRAIN_SERVLET = new TrainsServlet();

    DaoFactory factory;
    Connection connection;
    GenericDao<Train> trainDao;

    @Before
    public void setUp() {
        factory = new Factory();
        connection = factory.getConnection();
        trainDao = new TrainDao(connection);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        when(REQUEST.getParameter("from")).thenReturn("test");
        when(REQUEST.getParameter("to")).thenReturn("test");
        when(REQUEST.getParameter("date")).thenReturn("test");
        when(REQUEST.getParameter("startTime")).thenReturn("00:00:00");
        when(REQUEST.getParameter("finishTime")).thenReturn("00:00:00");
        doNothing().when(RESPONSE).sendRedirect(PATH);
        Assert.assertEquals(0,trainDao.findBy("test","test","test").size());

        TRAIN_SERVLET.doPost(REQUEST,RESPONSE);
        Assert.assertNotNull(trainDao.findBy("test","test","test"));

        verify(REQUEST,times(5)).getParameter(anyString());
        verify(RESPONSE).sendRedirect(PATH);
        trainDao.delete(trainDao.findBy("test","test","test").get(0));
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(REQUEST.getRequestDispatcher(PATH+".jsp")).thenReturn(DISPATCHER);
        TRAIN_SERVLET.doGet(REQUEST,RESPONSE);

        verify(REQUEST).getRequestDispatcher(anyString());
        verify(DISPATCHER).forward(REQUEST,RESPONSE);
    }

    @Test
    public void testDoPut() throws ServletException, IOException {
        Train train = new Train("test","test","test","00:00:00","00:00:00");
        trainDao.insert(train);
        train = trainDao.findBy("test","test","test").get(0);
        when(REQUEST.getParameter("train_id")).thenReturn(String.valueOf(train.getId()));
        when(REQUEST.getParameter("from")).thenReturn("test1");
        when(REQUEST.getParameter("to")).thenReturn("test");
        when(REQUEST.getParameter("date")).thenReturn("test");
        when(REQUEST.getParameter("startTime")).thenReturn("00:00:00");
        when(REQUEST.getParameter("finishTime")).thenReturn("00:00:00");
        doNothing().when(RESPONSE).sendRedirect(PATH);
        Assert.assertNotNull(train);

        TRAIN_SERVLET.doPut(REQUEST,RESPONSE);
        train = trainDao.findBy("test1","test","test").get(0);
        Assert.assertEquals("test1",train.getFrom());
        verify(REQUEST,times(6)).getParameter(anyString());
        verify(RESPONSE).sendRedirect(PATH);
        trainDao.delete(train);
    }

    @Test
    public void testDoDelete() throws IOException, ServletException {
        Train train = new Train("test","test","test","00:00:00","00:00:00");
        trainDao.insert(train);
        train = trainDao.findBy("test","test","test").get(0);
        when(REQUEST.getParameter("train_id")).thenReturn(String.valueOf(train.getId()));
        doNothing().when(RESPONSE).sendRedirect(PATH);
        Assert.assertNotNull(train);

        TRAIN_SERVLET.doDelete(REQUEST,RESPONSE);
        Assert.assertEquals(0,trainDao.findBy("test","test","test").size());

        verify(REQUEST).getParameter("train_id");
        verify(RESPONSE).sendRedirect(PATH);
    }
}