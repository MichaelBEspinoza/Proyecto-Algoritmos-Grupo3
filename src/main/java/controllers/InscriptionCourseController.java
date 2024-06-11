package controllers;

import domain.Course;
import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;
import java.util.Objects;

public class InscriptionCourseController {
    @javafx.fxml.FXML
    private Menu menuPaginaPrincipal;
    @javafx.fxml.FXML
    private Menu menuAyuda;
    @javafx.fxml.FXML
    private TextField txf_courseID;
    @javafx.fxml.FXML
    private MenuItem mn_mainPage;
    @javafx.fxml.FXML
    private TextField txf_name;
    @javafx.fxml.FXML
    private Menu menuCursos;
    @javafx.fxml.FXML
    private MenuItem mn_courses;
    @javafx.fxml.FXML
    private BorderPane bp;

    CourseOperations CO = new CourseOperations();
    User loggedUser = UserSession.getInstance().getLoggedUser();

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        System.out.println(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            //util.UtilityFX.alert("Error", "No se pudo cargar la página: " + page);
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("usersupport.fxml");
    }

    @javafx.fxml.FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void cursosOnAction(ActionEvent actionEvent) {loadPage("userCourses.fxml");
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        if (txf_name.getText().isEmpty() || txf_courseID.getText().isEmpty()) {
            util.UtilityFX.alert("Error en inscripción", "Ambos campos deben estar llenos antes de la inscripción.");
            return;
        }
        for (Course checkThis : CO.listCourse())
            if (Objects.equals(checkThis.getName(), txf_name.getText()) && checkThis.getId() == Integer.parseInt(txf_courseID.getText())) {
                loggedUser.addCourse(checkThis);
                util.UtilityFX.alert("¡Éxitos!", "Su inscripción se ha procesado con éxito.");
                return;
            }
        util.UtilityFX.alert("Error en inscripción", "No se encontró el curso ingresado. Inténtelo de nuevo.");
    }
}
