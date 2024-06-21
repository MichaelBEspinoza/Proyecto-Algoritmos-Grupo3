package controllers;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import operations.UserOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class AdminEditProfilesController {
    @javafx.fxml.FXML
    private ComboBox<String> cb_role;
    @javafx.fxml.FXML
    private TextField txf_city;
    @javafx.fxml.FXML
    private TextField txf_id;
    @javafx.fxml.FXML
    private TextField txf_email;
    @javafx.fxml.FXML
    private TextField txf_name;
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Circle circleImage;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextField editThisTXF;
    @javafx.fxml.FXML
    private Button changePasswordText;
    @javafx.fxml.FXML
    private Button saveChangesButton;

    UserOperations UO = new UserOperations();
    User foundUser;
    @javafx.fxml.FXML
    private Button deleteButton;
    @javafx.fxml.FXML
    private ComboBox<String> cb_Place;
    @javafx.fxml.FXML
    private ComboBox<String> cb_Country;

    public void initialize() {
        cb_Place.getItems().addAll("Ciudad Rodrigo Facio", "San Ramón", "Grecia",
                "Turrialba", "Paraíso", "Guápiles",
                "Liberia", "Santa Cruz", "Puerto Limón",
                "Puntarenas", "Región Brunca");
        cb_Country.getItems().addAll("Argentina", "Belice", "Bolivia", "Brasil", "Canadá", "Chile", "Colombia", "Costa Rica", "Cuba",
                "Ecuador", "El Salvador", "Estados Unidos", "Guatemala", "Haití", "Honduras", "México", "Nicaragua", "Panamá",
                "Paraguay", "Perú", "Rep. Dominicana", "Uruguay", "Venezuela");
        foundUser = new User();
        clearAllFields();

        txf_name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z ]*")) {
                txf_name.setText(oldValue);
            }
        });

        txf_city.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z ]*")) {
                txf_city.setText(oldValue);
            }
        });

        txf_id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_id.setText(oldValue);
            }
        });

        txf_email.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                if (!isValidEmail(txf_email.getText())) {
                    util.UtilityFX.alert("Email inválido", "Por favor, ingrese una dirección de correo electrónico de Gmail válida.");
                    txf_email.requestFocus();
                }
        });
    }

    private void clearAllFields() {
        txf_name.clear();
        txf_id.clear();
        txf_email.clear();
        cb_role.getSelectionModel().select(null);
        cb_Country.getSelectionModel().select(null);
        txf_city.clear();
        cb_Place.getSelectionModel().select(null);
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
    public void mainPageOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void changePasswordOnAction(ActionEvent actionEvent) {
        loadPage("adminChangePassword.fxml");
    }

    @javafx.fxml.FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
        if (fieldsAreEmpty())
            util.UtilityFX.alert("Error: cambios no aplicables",
                    "Uno o más campos están vacíos. Todos los campos deben contener información.\nInténtelo de nuevo.");
         else {
            foundUser.setName(txf_name.getText());
            foundUser.setId(Integer.parseInt(txf_id.getText()));
            foundUser.setEmail(txf_email.getText());
            foundUser.setRole(foundUser.stringToRole(cb_role.getValue()));
            foundUser.setCountry(cb_Country.getValue());
            foundUser.setCity(txf_city.getText());
            foundUser.setPlace(cb_Place.getValue());
            UO.updateUser(foundUser);

            UO.updateProfile(foundUser);
            UO.saveUsersToFile("users.txt");
            util.UtilityFX.alert("Cambios efectuados", "Se han realizado los cambios acordemente.");
        }
    }

    private boolean fieldsAreEmpty() {
        return txf_name.getText().isEmpty() || txf_id.getText().isEmpty() ||
                txf_email.getText().isEmpty() || cb_role.getValue() == null ||
                cb_Country.getValue() == null || txf_city.getText().isEmpty() || cb_Place.getValue() == null;
    }

    @javafx.fxml.FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        if (foundUser != null && UO.userExists(foundUser)) {
            UO.deleteUser(foundUser.getId());
            UO.saveUsersToFile("users.txt");
            util.UtilityFX.alert("Eliminado","Usuario eliminado con éxito.");
        } else
            util.UtilityFX.alert("Usuario no encontrado", "El usuario no pudo ser encontrado por su ID.\nInténtelo de nuevo.");
    }

    @javafx.fxml.FXML
    public void searchUserOnAction(ActionEvent actionEvent) {
        foundUser = UO.readUser(Integer.parseInt(editThisTXF.getText()));
        if (foundUser != null && UO.userExists(foundUser)) {
            util.UtilityFX.alert("Modificar", "Usuario encontrado con éxito.\nHabilitando campos...");
            txf_name.setDisable(false);
            txf_id.setDisable(false);
            txf_email.setDisable(false);
            cb_role.setDisable(false);
            cb_Country.setDisable(false);
            txf_city.setDisable(false);
            cb_Place.setDisable(false);
            saveChangesButton.setDisable(false);
            changePasswordText.setDisable(false);
            deleteButton.setDisable(false);

            txf_name.setText(foundUser.getName());
            txf_id.setText(String.valueOf(foundUser.getId()));
            txf_email.setText(foundUser.getEmail());
            cb_role.getSelectionModel().select(foundUser.roleToString());
            cb_Country.getSelectionModel().select(foundUser.getCountry());
            txf_city.setText(foundUser.getCity());
            cb_Place.getSelectionModel().select(foundUser.getPlace());
        } else
            util.UtilityFX.alert("Usuario no encontrado", "El usuario no pudo ser encontrado por su ID.\nInténtelo de nuevo.");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.endsWith("@gmail.com");
    }
}

