package servlet.loginServlets;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "RegisterServlet", urlPatterns = "register")
public class RegisterServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(RegisterServlet.class);
    private DaoFactory factory;
    private Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("New user registration");
        String name = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = "USER";
        try{
            factory = new Factory();
            connection = factory.getConnection();
            GenericDao<User> userDao = new UserDao(connection);
            List<User> users = userDao.findBy(email, password);
            User user = new User(name,email,password, role);
            if (users == null){
                userDao.insert(user);
                LoginServlet.storeLoggedUser(request,response,user);
            }else{
                try (PrintWriter writer = response.getWriter()) {
                    writer.println("Such email is already exist");
                }
            }
        }finally {
            factory.closeConnection(connection);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request,response);
    }
}
