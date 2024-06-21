package controllers;

import domain.Lesson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.cell.PropertyValueFactory;
import operations.LessonOperations;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;
import util.UtilityFX;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LessonMaintenanceController {

    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private Pane pane1;
    @FXML
    private TableView<Lesson> tv_Lessons;
    @FXML
    private Menu menuCursos;
    @FXML
    private BorderPane bp;
    @FXML
    private TableColumn<Lesson, String> tc_Content;
    @FXML
    private TableColumn<Lesson, String> tc_Course;
    @FXML
    private TableColumn<Lesson, String> tc_Title;
    @FXML
    private TableColumn<Lesson, Integer> tc_ID;

    LessonOperations lessonOperations = new LessonOperations();

    public void initialize() throws TreeException {
        lessonOperations.loadLessonsFromFile("lessons.txt");

        tc_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tc_Course.setCellValueFactory(new PropertyValueFactory<>("course"));
        tc_Content.setCellValueFactory(new PropertyValueFactory<>("content"));
        loadLessonsIntoTableView();
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editOnAction(ActionEvent actionEvent) {
        loadPage("lessonMaintenance.fxml");
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        loadPage("addLessons.fxml");
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) throws TreeException {
        lessonOperations.loadLessonsFromFile("lessons.txt");
        String deleteThis = UtilityFX.dialog("Eliminar lección", "Digite la lección a eliminar.");
        Optional<String> value = deleteThis.describeConstable();

        if (value.isPresent()) {
            String id = value.get();
            if (!lessonOperations.checkIfExistsById(Integer.parseInt(id))) {
                lessonOperations.deleteLesson(Integer.parseInt(id));
                lessonOperations.saveLessonsToFile("lessons.txt");
                UtilityFX.alert("Éxito al eliminar", "La lección ha sido suprimida exitosamente.");
            } else {
                UtilityFX.alert("Error al eliminar", "La lección con ID " + id + " no existe o no fue encontrada.");
            }
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

    private void loadLessonsIntoTableView() {
        ObservableList<Lesson> list = FXCollections.observableArrayList();

        try {
            List<Lesson> lessons = lessonOperations.listLessons();
            for (Lesson lesson : lessons) {
                list.add(lesson);
            }
            tv_Lessons.setItems(list);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}
