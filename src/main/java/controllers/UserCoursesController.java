package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UserCoursesController {


    @javafx.fxml.FXML
    private Menu menuPaginaPrincipal;
    @javafx.fxml.FXML
    private Menu menuAyuda;
    @javafx.fxml.FXML
    private Pane pane1;
    @javafx.fxml.FXML
    private Menu menuCursos;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TableColumn tc_duration;
    @javafx.fxml.FXML
    private TableColumn tc_dificultyLevel;
    @javafx.fxml.FXML
    private TableColumn tc_descripcion;
    @javafx.fxml.FXML
    private TableColumn tc_id;
    @javafx.fxml.FXML
    private TableColumn tc_name;

    @javafx.fxml.FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void editCoursesOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cursosOnAction(ActionEvent actionEvent) {
    }
}
