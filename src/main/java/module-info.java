module ucr.proyecto.proyectoalgoritmosv1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.logging;
    requires java.desktop;
    requires java.base;

    opens controllers to javafx.fxml;
    opens ucr.proyecto.proyectoalgoritmosv1 to javafx.fxml;
    opens domain to javafx.base;

    exports ucr.proyecto.proyectoalgoritmosv1;
}