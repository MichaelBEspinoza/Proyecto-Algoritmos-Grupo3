package controllers;

import domain.Course;
import domain.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import operations.CourseOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;
import util.UtilityFX;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserCoursesController implements Initializable {

    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private Pane pane1;
    @FXML
    private Menu menuCursos;
    @FXML
    private BorderPane bp;
    @FXML
    private TableView<Course> tableView;
    @FXML
    private TableColumn<Course, Integer> tc_id;
    @FXML
    private TableColumn<Course, String> tc_name;
    @FXML
    private TableColumn<Course, String> tc_description;
    @FXML
    private TableColumn<Course, String> tc_duration;
    @FXML
    private TableColumn<Course, String> tc_dificultyLevel;
    @FXML
    private TableColumn<Course, Integer> tc_instructorId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (tc_id != null) {
            tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        }
        if (tc_name != null) {
            tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        if (tc_description != null) {
            tc_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        }
        if (tc_duration != null) {
            tc_duration.setCellValueFactory(new PropertyValueFactory<>("courseLength"));
        }
        if (tc_dificultyLevel != null) {
            tc_dificultyLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        }
        if (tc_instructorId != null) {
            tc_instructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        }

        // Cargar los datos en la tabla
        loadTableData();
    }

    private void loadTableData() {
        CourseOperations courseOperations = new CourseOperations();
        List<Course> coursesList = courseOperations.listCourse();
        ObservableList<Course> courses = FXCollections.observableArrayList(coursesList);

        if (!courses.isEmpty()) {
            tableView.setItems(courses);
        } else {
            System.out.println("La lista de cursos está vacía o no se pudo cargar.");
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
    public void editCoursesOnAction(ActionEvent actionEvent) {
        String userType = String.valueOf(UserSession.getInstance().getLoggedUser().getRole()); // Obtén el tipo de usuario actual
        if ("INSTRUCTOR".equals(userType) || "ADMINISTRATOR".equals(userType)) {
            bp.getChildren().clear();
            loadPage("editCourses.fxml");
        } else {
            System.out.println("No tienes permiso para editar cursos.");
            // Muestra una alerta al usuario
            UtilityFX.alert("Permiso denegado", "No tienes permiso para editar cursos.");
        }
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");
    }
}
