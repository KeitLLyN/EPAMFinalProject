package servlet;

import entity.Train;
import entity.Wagon;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
//        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/chooseWagon.jsp");
//        dispatcher.forward(request,response);
    }
}
