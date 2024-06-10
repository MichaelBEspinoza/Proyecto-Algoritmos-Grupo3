package controllers;

import domain.Course;
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
import operations.UserOperations;
import structures.lists.CircularDoublyLinkedList;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;
import java.net.URL;
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

        // Cargar los datos en la tabla
        loadTableData();
    }

    private void loadTableData() {
        UserOperations userOperations = new UserOperations();
        CircularDoublyLinkedList coursesList = userOperations.listAllCourses();
        ObservableList<Course> courses = FXCollections.observableArrayList();

        try {
            if (coursesList != null && coursesList.size() > 0) {
                for (int i = 1; i <= coursesList.size(); i++) {
                    Course course = (Course) coursesList.getNode(i).data;
                    if (course != null) {
                        courses.add(course);
                    }
                }
            } else {
                System.out.println("No hay cursos disponibles para mostrar.");
            }
        } catch (ListException e) {
            e.printStackTrace();
        }

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
        loadPage("helpScreen.fxml");
    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("mainPage.fxml");
    }

    @FXML
    public void editCoursesOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("editCourses.fxml");
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");
    }
}
