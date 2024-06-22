package controllers;

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
import java.util.Objects;

public class EditLessonsController {

    @javafx.fxml.FXML
    private TextField txf_title;
    @javafx.fxml.FXML
    private Menu menuPaginaPrincipal;
    @javafx.fxml.FXML
    private Menu menuAyuda;
    @javafx.fxml.FXML
    private TextField txf_id;
    @javafx.fxml.FXML
    private Button editButton;
    @javafx.fxml.FXML
    private MenuItem mn_mainPage;
    @javafx.fxml.FXML
    private TextField txf_idcourse;
    @javafx.fxml.FXML
    private TextArea txa_Content;
    @javafx.fxml.FXML
    private Menu menuCursos;
    @javafx.fxml.FXML
    private TextField txf_SearchID;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private MenuItem mn_courses;

    LessonOperations lessons = new LessonOperations();
    CourseOperations courses = new CourseOperations();
    Lesson foundLesson;

    public void initialize() {
        foundLesson = new Lesson();
        lessons.loadLessonsFromFile("lessons.txt");
        txf_id.setDisable(true);
        txf_title.setDisable(true);
        txa_Content.setDisable(true);
        txf_idcourse.setDisable(true);
        editButton.setDisable(true);

        txf_title.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z ]*")) {
                txf_title.setText(oldValue);
            }
        });

        txf_id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_id.setText(oldValue);
            }
        });

        txf_idcourse.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_idcourse.setText(oldValue);
            }
        });

        txf_SearchID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_SearchID.setText(oldValue);
            }
        });
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            //util.UtilityFX.alert("Error", "No se pudo cargar la página: " + page);
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
    public void editOnAction(ActionEvent actionEvent) throws TreeException {
        if (txf_id.getText().isEmpty() || txf_title.getText().isEmpty() || txa_Content.getText().isEmpty() || txf_idcourse.getText().isEmpty()) {
            util.UtilityFX.alert("Error al editar", "Ningún campo debe quedar vacío. Inténtelo de nuevo.");
            return;
        }

        try {
            int lessonId = Integer.parseInt(txf_id.getText());
            int courseId = Integer.parseInt(txf_idcourse.getText());

            if (Objects.equals(courses.readCourse(courseId), "El curso no existe")) {
                util.UtilityFX.alert("Error al editar", "El curso con la ID " + courseId + " no existe.");
                return;
            }

            foundLesson.setId(lessonId);
            foundLesson.setTitle(txf_title.getText());
            foundLesson.setContent(txa_Content.getText());
            foundLesson.setCourseId(courseId);
            lessons.updateLesson(foundLesson);
            lessons.saveLessonsToFile("lessons.txt");
            util.UtilityFX.alert("Éxito al editar", "Se ha editado la lección exitosamente.");

        } catch (NumberFormatException e) {
            util.UtilityFX.alert("Error al editar", "Los campos de ID deben contener solo números enteros.");
        }
    }

    @javafx.fxml.FXML
    public void backOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("lessonMaintenance.fxml");
    }

    @FXML
    public void searchOnAction(ActionEvent actionEvent) {
        if (txf_SearchID.getText().isEmpty()) {
            util.UtilityFX.alert("Error al buscar", "El campo de búsqueda para la ID está vacío.");
            return;
        }

        try {
            int searchId = Integer.parseInt(txf_SearchID.getText());
            foundLesson = lessons.readLesson(searchId);
            if (foundLesson != null) {
                txf_id.setDisable(false);
                txf_title.setDisable(false);
                txa_Content.setDisable(false);
                txf_idcourse.setDisable(false);
                editButton.setDisable(false);
                util.UtilityFX.alert("Lección encontrada", "La lección fue encontrada exitosamente.\nCargando campos...");
                txf_id.setText(String.valueOf(lessons.readLesson(searchId).getId()));
                txf_title.setText(lessons.readLesson(searchId).getTitle());
                txa_Content.setText(lessons.readLesson(searchId).getContent());
                txf_idcourse.setText(String.valueOf(lessons.readLesson(searchId).getCourseId()));
            } else
                util.UtilityFX.alert("Error al buscar", "La ID no está asociada a una lección existente.");
        } catch (NumberFormatException e) {
            util.UtilityFX.alert("Error al buscar", "El campo de ID debe contener solo números enteros.");
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}// End of class [EditLessonsController].

