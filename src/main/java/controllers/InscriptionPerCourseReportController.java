package controllers;

import domain.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import operations.ReportsOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscriptionPerCourseReportController {
    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private MenuItem mn_mainPage;
    @FXML
    private Menu menuCursos;
    @FXML
    private BorderPane bp;
    @FXML
    private MenuItem mn_courses;

    private CourseOperations courseOperations;
    private ReportsOperations reportsOperations;
    private Map<String, Integer> courseNameToIdMap;
    @FXML
    private ComboBox cb_courseName;

    public InscriptionPerCourseReportController() {
        courseOperations = new CourseOperations();
        reportsOperations = new ReportsOperations();
        courseNameToIdMap = new HashMap<>();
    }

    @FXML
    public void initialize() {
        loadCourses();
    }

    private void loadCourses() {
        List<Course> courses = courseOperations.listCourse();
        for (Course course : courses) {
            courseNameToIdMap.put(course.getName(), course.getId());
            cb_courseName.getItems().add(course.getName());
        }
    }

    @FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
        // Implementar acción de ayuda
    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        // Implementar acción para ir a la página principal
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        String selectedCourseName = (String) cb_courseName.getSelectionModel().getSelectedItem();
        if (selectedCourseName != null) {
            int courseId = courseNameToIdMap.get(selectedCourseName);
            reportsOperations.generateEnrollmentReport(courseId);
            // Mostrar mensaje de éxito o manejar el resultado según sea necesario
        } else {
            // Mostrar mensaje de error o advertencia
        }
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        // Implementar acción para ir a la sección de cursos
    }
}
