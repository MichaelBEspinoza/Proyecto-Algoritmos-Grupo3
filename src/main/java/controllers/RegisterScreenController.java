package controllers;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import operations.UserOperations;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class RegisterScreenController {
    @FXML
    private Pane pane1;
    @FXML
    private TextField txf_role;
    @FXML
    private TextField txf_email;
    @FXML
    private TextField txf_user;
    @FXML
    private TextField txf_password;
    @FXML
    private BorderPane bp;
    @FXML
    private TextField txf_country;
    @FXML
    private TextField txf_city;
    @FXML
    private TextField txf_place;
    @FXML
    private TextField txf_id;

    UserOperations UO = new UserOperations();
    User forStringToRoleOnly = new User();

    public void initialize() {
        clearAllFields();
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
                    txf_password.getText(),
                    txf_email.getText(),
                    forStringToRoleOnly.stringToRole(txf_role.getText()),
                    txf_country.getText(),
                    txf_city.getText(),
                    txf_place.getText()));
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

    public String getCountryInput() {
        return txf_country.getText();
    }

    public String getCityInput() {
        return txf_city.getText();
    }

    public String getPlaceInput() {
        return txf_place.getText();
    }

    private void clearAllFields() {
        txf_id.clear();
        txf_email.clear();
        txf_place.clear();
        txf_city.clear();
        txf_country.clear();
        txf_role.clear();
        txf_user.clear();
        txf_password.clear();
    }

    private boolean areEmpty() {
        return txf_user.getText().isEmpty() || txf_id.getText().isEmpty() || txf_email.getText().isEmpty() || txf_password.getText().isEmpty()
                || txf_role.getText().isEmpty() || txf_country.getText().isEmpty() || txf_city.getText().isEmpty() || txf_place.getText().isEmpty();
    }

    private boolean isValidPassword(String password) {
        if (password == null) return false;
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?].*")) return false;

        return true;
    }

}

