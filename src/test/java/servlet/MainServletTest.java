package servlet;

import entity.Train;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class MainServletTest extends Mockito {
    final HttpServletRequest REQUEST = mock(HttpServletRequest.class);
    final HttpServletResponse RESPONSE = mock(HttpServletResponse.class);
    final RequestDispatcher DISPATCHER = mock(RequestDispatcher.class);
    final String MAIN_PATH = "/main.jsp";
    MainServlet mainServlet;

    @Before
    public void setUp(){
        mainServlet = new MainServlet();
    }

    @Test
    public void testMainServlet() throws IOException, ServletException {
        when(REQUEST.getRequestDispatcher(MAIN_PATH)).thenReturn(DISPATCHER);
        mainServlet.doGet(REQUEST,RESPONSE);

        verify(REQUEST).getParameter("fromCountry");
        verify(REQUEST).getParameter("toCountry");
        verify(REQUEST).getParameter("date");
        verify(REQUEST,never()).getSession();
        verify(REQUEST).setAttribute("trains", new ArrayList<Train>());

        verify(DISPATCHER).forward(REQUEST,RESPONSE);
    }
}
