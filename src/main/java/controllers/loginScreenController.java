package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class loginScreenController {


    @javafx.fxml.FXML
    private Pane pane1;
    @javafx.fxml.FXML
    private TextField txf_user;
    @javafx.fxml.FXML
    private TextField txf_password;
    @javafx.fxml.FXML
    private BorderPane bp;

    @javafx.fxml.FXML
    public void accessOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void registerOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void loginOnAction(ActionEvent actionEvent) {
    }
}
