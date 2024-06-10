package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class MainPageController {
    @javafx.fxml.FXML
    private Menu menuPaginaPrincipal;
    @javafx.fxml.FXML
    private Menu menuAyuda;
    @javafx.fxml.FXML
    private Pane pane1;
    @javafx.fxml.FXML
    private Menu menuCursos;
    @javafx.fxml.FXML
    private Pane p_course3;
    @javafx.fxml.FXML
    private Pane p_anunciosInteres;
    @javafx.fxml.FXML
    private Pane p_course2;
    @javafx.fxml.FXML
    private Pane p_course1;
    @javafx.fxml.FXML
    private BorderPane bp;

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
    public void perfilOnAction(ActionEvent actionEvent) {loadPage("userProfile.fxml");
    }

    @javafx.fxml.FXML
    public void ayudaOnAction(ActionEvent actionEvent) {loadPage("");
    }

    @javafx.fxml.FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void cursosOnAction(ActionEvent actionEvent) {loadPage("userCourses.fxml");
    }

    @javafx.fxml.FXML
    public void cerrarSesionOnAction(ActionEvent actionEvent) {loadPage("loginScreen.fxml");
    }

    @javafx.fxml.FXML
    public void userMaintenenceOnAction(ActionEvent actionEvent) {
        loadPage("usersMaintenance.fxml");
    }
}
