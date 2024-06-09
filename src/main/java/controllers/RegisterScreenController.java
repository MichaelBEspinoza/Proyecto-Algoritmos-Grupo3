package controllers;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import operations.UserOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class RegisterScreenController {
    @javafx.fxml.FXML
    private Pane pane1;
    @javafx.fxml.FXML
    private TextField txf_role;
    @javafx.fxml.FXML
    private TextField txf_email;
    @javafx.fxml.FXML
    private TextField txf_user;
    @javafx.fxml.FXML
    private TextField txf_password;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextField txf_country;
    @javafx.fxml.FXML
    private TextField txf_city;
    @javafx.fxml.FXML
    private TextField txf_place;
    @javafx.fxml.FXML
    private TextField txf_id;

    UserOperations UO = new UserOperations();
    User forStringToRoleOnly = new User();

    public void initialize() {
        clearAllFields();
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            util.UtilityFX.alert("Error", "No se pudo cargar la página: " + page);
        }
    }

    @javafx.fxml.FXML
    public void registerOnAction(ActionEvent actionEvent) {

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
                                                    forStringToRoleOnly.stringToRole(txf_role.getText())));
            loadPage("mainPage.fxml");
            util.UtilityFX.alert("Registro exitoso", "¡Bienvenido!");
        }
    }

    @javafx.fxml.FXML
    public void passwordRequirementsOnAction(ActionEvent actionEvent) {
        /*
         * Valida si una contraseña cumple con los requerimientos:
         * - Al menos 8 caracteres.
         * - Al menos una letra mayúscula.
         * - Al menos una letra minúscula.
         * - Al menos un signo especial.
         *
         * @param password La contraseña a validar
         * @return true si la contraseña cumple con los requerimientos, false de lo contrario
         */

        util.UtilityFX.alert("Requerimientos de contraseña:", """
                La contraseña debe tener:\

                1. Al menos 8 carácteres.\

                2. Letras mayúsculas y minúsculas.\

                3. Un signo especial ('*', '!', '$', etc.).""");
    }

    public String getCountryInput() {
        return txf_country.getText();
    }

//    public void setCountryInput(String country) {
//        this.txf_country.setText(country);
//    }

    public String getCityInput() {
        return txf_city.getText();
    }

//    public void setCityInput(String city) {
//        this.txf_city.setText(city);
//    }

    public String getPlaceInput() {
        return txf_place.getText();
    }

//    public void setPlaceInput(String place) {
//        this.txf_place.setText(place);
//    }

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
