package controllers;

import domain.User;
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
import operations.UserOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;
import util.UtilityFX;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentCourseEvaluationController {

    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private MenuItem mn_mainPage;
    @FXML
    private ComboBox<String> cb_courseName;
    @FXML
    private Menu menuCursos;
    @FXML
    private BorderPane bp;
    @FXML
    private MenuItem mn_courses;
    private CourseOperations courseOperations;
    private ReportsOperations reportsOperations;
    private UserOperations userOperations;
    private Map<String, Integer> courseNameToIdMap;

    @FXML
    public void initialize() {
        userOperations = new UserOperations();
        reportsOperations = new ReportsOperations(); // Initialize reportsOperation
        populateUserComboBox();
    }

    private void populateUserComboBox() {
        userOperations.loadUsersFromFile("users.txt");
        List<User> users = userOperations.listUsersArray();
        for (User user : users) {
            cb_courseName.getItems().add(String.valueOf(user.getId()));
        }
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
        String selectedCourseName = cb_courseName.getSelectionModel().getSelectedItem();
        if (selectedCourseName != null || "INSTRUCTOR".equals(userType) || "ADMINISTRATOR".equals(userType)) {
            // Parsear el ID del usuario del nombre seleccionado
            int userId = parseUserIdFromSelection(selectedCourseName);
            reportsOperations.generateEvaluationReport(userId);
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

    private int parseUserIdFromSelection(String selection) {
        if (selection != null && selection.contains("(") && selection.contains(")")) {
            int startIndex = selection.indexOf('(') + 1;
            int endIndex = selection.indexOf(')');
            String idStr = selection.substring(startIndex, endIndex);
            return Integer.parseInt(idStr);
        }
        return -1;
    }
}
