package controllers;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import operations.SecurityOperations;
import operations.UserOperations;
import structures.lists.*;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

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
    private BorderPane bp;
    @javafx.fxml.FXML
    private Button saveChangesButton;

    UserOperations UO = new UserOperations();
    User foundUser;
    SecurityOperations SO = new SecurityOperations();
    @javafx.fxml.FXML
    private ComboBox<User> cb_Users;

    public void initialize() {
        UO.loadUsersFromFile("users.txt");
        fillUserComboBox();
    }

    private void fillUserComboBox() {
        try {
            CircularDoublyLinkedList users = UO.listUsers();
            for (int i = 1; i < users.size(); i++) {
                User user = (User) users.getNode(i).data;
                cb_Users.getItems().add(user);
            }
            cb_Users.setCellFactory(lv -> new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    setText(empty ? null : user.getId() + " - " + user.getName());
                }
            });
            cb_Users.setButtonCell(new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    setText(empty ? null : user.getId() + " - " + user.getName());
                }
            });
            cb_Users.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    foundUser = newValue;
                    enableFields();
                }
            });
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

    private void enableFields() {
        txf_newPassword.setDisable(false);
        txf_confirmPassword.setDisable(false);
        saveChangesButton.setDisable(false);
    }

    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
        if (txf_newPassword.getText().isEmpty() || txf_confirmPassword.getText().isEmpty()) {
            util.UtilityFX.alert("Error al cambiar contraseña", "Uno o más campos están vacíos. Inténtelo de nuevo.");
            return;
        }

        if (txf_newPassword.getText().equals(txf_confirmPassword.getText())) {
            String newPass = SO.encryptPassword(txf_newPassword.getText());
            foundUser.setPassword(newPass);
            UO.updateUser(foundUser);
            UO.saveUsersToFile("users.txt");
            util.UtilityFX.alert("Éxito", "La contraseña del usuario " + foundUser.getName() + " fue cambiada exitosamente.");
        } else {
            util.UtilityFX.alert("Error al cambiar contraseña", "Las contraseñas no coinciden. Inténtelo de nuevo.");
        }
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

