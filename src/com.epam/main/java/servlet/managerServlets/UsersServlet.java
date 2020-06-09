package servlet.managerServlets;

import entity.User;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "UsersServlet", urlPatterns = "users")
public class UsersServlet extends HttpServlet {
    private final DaoFactory FACTORY = new Factory();
    private Connection connection;

    public void init(ServletConfig servletConfig) {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/manageMainPage.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int userId = Integer.parseInt(request.getParameter("user_id"));
        try{
            connection = FACTORY.getConnection();
            GenericDao<User> userDao = new UserDao(connection);
            User user = userDao.getByKey("id",userId);
            user.setRole("MANAGER");
            userDao.update(user);
        }finally {
            FACTORY.closeConnection(connection);
        }
        response.sendRedirect("/manageMainPage");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int userId = Integer.parseInt(request.getParameter("user_id"));
        try{
            connection = FACTORY.getConnection();
            GenericDao<User> userDao = new UserDao(connection);
            User user = userDao.getByKey("id",userId);
            userDao.delete(user);
        }finally {
            FACTORY.closeConnection(connection);
        }
        response.sendRedirect("/manageMainPage");
    }
}
