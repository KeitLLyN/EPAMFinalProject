package servlet.managerServlets;

import com.google.protobuf.CodedOutputStream;
import entity.Train;
import entity.User;
import entity.Wagon;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.TrainDao;
import utils.mysql.UserDao;
import utils.mysql.WagonDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "manageMainServlet", urlPatterns = "manageMainPage")
public class ManageMainServlet extends HttpServlet {
    private final DaoFactory FACTORY = new Factory();
    private Connection connection;
    private List<Train> trains;
    private List<User> users;
    private List<Wagon> wagons;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            connection = FACTORY.getConnection();
            GenericDao<Train> trainDao = new TrainDao(connection);
            GenericDao<User> userDao = new UserDao(connection);
            GenericDao<Wagon> wagonDao = new WagonDAO(connection);

            trains = trainDao.getAll();
            users = userDao.getAll();
            wagons = wagonDao.getAll();
        }finally {
            FACTORY.closeConnection(connection);
        }
        request.setAttribute("trains", trains);
        request.setAttribute("users", users);
        request.setAttribute("wagons", wagons);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/manageMainPage.jsp");
        dispatcher.forward(request,response);
    }

}
