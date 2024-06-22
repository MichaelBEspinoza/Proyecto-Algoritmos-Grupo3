package controllers;

import domain.Course;
import domain.Lesson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import operations.LessonOperations;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class AddLessonsController {

    @FXML
    private TextField txf_title;
    @FXML
    private TextField txf_id;
    @FXML
    private TextArea txa_Content;
    @FXML
    private BorderPane bp;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<Course> cb_Course;

    private final CourseOperations courses = new CourseOperations();
    private final LessonOperations lessons = new LessonOperations();

    @FXML
    public void initialize() {
        txf_id.setDisable(true);
        txf_title.setDisable(true);
        txa_Content.setDisable(true);
        addButton.setDisable(true);

        courses.loadCoursesFromFile("cursos.txt");
        cb_Course.getItems().setAll(courses.listCourse());
        cb_Course.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                return course != null ? course.getId() + " - " + course.getName() : "";
            }

            @Override
            public Course fromString(String string) {
                return null; // No need to convert from string to Course
            }
        });

        cb_Course.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txf_id.setDisable(false);
                txf_title.setDisable(false);
                txa_Content.setDisable(false);
                addButton.setDisable(false);
            }
        });
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) throws TreeException {
        if (txf_title.getText().isEmpty() || txf_id.getText().isEmpty() || txa_Content.getText().isEmpty()) {
            util.UtilityFX.alert("Error al insertar", "Todos los campos deben estar llenos. Inténtelo de nuevo.");
            return;
        }

        try {
            int lessonId = Integer.parseInt(txf_id.getText());
            Course selectedCourse = cb_Course.getSelectionModel().getSelectedItem();
            if (selectedCourse != null && !lessons.isLessonIdUnique(lessonId)) {
                util.UtilityFX.alert("Error al insertar", "Una lección con esta ID ya existe.");
                return;
            }

            Lesson addThis = new Lesson(lessonId, txf_title.getText(), txa_Content.getText(), selectedCourse.getName(), selectedCourse.getId(), false);
            lessons.createLesson(addThis);
            util.UtilityFX.alert("Éxito al insertar", "Se ha insertado la lección exitosamente.");

        } catch (NumberFormatException e) {
            util.UtilityFX.alert("Error al insertar", "El campo de ID debe contener solo números enteros.");
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
            e.printStackTrace();
        }
    }
}

