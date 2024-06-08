package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class ChangePasswordController {
    @javafx.fxml.FXML
    private TextField txf_confirmPassword;
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Circle circleImage;
    @javafx.fxml.FXML
    private TextField txf_newPassword;

    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
    }
}
