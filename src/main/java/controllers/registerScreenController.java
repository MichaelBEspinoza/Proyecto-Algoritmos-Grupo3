package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class registerScreenController {
    @javafx.fxml.FXML
    private Pane pane1;
    @javafx.fxml.FXML
    private TextField txf_role;
    @javafx.fxml.FXML
    private TextField txf_email;
    @javafx.fxml.FXML
    private TextField txf_user;
    @javafx.fxml.FXML
    private TextField txf_password;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextField txf_country;
    @javafx.fxml.FXML
    private TextField txf_city;
    @javafx.fxml.FXML
    private TextField txf_place;
    @javafx.fxml.FXML
    private TextField txf_id;

    private void loadPage(String page){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void registerOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void passwordRequerimentsOnAction(ActionEvent actionEvent) {
    }
}
