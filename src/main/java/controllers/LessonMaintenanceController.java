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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import operations.LessonOperations;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;
import util.UtilityFX;

import java.io.IOException;
import java.util.Optional;

public class LessonMaintenanceController {
    @javafx.fxml.FXML
    private Menu menuPaginaPrincipal;
    @javafx.fxml.FXML
    private Menu menuAyuda;
    @javafx.fxml.FXML
    private Pane pane1;
    @javafx.fxml.FXML
    private TableView<Lesson> tv_Lessons;
    @javafx.fxml.FXML
    private Menu menuCursos;
    @javafx.fxml.FXML
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
        if (!lessonOperations.listLessons().isEmpty())
            lessonOperations.loadLessonsFromFile("lessons.txt");

        tc_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tc_Content.setCellValueFactory(new PropertyValueFactory<>("course"));
        tc_Content.setCellValueFactory(new PropertyValueFactory<>("content"));
        loadLessonsIntoTableView();
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

    @javafx.fxml.FXML
    public void editOnAction(ActionEvent actionEvent) {loadPage("lessonMaintenance.fxml");
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {loadPage("addLessons.fxml");
    }

    @javafx.fxml.FXML
    public void deleteOnAction(ActionEvent actionEvent) throws TreeException {

        String deleteThis = UtilityFX.dialog("Eliminar lección", "Digite la lección a eliminar.");
        Optional<String> value = deleteThis.describeConstable();

        if (value.isPresent()) {
            String id = value.get();
            if (id.matches("[0-9]") && lessonOperations.readLesson(Integer.parseInt(id)) != null) {
                lessonOperations.deleteLesson(Integer.parseInt(id));
                util.UtilityFX.alert("Éxito al eliminar", "La lección ha sido suprimida exitosamente.");
            } else util.UtilityFX.alert("Error al eliminar", "La lección con ID " + id + " no existe o no fue encontrada.");
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
            for (int i = 1; i <= lessonOperations.listLessons().size(); i++) {
                Lesson lesson =  lessonOperations.listLessons().get(i);
                list.add(lesson);
            }
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }

}
