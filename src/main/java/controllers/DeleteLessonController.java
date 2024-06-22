package controllers;

import domain.Lesson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import operations.LessonOperations;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;
import java.util.ArrayList;
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
    private ComboBox<Lesson> cb_Lessons;

    @FXML
    public void initialize() {
        LO = new LessonOperations();
        LO.loadLessonsFromFile("lessons.txt");

        fillComboBox();

        cb_Lessons.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txf_Title.setText(newVal.getTitle());
                txf_Course.setText(newVal.getCourse());
                txf_CourseId.setText(String.valueOf(newVal.getCourseId()));
                txa_Content.setText(newVal.getContent());
            }
        });
    }

    private void fillComboBox() {
        List<Lesson> lessons = new ArrayList<>();
        try {
            lessons = LO.listLessons();
        } catch (TreeException e) {
            e.printStackTrace();
        }

        cb_Lessons.getItems().setAll(lessons);
        cb_Lessons.setCellFactory(param -> new ListCell<Lesson>() {
            @Override
            protected void updateItem(Lesson item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getId() + " - " + item.getTitle());
                }
            }
        });

        cb_Lessons.setButtonCell(new ListCell<Lesson>() {
            @Override
            protected void updateItem(Lesson item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getId() + " - " + item.getTitle());
                }
            }
        });
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        Lesson selectedLesson = cb_Lessons.getSelectionModel().getSelectedItem();
        if (selectedLesson != null)
            try {
                boolean deleted = LO.deleteLesson(selectedLesson.getId());
                if (deleted) {
                    util.UtilityFX.alert("Éxito", "La lección ha sido eliminado correctamente.");
                    LO.saveLessonsToFile("lessons.txt");
                    fillComboBox();
                } else util.UtilityFX.alert("Error", "No se pudo eliminar la lección.");
            } catch (TreeException e) {
                util.UtilityFX.alert("Error", "Error al eliminar la lección.");
                e.printStackTrace();
            }
         else util.UtilityFX.alert("Error", "No se ha seleccionado ninguna lección.");
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

    @FXML
    public void backOnAction(ActionEvent actionEvent) {loadPage("lessonMaintenance.fxml");
    }
}
