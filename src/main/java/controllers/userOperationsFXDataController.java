package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class userOperationsFXDataController {
    @javafx.fxml.FXML
    private TableView tv_UserData;
    @javafx.fxml.FXML
    private TableColumn tvC_name;
    @javafx.fxml.FXML
    private AnchorPane ap_users;
    @javafx.fxml.FXML
    private TableColumn tvC_role;
    @javafx.fxml.FXML
    private TableColumn tvC_id;
    @javafx.fxml.FXML
    private TableColumn tvC_password;
    @javafx.fxml.FXML
    private TableColumn tvC_mail;

    @javafx.fxml.FXML
    public void backOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void modifyOnAction(ActionEvent actionEvent) {
    }
}
