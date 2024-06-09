package domain;

import controllers.LoginScreenController;
import operations.UserOperations;
import structures.lists.ListException;

public class UserSession {

    LoginScreenController LSC = new LoginScreenController();
    UserOperations UO = new UserOperations();

    private static UserSession instance;

    private User loggedUser = UO.getUserByUsername(String.valueOf(LSC.getTxf_user()));

    private UserSession() throws ListException {}

    public static UserSession getInstance() throws ListException {
        if (instance == null)
            instance = new UserSession();

        return instance;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}

