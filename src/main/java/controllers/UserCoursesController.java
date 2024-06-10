package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

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
