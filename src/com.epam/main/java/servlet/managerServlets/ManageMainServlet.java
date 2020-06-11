package servlet.managerServlets;

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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "manageMainServlet", urlPatterns = "manageMainPage")
public class ManageMainServlet extends HttpServlet {
    private final DaoFactory FACTORY = new Factory();
    private Connection connection;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Train> trains;
        List<User> users;
        List<Wagon> wagons;
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
        request.getRequestDispatcher("/manageMainPage.jsp").forward(request,response);
//        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/manageMainPage.jsp");
//        dispatcher.forward(request,response);
    }

}
