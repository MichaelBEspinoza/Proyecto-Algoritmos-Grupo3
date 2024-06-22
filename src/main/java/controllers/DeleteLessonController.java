package controllers;

import domain.Lesson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import operations.LessonOperations;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;
import util.UtilityFX;

import java.io.IOException;
import java.util.List;

public class DeleteLessonController {
    @javafx.fxml.FXML
    private Menu menuPaginaPrincipal;
    @javafx.fxml.FXML
    private Menu menuAyuda;
    @javafx.fxml.FXML
    private TextField txf_Course;
    @javafx.fxml.FXML
    private TextField txf_Title;
    @javafx.fxml.FXML
    private TextField txf_CourseId;
    @javafx.fxml.FXML
    private TextField txf_Id;
    @javafx.fxml.FXML
    private MenuItem mn_mainPage;
    @javafx.fxml.FXML
    private TextArea txa_Content;
    @javafx.fxml.FXML
    private Menu menuCursos;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private MenuItem mn_courses;

    private LessonOperations LO;

    @FXML
    public void initialize() {
        LO = new LessonOperations();
        LO.loadLessonsFromFile("lessons.txt");

        txf_Id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int courseId = Integer.parseInt(newValue);
                    Lesson lesson = findLessonById(courseId);
                    if (lesson != null) {
                        txf_Title.setText(lesson.getTitle());
                        txf_Id.setText(String.valueOf(lesson.getId()));
                        txf_Course.setText(lesson.getCourse());
                        txf_CourseId.setText(String.valueOf(lesson.getCourseId()));
                        txa_Content.setText(lesson.getContent());
                    }
                } catch (NumberFormatException e) {
                    UtilityFX.alert("Error", "ID del curso no es un número válido.");
                } catch (TreeException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Platform.runLater(() -> {
            txf_Id.setFocusTraversable(true);
            txf_Title.setFocusTraversable(true);
            txf_Course.setFocusTraversable(true);
            txf_CourseId.setFocusTraversable(true);
            txa_Content.setFocusTraversable(true);
        });
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        try {

            if (LO.isEmpty()) {
                util.UtilityFX.alert("Error al eliminar", "La lista de lecciones está vacía. No se puede suprimir nada.");
                return;
            }

            int courseId = Integer.parseInt(txf_Id.getText());
            boolean deleted = LO.deleteLesson(courseId);
            if (deleted) {
                UtilityFX.alert("Éxito", "La lección ha sido eliminado correctamente.");
                bp.getChildren().clear();
                loadPage("lessonMaintenance.fxml");
            } else
                UtilityFX.alert("Error", "No se encontró ninguna lección con el ID proporcionado.");
        } catch (NumberFormatException e) {
            UtilityFX.alert("Error", "ID de la lección no es un número válido.");
        } catch (TreeException e) {
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

    private Lesson findLessonById(int courseId) throws TreeException {
        List<Lesson> lessons = LO.listLessons();
        for (Lesson lesson : lessons) {
            if (lesson.getId() == courseId) {
                return lesson;
            }
        }
        return null;
    }

    @FXML
    public void backOnAction(ActionEvent actionEvent) {loadPage("lessonMaintenance.fxml");
    }
}
