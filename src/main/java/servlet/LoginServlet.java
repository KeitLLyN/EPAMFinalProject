package servlet;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.AppUtils;
import utils.dao.JDBCDao;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = "login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(LoginServlet.class);
    private DaoFactory factory;
    private Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("psw");
        try {
            factory = new Factory();
            connection = factory.getConnection();
            GenericDao<User> userDao = new UserDao(connection);
            List<User> users = userDao.findBy(new String[]{userName, userPassword});
            if (users == null){
                String errorMessage = "Check your user name and password";
                request.setAttribute("errorMessage",errorMessage);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/errorLogin.jsp");
                dispatcher.forward(request,response);
                return;
            }
            storeLoggedUser(request,response,users.get(0));
        }finally {
            factory.closeConnection(connection);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.jsp");
        dispatcher.forward(request,response);
    }

    protected static void storeLoggedUser(HttpServletRequest request,HttpServletResponse response, User user) throws IOException{
        AppUtils.storeLoggedUser(request.getSession(),user);
        int redirectId = -1;
        try {
            redirectId = Integer.parseInt(request.getParameter("redirectId"));
        } catch (NumberFormatException e) {
            LOG.warn(e.getMessage());
        }
        String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
        if (requestUri != null) {
            response.sendRedirect(requestUri);
        } else {
            response.sendRedirect(request.getContextPath() + "/main");
        }
    }
}
