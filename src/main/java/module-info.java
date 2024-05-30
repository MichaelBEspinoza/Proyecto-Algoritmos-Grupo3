module ucr.proyecto.proyectoalgoritmosv1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;


    opens ucr.proyecto.proyectoalgoritmosv1 to javafx.fxml;
    exports ucr.proyecto.proyectoalgoritmosv1;
}