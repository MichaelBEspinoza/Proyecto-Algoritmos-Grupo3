package controllers;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import operations.SecurityOperations;
import operations.UserOperations;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;
import java.util.Objects;

public class RegisterScreenController {
    @FXML
    private Pane pane1;
    @FXML
    private TextField txf_email;
    @FXML
    private TextField txf_user;
    @FXML
    private BorderPane bp;
    @FXML
    private TextField txf_city;
    @FXML
    private TextField txf_id;
    @FXML
    private ComboBox<String> cb_role;

    UserOperations UO = new UserOperations();
    User forStringToRoleOnly = new User();
    SecurityOperations SO = new SecurityOperations();
    @FXML
    private PasswordField txf_confirmPassword;
    @FXML
    private ComboBox<String> cb_Place;
    @FXML
    private PasswordField txf_password;
    @FXML
    private ComboBox<String> cb_Country;

    public void initialize() {

        cb_role.getItems().addAll("Usuario", "Instructor", "Administrador");
        cb_Place.getItems().addAll("Ciudad Rodrigo Facio", "San Ramón", "Grecia",
                                         "Turrialba", "Paraíso", "Guápiles",
                                         "Liberia", "Santa Cruz", "Puerto Limón",
                                         "Puntarenas", "Región Brunca");
        cb_Country.getItems().addAll("Argentina", "Belice", "Bolivia", "Brasil", "Canadá", "Chile", "Colombia", "Costa Rica", "Cuba",
                                           "Ecuador", "El Salvador", "Estados Unidos", "Guatemala", "Haití", "Honduras", "México", "Nicaragua", "Panamá",
                                           "Paraguay", "Perú", "Rep. Dominicana", "Uruguay", "Venezuela");
        clearAllFields();

        txf_user.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[a-zA-Z ]*")) {
                return change;
            }
            return null;
        }));

        txf_city.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[a-zA-Z ]*")) {
                return change;
            }
            return null;
        }));

        txf_id.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d{0,9}")) {
                return change;
            }
            return null;
        }));

        txf_email.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!isValidEmail(txf_email.getText())) {
                    util.UtilityFX.alert("Email inválido", "Por favor, ingrese una dirección de correo electrónico de Gmail válida.");
                    txf_email.requestFocus();
                }
            }
        });
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        System.out.println(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            //util.UtilityFX.alert("Error", "No se pudo cargar la página: " + page);
            e.printStackTrace();
        }
    }

    @FXML
    public void registerOnAction(ActionEvent actionEvent) throws ListException {

        if (areEmpty()) {
            util.UtilityFX.alert("Campos vacíos", "Todos los campos deben estar llenos al crear una cuenta. Verifique que todos los campos estén llenos antes de proseguir.");
            return;
        }

        if (txf_id.getText().length() != 9) {
            util.UtilityFX.alert("Cédula inválida", "La cédula debe tener exactamente 9 caracteres.");
            return;
        }

        if (!Objects.equals(txf_password.getText(), txf_confirmPassword.getText())) {
            util.UtilityFX.alert("Contraseñas no coinciden", "La contraseña no coincide con la impuesta en el campo de confirmación.\nInténtelo de nuevo.");
            return;
        }

        if (!isValidPassword(txf_password.getText())) {
            util.UtilityFX.alert("La contraseña no es segura", """
                    Su contraseña debe de incluir:\

                    1. Al menos 8 carácteres.\

                    2. Letras mayúsculas y minúsculas.\

                    3. Un signo especial ('*', '!', '$', etc.).\

                    Inténtelo de nuevo.""");
        } else {
            UO.createUser(new User(Integer.parseInt(txf_id.getText()),
                    txf_user.getText(),
                    SO.encryptPassword(txf_password.getText()),
                    txf_email.getText(),
                    forStringToRoleOnly.stringToRole(cb_role.getValue()),
                    cb_Country.getValue(),
                    txf_city.getText(),
                    cb_Place.getValue()));
            loadPage("loginScreen.fxml");
            util.UtilityFX.alert("Registro exitoso", "¡Bienvenido!");
        }
    }

    @FXML
    public void passwordRequirementsOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Requerimientos de contraseña:", """
                La contraseña debe tener:\

                1. Al menos 8 carácteres.\

                2. Letras mayúsculas y minúsculas.\

                3. Un signo especial ('*', '!', '$', etc.).""");
    }

    private void clearAllFields() {
        txf_id.clear();
        txf_email.clear();
        cb_Place.getSelectionModel().select("Ciudad Rodrigo Facio");
        txf_city.clear();
        cb_Country.getSelectionModel().select("Costa Rica");
        cb_role.getSelectionModel().select("Usuario");
        txf_user.clear();
        txf_password.clear();
    }

    private boolean areEmpty() {
        return txf_user.getText().isEmpty() || txf_id.getText().isEmpty() || txf_email.getText().isEmpty() || txf_password.getText().isEmpty()
                || cb_role.getValue() == null || cb_Country.getValue() == null || txf_city.getText().isEmpty() || cb_Place.getValue() == null;
    }

    private boolean isValidPassword(String password) {
        if (password == null) return false;
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?].*")) return false;

        return true;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.endsWith("@gmail.com");
    }
}

