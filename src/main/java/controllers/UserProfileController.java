package controllers;

import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class UserProfileController {
    @FXML
    private ImageView imageView;
    @FXML
    private Circle circleImage;
    @FXML
    private BorderPane bp;
    @FXML
    private Text courses;
    @FXML
    private Text role;
    @FXML
    private Text name;
    @FXML
    private Text id;
    @FXML
    private Text email;
    @FXML
    private Text country;
    @FXML
    private Text city;
    @FXML
    private Text place;
    @FXML
    private Menu menuAyuda;
    @FXML
    private Menu menuCursos;
    @FXML
    private Menu menuPaginaPrincipal;

    public void initialize() throws ListException {
        setInfo();
    }

    private void setInfo() throws ListException {
        User loggedUser = UserSession.getInstance().getLoggedUser();

        if (loggedUser != null) {
            name.setText(loggedUser.getName());
            id.setText(String.valueOf(loggedUser.getId()));
            email.setText(loggedUser.getEmail());
            role.setText(loggedUser.roleToString());
            country.setText(loggedUser.getCountry());
            city.setText(loggedUser.getCity());
            place.setText(loggedUser.getPlace());

            if (loggedUser.getCourses() == null) courses.setText("Sin cursos que mostrar.");
            else courses.setText(String.valueOf(loggedUser.coursesToString()));
        }
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
    public void mainPageOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @FXML
    public void editProfileOnAction(ActionEvent actionEvent) {
        loadPage("userEditProfile.fxml");
    }

    @FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
        // Implementación del manejo de la acción de ayuda
    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        // Implementación del manejo de la acción de cursos
    }
}
