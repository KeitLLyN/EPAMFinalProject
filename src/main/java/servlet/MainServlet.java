package servlet;

import entity.Train;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.TrainDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "MainServlet", urlPatterns = "main")
public class MainServlet extends HttpServlet {
    private List<Train> trains = null;
    private DaoFactory factory;
    private Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from = request.getParameter("fromCountry");
        String to = request.getParameter("toCountry");
        String date = request.getParameter("date"); // yyyy-mm-dd
        try{
            factory = new Factory();
            connection = factory.getConnection();
            GenericDao<Train> trainDao = new TrainDao(connection);
            trains = trainDao.findBy(new String[]{from,to,date});
        }finally {
            factory.closeConnection(connection);
        }
        request.setAttribute("trains",trains);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/main.jsp");
        dispatcher.forward(request,response);
    }
}
