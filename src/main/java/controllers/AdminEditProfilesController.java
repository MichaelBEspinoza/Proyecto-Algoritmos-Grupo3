package controllers;

import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import operations.UserOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class AdminEditProfilesController {
    @javafx.fxml.FXML
    private TextField txf_country;
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
    private TextField txf_place;
    @javafx.fxml.FXML
    private TextField editThisTXF;
    @javafx.fxml.FXML
    private Button changePasswordText;
    @javafx.fxml.FXML
    private Button saveChangesButton;

    UserOperations UO = new UserOperations();
    User foundUser;

    public void initialize() {
        foundUser = new User();
        clearAllFields();
    }

    private void clearAllFields() {
        txf_name.clear();
        txf_id.clear();
        txf_email.clear();
        cb_role.getSelectionModel().select(null);
        txf_country.clear();
        txf_city.clear();
        txf_place.clear();
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

    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void changePasswordOnAction(ActionEvent actionEvent) {loadPage("adminChangePassword.fxml");
    }

    @javafx.fxml.FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
        if (fieldsAreEmpty()) {
            util.UtilityFX.alert("Error: cambios no aplicables",
                    "Uno o más campos están vacíos. Todos los campos deben contener información.\nInténtelo de nuevo.");
        } else {
            foundUser.setName(txf_name.getText());
            foundUser.setId(Integer.parseInt(txf_id.getText()));
            foundUser.setEmail(txf_email.getText());
            foundUser.setRole(foundUser.stringToRole(cb_role.getValue()));
            foundUser.setCountry(txf_country.getText());
            foundUser.setCity(txf_city.getText());
            foundUser.setPlace(txf_place.getText());

            UO.updateProfile(foundUser);
        }
    }

    @javafx.fxml.FXML
    public void searchOnAction(Event event) {
        editThisTXF.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                foundUser = UO.readUser(Integer.parseInt(editThisTXF.getText()));
                if (userFound() && foundUser != null) {
                    util.UtilityFX.alert("Modificar", "Usuario encontrado con éxito.\nHabilitando campos...");
                    txf_name.setDisable(false);
                    txf_id.setDisable(false);
                    txf_email.setDisable(false);
                    cb_role.setDisable(false);
                    txf_country.setDisable(false);
                    txf_city.setDisable(false);
                    txf_place.setDisable(false);
                    saveChangesButton.setDisable(false);
                    changePasswordText.setDisable(false);

                    txf_name.setText(foundUser.getName());
                    txf_id.setText(String.valueOf(foundUser.getId()));
                    txf_email.setText(foundUser.getEmail());
                    cb_role.getSelectionModel().select(foundUser.roleToString());
                    txf_country.setText(foundUser.getCountry());
                    txf_city.setText(foundUser.getCity());
                    txf_place.setText(foundUser.getPlace());
                } else
                    util.UtilityFX.alert("Usuario no encontrado", "El usuario no pudo ser encontrado por su ID.\nInténtelo de nuevo.");
            }});
    }

    private boolean fieldsAreEmpty() {
        return txf_name.getText().isEmpty() || txf_id.getText().isEmpty() ||
                txf_email.getText().isEmpty() || cb_role.getValue() == null ||
                txf_country.getText().isEmpty() || txf_city.getText().isEmpty() || txf_place.getText().isEmpty();
    }

    private boolean userFound() {
        return UO.readUser(Integer.parseInt(editThisTXF.getText())).getId() == Integer.parseInt(editThisTXF.getText());
    }
}
