package servlet;

import entity.Train;
import entity.Wagon;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChooseWagonServletTest extends Mockito {
    final HttpServletRequest REQUEST = mock(HttpServletRequest.class);
    final HttpServletResponse RESPONSE = mock(HttpServletResponse.class);
    final RequestDispatcher DISPATCHER = mock(RequestDispatcher.class);
    final HttpSession SESSION = mock(HttpSession.class);
    final String PATH = "/chooseWagon.jsp";
    ChooseWagonServlet servlet;

    @Before
    public void setUp(){
        servlet = new ChooseWagonServlet();
    }

    @Test
    public void testChooseWagonServlet() throws IOException, ServletException {
        Train train = new Train();
        List<Wagon> wagons = new ArrayList<>();
        wagons.add(new Wagon("test",1,1,1));
        train.setWagons(wagons);
        when(REQUEST.getSession()).thenReturn(SESSION);
        when(SESSION.getAttribute("train")).thenReturn(train);
        when(REQUEST.getParameter("wagonService")).thenReturn("");
        when(REQUEST.getRequestDispatcher(PATH)).thenReturn(DISPATCHER);

        servlet.doGet(REQUEST,RESPONSE);

        verify(REQUEST).getSession();
        verify(REQUEST).getParameter(anyString());
        verify(SESSION).getAttribute(anyString());
        verify(REQUEST, times(2)).setAttribute(anyString(),any());

    }
}
