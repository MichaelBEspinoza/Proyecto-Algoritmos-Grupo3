package controllers;

import domain.Course;
import domain.Role;
import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import operations.CourseOperations;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;
import java.util.List;

public class MainPageController {
    @FXML
    public Menu menuLessons;
    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private Pane pane1;
    @FXML
    private Menu menuCursos;
    @FXML
    private Button p_course1;
    @FXML
    private Button p_course2;
    @FXML
    private Button p_course3;
    @FXML
    private Pane p_anunciosInteres;
    @FXML
    private BorderPane bp;

    private CourseOperations courseOperations;
    private Course[] displayedCourses = new Course[3]; // Array to store courses displayed in buttons
    User loggedUser = UserSession.getInstance().getLoggedUser();

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        courseOperations = new CourseOperations();
        courseOperations.loadCoursesFromFile("cursos.txt");

        if (loggedUser.getRole() == Role.USER) {
            menuLessons.setDisable(true);
            menuLessons.setVisible(false);
        }

        try {
            loadCourses();
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCourses() throws TreeException {
        List<Course> courses = courseOperations.listCourse();
        if (courses == null || courses.isEmpty()) {
            displayNoCoursesMessage();
        } else {
            if (courses.size() > 0) {
                displayCourse(p_course1, courses.get(0), 0);
            }
            if (courses.size() > 1) {
                displayCourse(p_course2, courses.get(1), 1);
            }
            if (courses.size() > 2) {
                displayCourse(p_course3, courses.get(2), 2);
            }
        }
    }

    private void displayCourse(Button courseButton, Course course, int index) {
        courseButton.setText("ID: " + course.getId() + "\nName: " + course.getName() + "\nDescription: " + course.getDescription());
        courseButton.setOnAction(event -> {
            // Acción al hacer clic en el botón del curso
        });
        displayedCourses[index] = course; // Store course in the array
    }

    private void displayNoCoursesMessage() {
        p_course1.setText("No hay cursos disponibles");
        p_course1.setDisable(true);
        p_course2.setText("No hay cursos disponibles");
        p_course2.setDisable(true);
        p_course3.setText("No hay cursos disponibles");
        p_course3.setDisable(true);
    }

    @FXML
    public void perfilOnAction(ActionEvent actionEvent) {
        loadPage("userProfile.fxml");
    }

    @FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
        loadPage("");
    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        loadPage("userCourses.fxml");
    }

    @FXML
    public void cerrarSesionOnAction(ActionEvent actionEvent) {
        loadPage("loginScreen.fxml");
    }

    @FXML
    public void userMaintenenceOnAction(ActionEvent actionEvent) {
        if (loggedUser.getRole() == Role.USER) {
            loadPage("userEditProfile.fxml");
        } else {
            loadPage("usersMaintenance.fxml");
        }
    }

    @FXML
    public void menuHelpOnAction(ActionEvent actionEvent) {
        loadPage("usersupport.fxml");
    }

    @FXML
    public void menuCursosOnAction(ActionEvent actionEvent) {
        loadPage("userCourses.fxml");
    }

    @FXML
    public void menuMainPage(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @FXML
    public void inscripcionOnAction(ActionEvent actionEvent) {
        loadPage("courseInscription.fxml");
    }

    @FXML
    public void menuLessons(ActionEvent actionEvent) {
        loadPage("lessonMaintenance.fxml");
    }

    @FXML
    public void courseLessons(ActionEvent actionEvent) {
        loadPage("courseLessons.fxml");
    }
}

