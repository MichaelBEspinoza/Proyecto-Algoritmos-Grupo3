package controllers;

import domain.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import operations.CourseOperations;

import java.util.List;

public class EditCoursesController {
    @javafx.fxml.FXML
    private Menu menuPaginaPrincipal;
    @javafx.fxml.FXML
    private Menu menuAyuda;
    @javafx.fxml.FXML
    private Pane p_course3;
    @javafx.fxml.FXML
    private Pane pane1;
    @javafx.fxml.FXML
    private Pane p_course2;
    @javafx.fxml.FXML
    private Pane p_course5;
    @javafx.fxml.FXML
    private Pane p_course4;
    @javafx.fxml.FXML
    private Pane p_course6;
    @javafx.fxml.FXML
    private Menu menuCursos;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private Pane p_course1;


    private CourseOperations courseOperations;

    @FXML
    public void initialize() {
        courseOperations = new CourseOperations();
        courseOperations.loadCoursesFromFile("cursos.dat");
        loadCourses();
    }

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

    private void displayCourse(Pane coursePane, Course course) {
        coursePane.getChildren().clear();
        Text idText = new Text("ID: " + course.getId());
        Text nameText = new Text("Name: " + course.getName());
        Text descriptionText = new Text("Description: " + course.getDescription());
        Text lengthText = new Text("Length: " + course.getCourseLength());
        Text levelText = new Text("Level: " + course.getLevel());
        Text instructorIdText = new Text("Instructor ID: " + course.getInstructorId());

        coursePane.getChildren().addAll(idText, nameText, descriptionText, lengthText, levelText, instructorIdText);
    }
    @javafx.fxml.FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void editOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void deleteOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void addCourseOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cursosOnAction(ActionEvent actionEvent) {
    }
}
