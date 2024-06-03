module ucr.proyecto.proyectoalgoritmosv1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.logging;
    requires java.desktop;


    opens ucr.proyecto.proyectoalgoritmosv1 to javafx.fxml;
    exports ucr.proyecto.proyectoalgoritmosv1;
}