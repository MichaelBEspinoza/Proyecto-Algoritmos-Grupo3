package controllers;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import operations.ReportsOperations;
import operations.UserOperations;
import structures.lists.ListException;

import java.util.HashMap;
import java.util.Map;

public class StudentCourseProgressController {

    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private MenuItem mn_mainPage;
    @FXML
    private ComboBox<String> cb_courseName; // Cambiar a ComboBox<String>
    @FXML
    private Menu menuCursos;
    @FXML
    private BorderPane bp;
    @FXML
    private MenuItem mn_courses;

    private CourseOperations courseOperations;
    private ReportsOperations reportsOperations;
    private Map<String, String> courseNameMap;
    private UserOperations userOperations = new UserOperations();

    public StudentCourseProgressController() {
        courseOperations = new CourseOperations();
        reportsOperations = new ReportsOperations();
        courseNameMap = new HashMap<>();
    }

    @FXML
    public void initialize() {
        loadUserNames();
    }

    private void loadUserNames() {
        try {
            userOperations.loadUsersFromFile("users.txt");
            for (int i = 1; i <= userOperations.listUsers().size(); i++) {
                User user = (User) userOperations.listUsers().getNode(i).data;
                cb_courseName.getItems().add(user.getName());
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {

        String selectedCourseName = cb_courseName.getSelectionModel().getSelectedItem();
        if (selectedCourseName != null) {
            String name = courseNameMap.get(selectedCourseName);
            reportsOperations.generateStudentProgressReport(name);
        }
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
    }
}
