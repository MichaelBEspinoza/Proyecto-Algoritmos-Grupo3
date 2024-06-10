package controllers;

import domain.Role;
import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import operations.UserOperations;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class UserEditProfileController {
    @FXML
    private ImageView imageView;
    @FXML
    private Circle circleImage;
    @FXML
    private TextField txf_email;
    @FXML
    private TextField txf_name;
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
    @FXML
    private TextField txf_role;

    private User loggedUser = UserSession.getInstance().getLoggedUser();
    private final UserOperations userOperations = new UserOperations();

    public UserEditProfileController() {
    }

    public void initialize() {
        textFieldSetUp();
    }

    @FXML
    public void mainPageOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @FXML
    public void changePasswordOnAction(ActionEvent actionEvent) {
        loadPage("changePassword.fxml");
    }

    @FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
        if (fieldsAreEmpty()) {
            util.UtilityFX.alert("Error: cambios no aplicables",
                    "Uno o más campos están vacíos. Todos los campos deben contener información.\nInténtelo de nuevo.");
        } else {
            loggedUser.setName(txf_name.getText());
            loggedUser.setId(Integer.parseInt(txf_id.getText()));
            loggedUser.setEmail(txf_email.getText());
            loggedUser.setRole(loggedUser.stringToRole(txf_role.getText()));
            loggedUser.setCountry(txf_country.getText());
            loggedUser.setCity(txf_city.getText());
            loggedUser.setPlace(txf_place.getText());

            userOperations.updateProfile(loggedUser);
        }
    }

    private void textFieldSetUp() {
        if (loggedUser.getRole() != Role.ADMINISTRATOR) {
            txf_name.setDisable(true);
            txf_id.setDisable(true);
            txf_email.setDisable(true);
            txf_role.setDisable(true);
        }

        txf_name.setText(loggedUser.getName());
        txf_email.setText(loggedUser.getEmail());
        txf_id.setText(String.valueOf(loggedUser.getId()));
        txf_role.setText(loggedUser.roleToString());
        txf_country.setText(loggedUser.getCountry());
        txf_city.setText(loggedUser.getCity());
        txf_place.setText(loggedUser.getPlace());
    }

    private boolean fieldsAreEmpty() {
        return txf_name.getText().isEmpty() || txf_id.getText().isEmpty() ||
                txf_email.getText().isEmpty() || txf_role.getText().isEmpty() ||
                txf_country.getText().isEmpty() || txf_city.getText().isEmpty() || txf_place.getText().isEmpty();
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
}

