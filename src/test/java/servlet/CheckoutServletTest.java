package servlet;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckoutServletTest extends Mockito {
    final HttpServletRequest REQUEST = mock(HttpServletRequest.class);
    final HttpServletResponse RESPONSE = mock(HttpServletResponse.class);
    final RequestDispatcher DISPATCHER = mock(RequestDispatcher.class);
    final HttpSession SESSION = mock(HttpSession.class);
    final String PATH = "/checkout.jsp";
    final CheckoutServlet SERVLET = new CheckoutServlet();

    @Test
    public void testDoGetMethod() throws ServletException, IOException {
        when(REQUEST.getRequestDispatcher(PATH)).thenReturn(DISPATCHER);
        when(REQUEST.getSession()).thenReturn(SESSION);
        when(REQUEST.getParameter("countOfSeats")).thenReturn("1");
        SERVLET.doGet(REQUEST,RESPONSE);

        verify(REQUEST,times(5)).setAttribute(anyString(),any());
        verify(REQUEST,times(4)).getParameter(anyString());
        verify(REQUEST).getSession();
        verify(DISPATCHER).forward(REQUEST,RESPONSE);
    }
}
