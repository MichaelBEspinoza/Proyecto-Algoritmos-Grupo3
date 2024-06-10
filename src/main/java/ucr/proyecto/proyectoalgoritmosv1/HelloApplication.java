package ucr.proyecto.proyectoalgoritmosv1;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static HelloApplication instance;
    private HostServices hostServices;

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        hostServices = getHostServices();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Proyecto de Algoritmos - Grupo #3");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
    }

    public static HelloApplication getInstance() {
        return instance;
    }

    public HostServices getHostServicesInstance() {
        return hostServices;
    }

    public static void main(String[] args) {
        launch();
    }
}