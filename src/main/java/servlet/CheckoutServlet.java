package servlet;

import entity.User;
import entity.Wagon;
import utils.AppUtils;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.WagonDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "CheckoutServlet", urlPatterns = "main/checkout")
public class CheckoutServlet extends HttpServlet {
    private User user = null;
    private int countOfSeats;
    private DaoFactory factory;
    private Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String wagonID = request.getParameter("wagonID");
        try {
            factory = new Factory();
            connection = factory.getConnection();
            GenericDao<Wagon> wagonDao = new WagonDAO(connection);
            Wagon wagon = wagonDao.getByKey("wagon_id",Integer.parseInt(wagonID));
            wagon.decreaseSeatsByValue(countOfSeats);
            wagonDao.update(wagon);
        }finally {
            factory.closeConnection(connection);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        user = AppUtils.getLoggedUser(request.getSession());
        request.setAttribute("user",user);
        request.setAttribute("price",request.getParameter("wagonPrice"));
        request.setAttribute("service",request.getParameter("wagonService"));
        request.setAttribute("wagonID",request.getParameter("wagonID"));
        countOfSeats = Integer.parseInt(request.getParameter("countOfSeats"));

        request.setAttribute("countOfSeats", countOfSeats);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/checkout.jsp");
        dispatcher.forward(request,response);
    }
}
