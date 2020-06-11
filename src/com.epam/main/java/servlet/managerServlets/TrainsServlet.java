package servlet.managerServlets;

import entity.Train;
import entity.Wagon;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.TrainDao;
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

@WebServlet(name = "TrainsServlet", urlPatterns = "trains")
public class TrainsServlet extends HttpServlet {
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
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String date = request.getParameter("date");
        String startTime = request.getParameter("startTime");
        String finishTime = request.getParameter("finishTime");
        try{
            connection = FACTORY.getConnection();
            GenericDao<Train> trainDao = new TrainDao(connection);
            Train train = new Train(from,to,date,startTime,finishTime);
            trainDao.insert(train);
        }finally {
            FACTORY.closeConnection(connection);
        }
        response.sendRedirect("/manageMainPage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/manageMainPage.jsp").forward(request,response);
    }

    @Override
    protected void doPut (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("train_id"));
        try{
            connection = FACTORY.getConnection();
            GenericDao<Train> trainDao = new TrainDao(connection);
            Train train = trainDao.getByKey("train_id", id);
            train.setDate(request.getParameter("date"));
            train.setFrom(request.getParameter("from"));
            train.setTo(request.getParameter("to"));
            train.setStartTime(request.getParameter("startTime"));
            train.setFinishTime(request.getParameter("finishTime"));
            trainDao.update(train);
        }finally {
            FACTORY.closeConnection(connection);
        }
        response.sendRedirect("/manageMainPage");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        int id = Integer.parseInt(request.getParameter("train_id"));
        try{
            connection = FACTORY.getConnection();
            GenericDao<Train> trainDao = new TrainDao(connection);
            GenericDao<Wagon> wagonDao = new WagonDAO(connection);
            Train trainToDelete = trainDao.getByKey("train_id", id);
            trainToDelete.getWagons().forEach(wagonDao::delete);
            trainDao.delete(trainToDelete);

        }finally {
            FACTORY.closeConnection(connection);
        }
        response.sendRedirect("/manageMainPage");
    }
}
