package utils;

import java.util.*;

/**
 * SecurityConfig помогает конфигурировать роли и функции, позволяющие иметь с той ролью
 * */
public class SecurityConfig {
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_USER = "USER";

    /**String: Role
    *List<String>: urlPatterns.*/
    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        List<String> urlPatternsEmployee = new ArrayList<>();
        urlPatternsEmployee.add("/main");
        mapConfig.put(ROLE_USER, urlPatternsEmployee);

        List<String> urlPatternsManager = new ArrayList<>();
        urlPatternsManager.add("/main");
        urlPatternsManager.add("/manageMainPage");
        mapConfig.put(ROLE_MANAGER, urlPatternsManager);
    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }
}
