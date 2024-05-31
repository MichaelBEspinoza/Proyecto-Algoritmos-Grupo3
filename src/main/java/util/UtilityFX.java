package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

public class UtilityFX {

    public static void loadPage(String className, String page, BorderPane bp){
        try {
            Class cl = Class.forName(className);
            FXMLLoader fxmlLoader = new FXMLLoader(cl.getResource(page));
            bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Alert alert(String title, String headerText){
        Alert myalert = new Alert(Alert.AlertType.INFORMATION);
        myalert.setTitle(title);
        myalert.setHeaderText(headerText);
        DialogPane dialogPane = myalert.getDialogPane();
        String css = HelloApplication.class.getResource("dialog.css").toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("myDialog");
        myalert.showAndWait();
        return myalert;
    }

    public static String dialog(String title, String headerText){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        String css = HelloApplication.class.getResource("dialog.css").toExternalForm();
        dialog.getEditor().getStylesheets().add(css);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(""); // Retorna el valor ingresado, o una cadena vacía si el diálogo fue cancelado.
    }

}