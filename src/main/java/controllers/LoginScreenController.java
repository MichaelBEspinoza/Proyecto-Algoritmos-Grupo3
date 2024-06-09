package controllers;

import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import operations.UserOperations;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginScreenController {

    @FXML
    private Pane pane1;
    @FXML
    private TextField txf_user;
    @FXML
    private TextField txf_password;
    @FXML
    private BorderPane bp;

    private final UserOperations userOperations = new UserOperations();

    public void initialize() {
        txf_user.setText("");
        txf_password.setText("");
    }

    @FXML
    public void accessOnAction(ActionEvent actionEvent) {
        String username = txf_user.getText();
        String password = txf_password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            util.UtilityFX.alert("Error", "Uno o ambos campos están vacíos. Complete ambos campos para continuar.");
            return;
        }

        try {
            if (authenticate(username, password)) {
                User loggedInUser = userOperations.getUserByUsername(username);
                UserSession.getInstance().setLoggedUser(loggedInUser);
                loadPage("mainPage.fxml");

            } else util.UtilityFX.alert("Error", "Nombre de usuario o contraseña incorrectos.");
        } catch (ListException e) {
            util.UtilityFX.alert("Error", "Ocurrió un error al acceder a la lista de usuarios.");
        }
    }

    private boolean authenticate(String username, String password) throws ListException {
        User user = userOperations.getUserByUsername(username);
        if (userOperations.userExists(user))
            return user != null && user.getPassword().equals(password);
        return false;
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            util.UtilityFX.alert("Error", "No se pudo cargar la página: " + page);
        }
    }

    @FXML
    public void registerOnAction(Event event) {loadPage("registerScreen.fxml");}

    public String getTxf_user() {
        return txf_user.getText();
    }

}
