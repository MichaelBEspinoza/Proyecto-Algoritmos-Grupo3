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

public class AddLessonsController {

    @javafx.fxml.FXML
    private TextField txf_title;
    @javafx.fxml.FXML
    private TextField txf_Course;
    @javafx.fxml.FXML
    private TextField txf_id;
    @javafx.fxml.FXML
    private TextArea txa_Content;
    @javafx.fxml.FXML
    private BorderPane bp;
    @FXML
    private Button addButton;

    private final CourseOperations courses = new CourseOperations();
    private final LessonOperations lessons = new LessonOperations();

    public void initialize() {
        txf_id.setDisable(true);
        txf_title.setDisable(true);
        txa_Content.setDisable(true);
        addButton.setDisable(true);
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
//            util.UtilityFX.alert("Error", "No se pudo cargar la página: " + page);
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) throws TreeException {
        if (txf_title.getText().isEmpty() || txf_id.getText().isEmpty() || txa_Content.getText().isEmpty()) {
            util.UtilityFX.alert("Error al insertar", "Todos los campos deben estar llenos. Inténtelo de nuevo.");
            return;
        }

        for (Lesson check : lessons.listLessons()) {
            if (check.getId() == Integer.parseInt(txf_id.getText())){
                util.UtilityFX.alert("Error al insertar", "Una lección con esta ID ya existe.");
                return;
            } else {
                Lesson addThis = new Lesson(Integer.parseInt(txf_id.getText()),
                                            txf_title.getText(),
                                            txa_Content.getText(),
                                            Integer.parseInt(txf_Course.getText()));
                lessons.createLesson(addThis);
                util.UtilityFX.alert("Éxito al insertar", "Se ha insertado la lección.");
            }
        }
    }

    @FXML
    public void searchOnAction(ActionEvent actionEvent) {
        if (txf_Course.getText().isEmpty()) {
            util.UtilityFX.alert("Error al buscar", "El campo de búsqueda para el curso está vacío o la ID insertada no existe.");
            return;
        }

         for (Course check : courses.listCourse())
             if (check.getId() == Integer.parseInt(txf_Course.getText())) {
                 util.UtilityFX.alert("Éxito", "Se ha encontrado el curso.\nHabilitando campos...");
                 txf_id.setDisable(false);
                 txf_title.setDisable(false);
                 txa_Content.setDisable(false);
                 addButton.setDisable(false);
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
}// End of class [AddLessonsController].
