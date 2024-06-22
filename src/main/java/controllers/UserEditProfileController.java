package controllers;

import domain.Role;
import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
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
    private TextField txf_city;
    @FXML
    private TextField txf_id;
    @FXML
    private ComboBox<String> cb_role;

    private User loggedUser = UserSession.getInstance().getLoggedUser();
    private final UserOperations userOperations = new UserOperations();
    @FXML
    private ComboBox<String> cb_Place;
    @FXML
    private ComboBox<String> cb_Country;

    public void initialize() {
        cb_Place.getItems().addAll("Ciudad Rodrigo Facio", "San Ramón", "Grecia",
                "Turrialba", "Paraíso", "Guápiles",
                "Liberia", "Santa Cruz", "Puerto Limón",
                "Puntarenas", "Región Brunca");
        cb_Country.getItems().addAll("Argentina", "Belice", "Bolivia", "Brasil", "Canadá", "Chile", "Colombia", "Costa Rica", "Cuba",
                "Ecuador", "El Salvador", "Estados Unidos", "Guatemala", "Haití", "Honduras", "México", "Nicaragua", "Panamá",
                "Paraguay", "Perú", "Rep. Dominicana", "Uruguay", "Venezuela");
        cb_role.getItems().addAll("Usuario","Instructor","Administrador");
        textFieldSetUp();

        txf_name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚ ]*")) {
                txf_name.setText(oldValue);
            }
        });

        txf_city.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚ ]*")) {
                txf_name.setText(oldValue);
            }
        });

        txf_id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,9}")) {
                txf_id.setText(oldValue);
            }
        });

        txf_email.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!isValidEmail(txf_email.getText())) {
                    util.UtilityFX.alert("Email inválido", "Por favor, ingrese una dirección de correo electrónico de Gmail válida.");
                    txf_email.requestFocus();
                }
            }
        });
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

            util.UtilityFX.alert("Exito","Los cambios fueron aplicados");
            loggedUser.setName(txf_name.getText());
            loggedUser.setId(Integer.parseInt(txf_id.getText()));
            loggedUser.setEmail(txf_email.getText());
            loggedUser.setRole(loggedUser.stringToRole(cb_role.getValue()));
            loggedUser.setCountry(cb_Country.getValue());
            loggedUser.setCity(txf_city.getText());
            loggedUser.setPlace(cb_Place.getValue());

            userOperations.updateProfile(loggedUser);
            loadPage("userProfile.fxml");
        }
    }

    private void textFieldSetUp() {
        if (loggedUser.getRole() != Role.ADMINISTRATOR) {
            txf_name.setDisable(true);
            txf_id.setDisable(true);
            txf_email.setDisable(true);
            cb_role.setDisable(true);
        }

        txf_name.setText(loggedUser.getName());
        txf_email.setText(loggedUser.getEmail());
        txf_id.setText(String.valueOf(loggedUser.getId()));
        cb_Country.getSelectionModel().select(loggedUser.getCountry());
        txf_city.setText(loggedUser.getCity());
        cb_Place.getSelectionModel().select(loggedUser.getPlace());
        cb_role.getSelectionModel().selectFirst();
    }

    private boolean fieldsAreEmpty() {
        return txf_name.getText().isEmpty() || txf_id.getText().isEmpty() ||
                txf_email.getText().isEmpty() || cb_role.getValue() == null ||
                cb_Country.getValue() == null || txf_city.getText().isEmpty() || cb_Place.getValue() == null;
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

    private boolean isValidEmail(String email) {
        return email != null && email.endsWith("@gmail.com");
    }
}

