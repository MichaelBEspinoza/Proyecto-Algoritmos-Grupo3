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

    public void initialize() {
        txf_id.setDisable(true);
        txf_title.setDisable(true);
        txa_Content.setDisable(true);
        txf_idcourse.setDisable(true);
        editButton.setDisable(true);
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        System.out.println(HelloApplication.class.getResource(page));
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

    @javafx.fxml.FXML
    public void editOnAction(ActionEvent actionEvent) throws TreeException {

        if (txf_idcourse.getText().isEmpty() || txf_title.getText().isEmpty() || txa_Content.getText().isEmpty() || txf_idcourse.getText().isEmpty()) {
            util.UtilityFX.alert("Error al editar", "Ningún campo debe quedar vacío. Inténtelo de nuevo.");
            return;
        }

        if (Objects.equals(courses.readCourse(Integer.parseInt(txf_idcourse.getText())), "The course doesn’t exist")) {
            util.UtilityFX.alert("Error al editar", "El curso con la ID " + txf_idcourse.getText() + " no existe.");
            return;
        }

        Lesson editThis = lessons.readLesson(Integer.parseInt(txf_SearchID.getText()));
        editThis.setId(Integer.parseInt(txf_id.getText()));
        editThis.setTitle(txf_title.getText());
        editThis.setContent(txa_Content.getText());
        editThis.setCourseId(Integer.parseInt(txf_idcourse.getText()));
        util.UtilityFX.alert("Éxito al editar", "Se ha editado la lección exitosamente.");
    }

    @javafx.fxml.FXML
    public void backOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("lessonMaintenance.fxml");
    }

    @FXML
    public void searchOnAction(ActionEvent actionEvent) throws TreeException {
        lessons.loadLessonsFromFile("lessons.txt");

        int searchThis = Integer.parseInt(txf_SearchID.getText());

        if (lessons.checkIfExistsById(searchThis)) {
            txf_id.setDisable(false);
            txf_title.setDisable(false);
            txa_Content.setDisable(false);
            txf_idcourse.setDisable(false);
            editButton.setDisable(false);
            util.UtilityFX.alert("Lección encontrada", "La lección fue encontrada exitosamente.\nCargando campos...");
            txf_id.setText(String.valueOf(lessons.readLesson(searchThis).getId()));
            txf_title.setText(lessons.readLesson(searchThis).getTitle());
            txa_Content.setText(lessons.readLesson(searchThis).getContent());
            txf_idcourse.setText(String.valueOf(lessons.readLesson(searchThis).getCourseId()));
        } else util.UtilityFX.alert("Error al buscar", "La ID no está asociada a una lección existente.");
    }
}// End of class [EditLessonsController].
