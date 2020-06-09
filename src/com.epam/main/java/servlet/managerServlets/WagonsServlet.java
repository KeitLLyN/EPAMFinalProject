package servlet.managerServlets;

import entity.Wagon;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
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

@WebServlet(name = "WagonsServlet", urlPatterns = "wagons")
public class WagonsServlet extends HttpServlet {
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
        int trainId = Integer.parseInt(request.getParameter("train_id"));
        int numberOfSeats = Integer.parseInt(request.getParameter("seats"));
        int price = Integer.parseInt(request.getParameter("price"));
        String service = request.getParameter("service");
        try{
            connection = FACTORY.getConnection();
            GenericDao<Wagon> wagonDao = new WagonDAO(connection);
            Wagon wagon = new Wagon(service,price,numberOfSeats,trainId);
            wagonDao.insert(wagon);
        }finally {
            FACTORY.closeConnection(connection);
        }
        response.sendRedirect("/manageMainPage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/manageMainPage.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPut (HttpServletRequest request, HttpServletResponse response) throws IOException{
        int id = Integer.parseInt(request.getParameter("wagon_id"));
        try{
            connection = FACTORY.getConnection();
            GenericDao<Wagon> wagonDao = new WagonDAO(connection);
            Wagon wagon = wagonDao.getByKey("wagon_id", id);
            wagon.setNumberOfSeats(Integer.parseInt(request.getParameter("seats")));
            wagon.setPrice(Integer.parseInt(request.getParameter("price")));
            wagon.setServiceClass(request.getParameter("service"));
            wagonDao.update(wagon);
        }finally {
            FACTORY.closeConnection(connection);
        }
        response.sendRedirect("/manageMainPage");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws  IOException{
        int id = Integer.parseInt(request.getParameter("wagon_id"));
        try{
            connection = FACTORY.getConnection();
            GenericDao<Wagon> wagonDao = new WagonDAO(connection);
            Wagon wagon = wagonDao.getByKey("wagon_id", id);
            wagonDao.delete(wagon);
        }finally {
            FACTORY.closeConnection(connection);
        }
        response.sendRedirect("/manageMainPage");
    }
}
