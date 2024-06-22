package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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
    private ComboBox<Course> cb_Courses; // Asegúrate de que el tipo genérico coincida con el objeto manejado

    @FXML
    public void initialize() {
        courseOperations = new CourseOperations();
        courseOperations.loadCoursesFromFile("cursos.txt");

        // Llenar el ComboBox con cursos
        cb_Courses.getItems().setAll(courseOperations.listCourse());
        cb_Courses.setCellFactory(lv -> new ListCell<Course>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getId() + " - " + item.getName());
            }
        });

        cb_Courses.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getName());
                descriptionField.setText(newSelection.getDescription());
                lengthField.setText(newSelection.getCourseLength());
                levelField.setText(newSelection.getLevel());
                instructorIdField.setText(String.valueOf(newSelection.getInstructorId()));
            }
        });
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        Course selectedCourse = cb_Courses.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            boolean deleted = courseOperations.deleteCourse(selectedCourse.getId());
            if (deleted) {
                UtilityFX.alert("Éxito", "El curso ha sido eliminado correctamente.");
                cb_Courses.getItems().remove(selectedCourse);  // Eliminar de la lista visual
            } else {
                UtilityFX.alert("Error", "No se encontró ningún curso con el ID proporcionado.");
            }
        } else {
            UtilityFX.alert("Error", "Por favor seleccione un curso para eliminar.");
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
