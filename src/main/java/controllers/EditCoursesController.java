package controllers;

import domain.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import structures.trees.TreeException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditCoursesController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
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

    private CourseOperations courseOperations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseOperations = new CourseOperations();
        courseOperations.loadCoursesFromFile("cursos.txt");

        // Configurar las columnas de la tabla
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tc_duration.setCellValueFactory(new PropertyValueFactory<>("courseLength"));
        tc_dificultyLevel.setCellValueFactory(new PropertyValueFactory<>("level"));

        // Cargar los datos en la tabla
        loadCourses();
    }

    private void loadCourses() {
        try {
            List<Course> coursesList = courseOperations.listCourse();
            if (coursesList.isEmpty()) {
                throw new TreeException("El árbol de cursos está vacío.");
            }
            ObservableList<Course> courses = FXCollections.observableArrayList(coursesList);
            tableView.setItems(courses);
        } catch (TreeException e) {
            showAlert("Error", "El árbol de cursos está vacío.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Ocurrió un error al cargar los cursos.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
