package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;
import util.UtilityFX;
import java.io.IOException;
import domain.Course;
import java.util.List;

public class DeleteCourseController {

    private CourseOperations courseOperations;
    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private TextField idField;
    @FXML
    private TextField levelField;
    @FXML
    private TextField instructorIdField;
    @FXML
    private MenuItem mn_mainPage;
    @FXML
    private TextField lengthField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Menu menuCursos;
    @FXML
    private BorderPane bp;
    @FXML
    private MenuItem mn_courses;

    @FXML
    public void initialize() {
        courseOperations = new CourseOperations();
        courseOperations.loadCoursesFromFile("cursos.txt");

        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int courseId = Integer.parseInt(newValue);
                    Course course = findCourseById(courseId);
                    if (course != null) {
                        nameField.setText(course.getName());
                        descriptionField.setText(course.getDescription());
                        lengthField.setText(course.getCourseLength());
                        levelField.setText(course.getLevel());
                        instructorIdField.setText(String.valueOf(course.getInstructorId()));
                    }
                } catch (NumberFormatException e) {
                    UtilityFX.alert("Error", "ID del curso no es un número válido.");
                }
            }
        });

        Platform.runLater(() -> {
            idField.setFocusTraversable(true);
            nameField.setFocusTraversable(true);
            descriptionField.setFocusTraversable(true);
            lengthField.setFocusTraversable(true);
            levelField.setFocusTraversable(true);
            instructorIdField.setFocusTraversable(true);
        });
    }

    private Course findCourseById(int courseId) {
        List<Course> courses = courseOperations.listCourse();
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return course;
            }
        }
        return null;
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            int courseId = Integer.parseInt(idField.getText());
            boolean deleted = courseOperations.deleteCourse(courseId);
            if (deleted) {
                UtilityFX.alert("Éxito", "El curso ha sido eliminado correctamente.");
                bp.getChildren().clear();
                loadPage("editCourses.fxml");
            } else {
                UtilityFX.alert("Error", "No se encontró ningún curso con el ID proporcionado.");
            }
        } catch (NumberFormatException e) {
            UtilityFX.alert("Error", "ID del curso no es un número válido.");
        }
    }

    @FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("usersupport.fxml");
    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("mainPage.fxml");
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
