package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class UsersMaintenenceController {
    @javafx.fxml.FXML
    private Pane p_anunciosInteres;
    @javafx.fxml.FXML
    private TableColumn<String,String> tc_city;
    @javafx.fxml.FXML
    private Pane pane1;
    @javafx.fxml.FXML
    private TableColumn tc_email;
    @javafx.fxml.FXML
    private TableColumn tc_country;
    @javafx.fxml.FXML
    private TableColumn tc_cédula;
    @javafx.fxml.FXML
    private TableColumn tc_name;
    @javafx.fxml.FXML
    private TableColumn tc_rol;
    @javafx.fxml.FXML
    private TableColumn tc_place;
    @javafx.fxml.FXML
    private BorderPane bp;

    private void loadPage(String page){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void editInformationOnAction(ActionEvent actionEvent) {
    }
}
