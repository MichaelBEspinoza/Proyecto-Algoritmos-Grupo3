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

import java.io.IOException;
import java.util.List;

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

    @javafx.fxml.FXML
    public void editOnAction(ActionEvent actionEvent) {
        loadPage("editLessons.fxml");
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        loadPage("addLessons.fxml");
    }

    @javafx.fxml.FXML
    public void deleteOnAction(ActionEvent actionEvent) throws TreeException {
        loadPage("deleteLesson.fxml");
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
        this.tv_Lessons.getItems().clear();
        ObservableList<Lesson> list = FXCollections.observableArrayList();

        try {
            List<Lesson> lessonsList = lessonOperations.listLessons();
            list.addAll(lessonsList);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }

        tv_Lessons.setItems(list);
    }
}
