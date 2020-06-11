package filter;

import entity.User;
import request.UserRoleRequestWrapper;
import utils.AppUtils;
import utils.SecurityUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * SecurityFilter выполняет обязанность проверки всех request
 * перед тем как позволить получить доступ в защищенные страницы
 * */
@WebFilter("/*")
public class SecurityFilter implements Filter {
    public void destroy() {
    }

    /**
     * @param req запрос от пользователя
     * @param resp ответ от сервера
     * @param chain список фильтров
     *
     * @throws ServletException ошибка сервлета
     * @throws IOException ошибка ввода input/output
     *
     * */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String servletPath = request.getServletPath();

        User user = AppUtils.getLoggedUser(request.getSession());

        if (servletPath.equals("/login")){
            chain.doFilter(request,response);
            return;
        }
        HttpServletRequest wrapRequest = request;
        if (user != null) {
            String userName = user.getName();
            String role = user.getRole();
            wrapRequest = new UserRoleRequestWrapper(userName, role, request);
        }
        if (SecurityUtils.isSecurityPage(request)) {
            if (user == null) {
                String requestUri = request.getRequestURI();
                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }

            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/accessDenied.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(wrapRequest, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
