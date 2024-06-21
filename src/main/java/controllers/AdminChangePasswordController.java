package controllers;

import domain.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import operations.SecurityOperations;
import operations.UserOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

public class AdminChangePasswordController {
    @javafx.fxml.FXML
    private TextField txf_confirmPassword;
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Circle circleImage;
    @javafx.fxml.FXML
    private TextField txf_newPassword;
    @javafx.fxml.FXML
    private TextField searchThisTXF;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private Button saveChangesButton;

    UserOperations UO = new UserOperations();
    User foundUser;
    SecurityOperations SO = new SecurityOperations();

    public void initialize() {
        UO.loadUsersFromFile("users.txt");
        foundUser = new User();
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void searchOnAction(Event event) {
        searchThisTXF.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                foundUser = UO.readUser(Integer.parseInt(searchThisTXF.getText()));
                if (userFound() && foundUser != null) {
                    txf_newPassword.setDisable(false);
                    txf_confirmPassword.setDisable(false);
                    saveChangesButton.setDisable(false);
                }
            }
        });
    }

    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
        if (txf_newPassword.getText().isEmpty() || txf_confirmPassword.getText().isEmpty()) {
            util.UtilityFX.alert("Error al cambiar contraseña", "Uno o más campos están vacíos. Inténtelo de nuevo.");
            return;
        }

        if (txf_newPassword.getText().equals(txf_confirmPassword.getText())) {
            String newPass = SO.encryptPassword(txf_newPassword.getText()),
            changedPassMessage = "\"Estimado/a " + foundUser.getName() + ",\n" +
                    "\n" +
                    "Le informamos que su contraseña ha sido cambiada correctamente a " + newPass + " por un administrador de la plataforma MyOnlineLearning. " +
                    "Si tiene alguna pregunta o necesita asistencia adicional, no dude en ponerse en contacto con nuestro equipo de soporte.\n" +
                    "\n" +
                    "Atentamente, El Equipo de MyOnlineLearning" +
                    "Enviado el " + LocalDate.now() + " a las " + LocalTime.now() + "\"";
            foundUser.setPassword(newPass);
            UO.updateUser(foundUser);
            UO.saveUsersToFile("users.txt");
            UO.sendEmailNotification(foundUser, changedPassMessage);
            util.UtilityFX.alert("Éxito", "La contraseña del usuario " + foundUser.getName() + " fue cambiada exitosamente.");
        } else util.UtilityFX.alert("Error al cambiar contraseña", "Las campos difieren en sus contenidos. Inténtelo de nuevo.");
    }

    private boolean userFound() {
        return UO.readUser(Integer.parseInt(searchThisTXF.getText())).getId() == Integer.parseInt(searchThisTXF.getText());
    }
}
