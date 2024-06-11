package controllers;

import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import operations.SecurityOperations;
import operations.UserOperations;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class ChangePasswordController {
    @FXML
    private TextField txf_confirmPassword;
    @FXML
    private ImageView imageView;
    @FXML
    private Circle circleImage;
    @FXML
    private TextField txf_newPassword;
    @FXML
    private BorderPane bp;

    private User loggedUser = UserSession.getInstance().getLoggedUser();
    private UserOperations UO = new UserOperations();
    private SecurityOperations SO = new SecurityOperations();

    public ChangePasswordController() throws ListException {
        // Cargar los usuarios desde el archivo
        UO.loadUsersFromFile("users.txt");
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            // Manejar la excepción y mostrar un mensaje de error
            showAlert("Error", "No se pudo cargar la página: " + page);
            e.printStackTrace();
        }
    }

    @FXML
    public void mainPageOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
        String newPassword = txf_newPassword.getText();
        String confirmPassword = txf_confirmPassword.getText();

        // Verificar si la nueva contraseña y la confirmación coinciden
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error al cambiar contraseña", "La nueva contraseña debe ser la misma en ambos campos para actualizar. Inténtelo de nuevo.");
            return;
        }

        // Verificar si la nueva contraseña es válida
        if (!isValidPassword(newPassword)) {
            showAlert("Error al cambiar contraseña", "La contraseña no cumple con uno o más parámetros.\n" +
                    "La contraseña debe tener:\n" +
                    "1. Al menos 8 caracteres.\n" +
                    "2. Letras mayúsculas y minúsculas.\n" +
                    "3. Un carácter especial ('*', '!', '$', etc.).");
            return;
        }

        // Desencriptar la contraseña actual
        String decryptedPassword = SO.decryptPassword(loggedUser.getPassword());

        // Actualizar la contraseña
        try {
            // Encriptar la nueva contraseña
            String encryptedNewPassword = SO.encryptPassword(newPassword);
            boolean passwordChanged = UO.changePassword(loggedUser.getId(), encryptedNewPassword);
            if (passwordChanged) {
                // Actualizar la contraseña en el objeto del usuario actual
                loggedUser.setPassword(encryptedNewPassword);

                // Actualizar el usuario en la lista
                boolean userUpdated = UO.updateUser(loggedUser);
                if (userUpdated) {
                    // Guardar los usuarios en el archivo
                    UO.saveUsersToFile("users.txt");
                    showAlert("Contraseña cambiada", "La contraseña del usuario '" + loggedUser.getName() + "' ha sido modificada exitosamente.");
                } else {
                    showAlert("Error al cambiar contraseña", "No se pudo actualizar el usuario. Inténtelo de nuevo.");
                }
            } else {
                showAlert("Error al cambiar contraseña", "No se pudo cambiar la contraseña. Inténtelo de nuevo.");
            }
        } catch (Exception e) {
            showAlert("Error", "Ocurrió un error al actualizar la contraseña.");
            e.printStackTrace();
        }
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) return false;
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?].*")) return false;
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
