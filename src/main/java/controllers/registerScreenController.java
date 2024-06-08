package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

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
    public void registerOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void passwordRequerimentsOnAction(ActionEvent actionEvent) {
    }
}
