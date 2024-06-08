package controllers;

import domain.Role;
import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import operations.UserOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class UserEditProfileController {
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Circle circleImage;
    @javafx.fxml.FXML
    private TextField txf_role;
    @javafx.fxml.FXML
    private TextField txf_email;
    @javafx.fxml.FXML
    private TextField txf_name;
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

    User loggedUser = UserSession.getInstance().getLoggedUser();
    UserProfileController UPC = new UserProfileController();
    UserOperations UO = new UserOperations();

    public void initialize() {
        textFieldSetUp();
    }

    private void textFieldSetUp() {
        if (loggedUser.getRole() != Role.ADMINISTRATOR){
            txf_name.disabledProperty();
            txf_id.disabledProperty();
            txf_email.disabledProperty();
            txf_role.disabledProperty();
        }

        txf_name.setText(loggedUser.getName());
        txf_email.setText(loggedUser.getEmail());
        txf_id.setText(String.valueOf(loggedUser.getId()));
        txf_role.setText(loggedUser.roleToString());
        txf_country.setText(UPC.getCountryInput());
        txf_city.setText(UPC.getCityInput());
        txf_place.setText(UPC.getPlaceInput());
    }

    private void loadPage(String page){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");}

    @javafx.fxml.FXML
    public void changePasswordOnAction(ActionEvent actionEvent) {loadPage("changePassword.fxml");}

    @javafx.fxml.FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
        if (txf_name.getText().isEmpty() || txf_id.getText().isEmpty() ||
            txf_email.getText().isEmpty() || txf_role.getText().isEmpty() ||
            txf_country.getText().isEmpty() || txf_city.getText().isEmpty() || txf_place.getText().isEmpty()) {
            util.UtilityFX.alert("Error: cambios no aplicables",
                    "Uno o más campos están vacíos. Todos los campos deben contener información.\nInténtelo de nuevo.");
        } else {
            loggedUser.setName(txf_name.getText());
            loggedUser.setId(Integer.parseInt(txf_id.getText()));
            loggedUser.setEmail(txf_email.getText());
            loggedUser.setRole(loggedUser.stringToRole(txf_role.getText()));
            UPC.setCountryInput(txf_country.getText());
            UPC.setCityInput(txf_city.getText());
            UPC.setPlaceInput(txf_place.getText());
            UO.updateProfile(loggedUser);
        }
    }
}
