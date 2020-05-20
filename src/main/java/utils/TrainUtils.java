package utils;

import entity.Train;

import javax.servlet.http.HttpSession;

public class  TrainUtils {
    public static void storeLoggedUser(HttpSession session, Train train) {
        session.setAttribute("selected_train", train);
    }

    public static Train getSelectedTrain(HttpSession session) {
        return (Train) session.getAttribute("selected_train");
    }
}
