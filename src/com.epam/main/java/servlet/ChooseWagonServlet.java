package servlet;

import entity.Train;
import entity.Wagon;
import utils.dao.interfaces.DaoFactory;
import utils.dao.interfaces.GenericDao;
import utils.mysql.Factory;
import utils.mysql.WagonDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "ChooseWagonServlet", urlPatterns = "main/choose")
public class ChooseWagonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Train train = (Train) session.getAttribute("train");
        String service = request.getParameter("wagonService");
        List<Wagon> wagons = train.getWagonsByService(service);

        request.setAttribute("service",service);
        request.setAttribute("wagons",wagons);

        request.getRequestDispatcher("/chooseWagon.jsp").forward(request,response);
    }
}
