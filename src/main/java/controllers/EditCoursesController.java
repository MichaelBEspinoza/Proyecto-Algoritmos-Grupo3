package controllers;

import domain.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import operations.CourseOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;
import java.util.List;

public class EditCoursesController {
    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private Button p_course3;
    @FXML
    private Pane pane1;
    @FXML
    private Button p_course2;
    @FXML
    private Button p_course5;
    @FXML
    private Button p_course4;
    @FXML
    private Button p_course6;
    @FXML
    private Menu menuCursos;
    @FXML
    private BorderPane bp;
    @FXML
    private Button p_course1;

    private CourseOperations courseOperations;

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
    public void initialize() {
        courseOperations = new CourseOperations();
        courseOperations.loadCoursesFromFile("cursos.dat");
        loadCourses();
    }

    /****************************************************************************************/

    private void loadCourses() {
        List<Course> courses = courseOperations.listCourse();
        if (!courses.isEmpty()) {
            displayCourse(p_course1, courses.get(0));
        }
        if (courses.size() > 1) {
            displayCourse(p_course2, courses.get(1));
        }
        if (courses.size() > 2) {
            displayCourse(p_course3, courses.get(2));
        }
        if (courses.size() > 3) {
            displayCourse(p_course4, courses.get(3));
        }
        if (courses.size() > 4) {
            displayCourse(p_course5, courses.get(4));
        }
        if (courses.size() > 5) {
            displayCourse(p_course6, courses.get(5));
        }
    }

    private void displayCourse(Button courseButton, Course course) {
        courseButton.setText("ID: " + course.getId() + "\nName: " + course.getName());
        courseButton.setOnAction(event -> {
            /**Se define lo que ocurre si se toca el boton**/
        });
    }

    /****************************************************************************************/

    @FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("helpScreen.fxml");

    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("mainPage.fxml");

    }

    @FXML
    public void editOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void addCourseOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");

    }
}
