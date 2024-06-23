package controllers;

import domain.Course;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import operations.ReportsOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;
import util.UtilityFX;

import java.io.IOException;
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

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        bp.getChildren().clear();
        loadPage("usersupport.fxml");
    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("mainPage.fxml");
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        String userType = String.valueOf(UserSession.getInstance().getLoggedUser().getRole());
        String selectedCourseName = (String) cb_courseName.getSelectionModel().getSelectedItem();
        if (selectedCourseName != null || "INSTRUCTOR".equals(userType) || "ADMINISTRATOR".equals(userType)) {
            int courseId = courseNameToIdMap.get(selectedCourseName);
            reportsOperations.generateEnrollmentReport(courseId);
            UtilityFX.alert("Permiso concedido", "Tienes acceso para editar cursos.");
        } else {
            UtilityFX.alert("Permiso denegado", "No tienes permiso para editar cursos.");
        }
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");
    }
}
