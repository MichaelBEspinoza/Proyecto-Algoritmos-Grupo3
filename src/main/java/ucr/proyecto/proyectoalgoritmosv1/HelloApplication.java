package ucr.proyecto.proyectoalgoritmosv1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import operations.UserOperations;

import java.io.IOException;

public class HelloApplication extends Application {
    private UserOperations UO = new UserOperations();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Proyecto de Algoritmos - Grupo #3");
        stage.setScene(scene);
        //UO = new UserOperations();
        //UO.loadUsersFromFile("users.txt");
        stage.show();
        stage.setResizable(true);
    }

    public static void main(String[] args) {
        launch();
    }
}