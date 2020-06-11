package servlet;

import entity.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.dao.JDBCDao;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.TrainDao;

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
import java.util.stream.Collectors;

@WebServlet(name = "MainServlet", urlPatterns = "main")
public class MainServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(MainServlet.class);

    private DaoFactory factory;
    private Connection connection;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from = request.getParameter("fromCountry");
        String to = request.getParameter("toCountry");
        String date = request.getParameter("date"); // yyyy-mm-dd
        List<Train> trains;
        try{
            factory = new Factory();
            connection = factory.getConnection();
            GenericDao<Train> trainDao = new TrainDao(connection);
            trains = trainDao.findBy(from,to,date);
            trains = trains.stream()
                    .filter(train -> train.getWagons().size() > 0)
                    .collect(Collectors.toList());
        }finally {
            factory.closeConnection(connection);
        }
        request.setAttribute("trains", trains);
        request.getRequestDispatcher("/main.jsp").forward(request,response);
    }
}
