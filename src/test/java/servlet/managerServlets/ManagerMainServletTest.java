package servlet.managerServlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ManagerMainServletTest extends Mockito {
    final HttpServletRequest REQUEST = mock(HttpServletRequest.class);
    final HttpServletResponse RESPONSE = mock(HttpServletResponse.class);
    final RequestDispatcher DISPATCHER = mock(RequestDispatcher.class);
    final String PATH = "/manageMainPage.jsp";
    ManageMainServlet servlet;

    @Before
    public void setUp(){
        servlet = new ManageMainServlet();
    }

    @Test
    public void testManagerMainServlet()throws IOException, ServletException{
        when(REQUEST.getRequestDispatcher(PATH)).thenReturn(DISPATCHER);
        servlet.doGet(REQUEST,RESPONSE);

        verify(REQUEST, times(3)).setAttribute(anyString(), anyList());

        verify(DISPATCHER).forward(REQUEST,RESPONSE);
    }
}
