package servlet.loginServlets;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServletTest extends Mockito {
    final HttpServletRequest REQUEST = mock(javax.servlet.http.HttpServletRequest .class);
    final HttpServletResponse RESPONSE = mock(HttpServletResponse.class);
    final HttpSession SESSION = mock(HttpSession.class);
    final LogoutServlet LOGOUT_SERVLET = new LogoutServlet();


    @Test
    public void testDoGet() throws ServletException, IOException {
        when(REQUEST.getSession()).thenReturn(SESSION);
        when(REQUEST.getContextPath()).thenReturn("");
        LOGOUT_SERVLET.doGet(REQUEST,RESPONSE);

        verify(REQUEST).getSession();
        verify(SESSION).invalidate();
        verify(RESPONSE).sendRedirect(anyString());
    }
}