package controllers;

import domain.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class addCourseController {
    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private TextField txf_id;
    @FXML
    private TextField txf_description;
    @FXML
    private TextField txf_name;
    @FXML
    private Menu menuCursos;
    @FXML
    private TextField txf_idIntructor;
    @FXML
    private TextField txf_level;
    @FXML
    private TextField txf_length;
    @FXML
    private MenuItem mn_mainPage;
    @FXML
    private MenuItem mn_courses;
    @FXML
    private BorderPane bp;

    private CourseOperations courseOperations = new CourseOperations();

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        System.out.println(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            util.UtilityFX.alert("Error", "No se pudo cargar la página: " + page);
            e.printStackTrace();
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

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txf_id.getText());
            String name = txf_name.getText();
            String description = txf_description.getText();
            String length = txf_length.getText();
            String level = txf_level.getText();
            int instructorId = Integer.parseInt(txf_idIntructor.getText());

            Course newCourse = new Course(id, name, description, length, level, instructorId);

            if (courseOperations.createCourse(newCourse)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Curso añadido exitosamente.");
                bp.getChildren().clear();
                loadPage("editCourses.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "El curso con este ID ya existe.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "ID e Instructor ID deben ser números enteros.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error al añadir el curso.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}