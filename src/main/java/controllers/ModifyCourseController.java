package controllers;

import domain.Course;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import util.UtilityFX;

import java.io.IOException;
import java.util.List;

public class ModifyCourseController {

    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private Menu menuCursos;
    @FXML
    private MenuItem mn_courses;
    @FXML
    private MenuItem mn_mainPage;

    @FXML
    private TextField txf_id;
    @FXML
    private TextField txf_description;
    @FXML
    private TextField txf_name;
    @FXML
    private TextField txf_idIntructor;
    @FXML
    private TextField txf_level;
    @FXML
    private TextField txf_length;

    @FXML
    private BorderPane bp;

    private CourseOperations courseOperations;

    @FXML
    public void initialize() {
        courseOperations = new CourseOperations();
        courseOperations.loadCoursesFromFile("cursos.txt");

        // Agregar listener para el campo de texto del ID
        txf_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    try {
                        int courseId = Integer.parseInt(newValue);
                        Course course = findCourseById(courseId);
                        if (course != null) {
                            txf_name.setText(course.getName());
                            txf_description.setText(course.getDescription());
                            txf_length.setText(course.getCourseLength());
                            txf_level.setText(course.getLevel());
                            txf_idIntructor.setText(String.valueOf(course.getInstructorId()));
                        } else {
                            clearFields();
                        }
                    } catch (NumberFormatException e) {
                        UtilityFX.alert("Error", "ID del curso no es un número válido.");
                    }
                } else {
                    clearFields();
                }
            }
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

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al cargar la página");
            alert.setContentText("No se pudo cargar la página: " + page);
            alert.showAndWait();
            e.printStackTrace();
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
    public void editOnAction(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txf_id.getText().trim());
            String name = txf_name.getText().trim();
            String description = txf_description.getText().trim();
            String length = txf_length.getText().trim();
            String level = txf_level.getText().trim();
            int instructorId = Integer.parseInt(txf_idIntructor.getText().trim());

            Course updatedCourse = new Course(id, name, description, length, level, instructorId);
            CourseOperations operations = new CourseOperations();

            boolean result = operations.updateCourse(updatedCourse);

            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Curso Actualizado");
                alert.setHeaderText(null);
                alert.setContentText("El curso fue actualizado exitosamente.");
                alert.showAndWait();
                bp.getChildren().clear();
                loadPage("editCourses.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo actualizar el curso.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, ingrese valores válidos.");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        txf_id.clear();
        txf_name.clear();
        txf_description.clear();
        txf_length.clear();
        txf_level.clear();
        txf_idIntructor.clear();
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");
    }
}
