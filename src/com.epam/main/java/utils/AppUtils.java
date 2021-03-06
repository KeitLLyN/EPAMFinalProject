package utils;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servlet.managerServlets.ManageMainServlet;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class AppUtils {
    private static final Logger LOG = LogManager.getLogger(AppUtils.class);
    private static int REDIRECT_ID = 0;

    private static final Map<Integer, String> id_uri_map = new HashMap<>();
    private static final Map<String, Integer> uri_id_map = new HashMap<>();

    public AppUtils(){}

    public static void storeLoggedUser(HttpSession session, User loggedUser) {
        LOG.info("Storing logged user to session");
        session.setAttribute("loggedUser", loggedUser);
    }

    public static User getLoggedUser(HttpSession session) {
        LOG.info("Getting stored user from session");
        User loggedUser = (User) session.getAttribute("loggedUser");
        return loggedUser;
    }
    public static int storeRedirectAfterLoginUrl(HttpSession session, String requestUri) {
        LOG.info("Storing redirect ID");
        Integer id = uri_id_map.get(requestUri);
        if (id == null) {
            id = REDIRECT_ID++;
            uri_id_map.put(requestUri, id);
            id_uri_map.put(id, requestUri);
            return id;
        }
        return id;
    }

    public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
        LOG.info("Returning redirect ID");
        return id_uri_map.get(redirectId);
    }
}
