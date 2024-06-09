package controllers;

import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import operations.SecurityOperations;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class LoginScreenController {

    @FXML
    private Pane pane1;

    public TextField getTxf_user() {
        return txf_user;
    }

    public void setTxf_user(TextField txf_user) {
        this.txf_user = txf_user;
    }

    @FXML
    private TextField txf_user;
    @FXML
    private TextField txf_password;
    @FXML
    private BorderPane bp;

    private final SecurityOperations securityOperations = new SecurityOperations();

    public void initialize() {
        txf_user.setText("");
        txf_password.setText("");
    }

    @FXML
    public void accessOnAction(ActionEvent actionEvent) throws ListException {
        String username = txf_user.getText();
        String password = txf_password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            util.UtilityFX.alert("Error", "Uno o ambos campos están vacíos. Complete ambos campos para continuar.");
            return;
        }

        if (authenticate(username, password)) {
            try {
                User loggedInUser = securityOperations.getUserByUsername(username);
                UserSession.getInstance().setLoggedUser(loggedInUser);
                loadPage("mainPage.fxml");
            } catch (ListException e) {
                util.UtilityFX.alert("Error", "Ocurrió un error al acceder a la lista de usuarios.");
            }
        } else {
            util.UtilityFX.alert("Error", "Nombre de usuario o contraseña incorrectos.");
        }
    }

    private boolean authenticate(String username, String password) throws ListException {
        return securityOperations.login(username, password);
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
    public void registerOnAction(Event event) {
        loadPage("registerScreen.fxml");
    }
}
