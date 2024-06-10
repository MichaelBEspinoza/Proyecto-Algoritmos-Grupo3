package controllers;

import domain.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import operations.UserOperations;
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
    private TextField searchThisTXF;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private Button saveChangesButton;

    UserOperations UO = new UserOperations();
    User foundUser;

    public void initialize() {
        foundUser = new User();
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
    public void searchOnAction(Event event) {

        searchThisTXF.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                foundUser = UO.readUser(Integer.parseInt(searchThisTXF.getText()));
                if (userFound() && foundUser != null) {

                }
            }
        });
    }

    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
    }

    private boolean userFound() {
        return UO.readUser(Integer.parseInt(searchThisTXF.getText())).getId() == Integer.parseInt(searchThisTXF.getText());
    }
}
