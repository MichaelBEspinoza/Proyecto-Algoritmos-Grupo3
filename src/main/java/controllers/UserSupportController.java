package controllers;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class UserSupportController {
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Circle circleImage;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextArea txa_userSuport;

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        System.out.println(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            util.UtilityFX.alert("Error", "No se pudo cargar la página: " + page);
            e.printStackTrace();
        }
    }
    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void mailSuportOnAction(ActionEvent actionEvent) {
        String email = "alejosolis1705@gmail.com";
        String subject = "Consulta de Soporte";
        String body = "Ingrese su consulta aquí:";
        String mailto = String.format("mailto:%s?subject=%s&body=%s", email, subject, body);

        HostServices hostServices = HelloApplication.getInstance().getHostServicesInstance();
        hostServices.showDocument(mailto);
    }
}