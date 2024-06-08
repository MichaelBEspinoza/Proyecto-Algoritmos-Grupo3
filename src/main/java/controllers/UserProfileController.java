package controllers;

import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class UserProfileController {
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Circle circleImage;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private Text courses;
    @javafx.fxml.FXML
    private Text role;
    @javafx.fxml.FXML
    private Text name;
    @javafx.fxml.FXML
    private Text id;
    @javafx.fxml.FXML
    private Text email;
    @javafx.fxml.FXML
    private Text country;
    @javafx.fxml.FXML
    private Text city;
    @javafx.fxml.FXML
    private Text place;

    public void initialize() {
        setInfo();
    }

    private void setInfo() {
        User loggedUser = UserSession.getInstance().getLoggedUser();

        if (loggedUser != null) {
            name.setText(loggedUser.getName());
            id.setText(String.valueOf(loggedUser.getId()));
            email.setText(loggedUser.getEmail());
            role.setText(loggedUser.roleToString());
            //courses.setText(loggedUser.coursesToString());
        }
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
    public void editProfileOnAction(ActionEvent actionEvent) {loadPage("userEditProfile.fxml");}
}
