package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
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

    private void loadPage(String page){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void changePasswordOnAction(ActionEvent actionEvent) {loadPage("changePassword.fxml");
    }

    @javafx.fxml.FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
    }
}
