package controllers;

import domain.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;
import util.UtilityFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyCourseController implements Initializable {

    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private TextField txf_id;
    @FXML
    private TextField txf_description;
    @FXML
    private MenuItem mn_mainPage;
    @FXML
    private TextField txf_name;
    @FXML
    private Menu menuCursos;
    @FXML
    private TextField txf_idIntructor;
    @FXML
    private TextField txf_level;
    @FXML
    private BorderPane bp;

    private CourseOperations courseOperations;
    @FXML
    private TextField txf_length;
    @FXML
    private MenuItem mn_courses;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                            txf_level.setText(course.getLevel());
                            txf_idIntructor.setText(String.valueOf(course.getInstructorId()));
                            txf_length.setText(course.getCourseLength());
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

    private void clearFields() {
        txf_name.clear();
        txf_description.clear();
        txf_level.clear();
        txf_idIntructor.clear();
        txf_length.clear();
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            UtilityFX.alert("Error", "No se pudo cargar la página: " + page);
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
            int courseId = Integer.parseInt(txf_id.getText());
            Course updatedCourse = new Course(courseId, txf_name.getText(), txf_description.getText(),
                    txf_length.getText(), txf_level.getText(), Integer.parseInt(txf_idIntructor.getText()));
            boolean updated = courseOperations.updateCourse(updatedCourse);
            if (updated) {
                UtilityFX.alert("Éxito", "El curso ha sido actualizado correctamente.");
                clearFields();
                bp.getChildren().clear();
                loadPage("editCourses.fxml");
            } else {
                UtilityFX.alert("Error", "No se encontró ningún curso con el ID proporcionado.");
            }
        } catch (NumberFormatException e) {
            UtilityFX.alert("Error", "Por favor, ingrese valores válidos en todos los campos.");
        }
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");
    }
}