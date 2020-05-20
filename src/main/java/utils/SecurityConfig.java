package utils;

import java.util.*;

public class SecurityConfig {
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";

    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        List<String> urlPatternsEmployee = new ArrayList<>();
        urlPatternsEmployee.add("/main");
//        urlPatternsEmployee.add("/employeeTask");
        mapConfig.put(ROLE_EMPLOYEE, urlPatternsEmployee);

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
