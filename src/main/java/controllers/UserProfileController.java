package controllers;

import domain.Course;
import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import storage.UserCourseStorage;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class UserProfileController {
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

            var userCourses = UserCourseStorage.getCoursesForUser(loggedUser.getName());
            if (userCourses.isEmpty()) {
                courses.setText("Sin cursos que mostrar.");
            } else {
                StringBuilder courseList = new StringBuilder();
                for (Course course : userCourses) {
                    courseList.append(course.getId()).append(" - ").append(course.getName()).append("\n");
                }
                courses.setText(courseList.toString());
            }
        }
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
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
        loadPage("usersupport.fxml");
    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {loadPage("userCourses.fxml");
    }
}
