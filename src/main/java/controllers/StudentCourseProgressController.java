package controllers;

import domain.Lesson;
import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import operations.LessonOperations;
import operations.ReportsOperations;
import operations.UserOperations;
import structures.lists.ListException;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;
import util.UtilityFX;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentCourseProgressController {

    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private MenuItem mn_mainPage;
    @FXML
    private ComboBox<String> cb_courseName; // Cambiar a ComboBox<String>
    @FXML
    private Menu menuCursos;
    @FXML
    private BorderPane bp;
    @FXML
    private MenuItem mn_courses;

    private CourseOperations courseOperations;
    private ReportsOperations reportsOperations;
    private LessonOperations lessonOperations;
    private Map<String, String> courseNameMap;
    private UserOperations userOperations = new UserOperations();

    public StudentCourseProgressController() {
        courseOperations = new CourseOperations();
        reportsOperations = new ReportsOperations();
        lessonOperations = new LessonOperations();
        courseNameMap = new HashMap<>();
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        loadUserNames();
    }

    private void loadUserNames() {
        try {
            userOperations.loadUsersFromFile("users.txt");
            for (int i = 1; i <= userOperations.listUsers().size(); i++) {
                User user = (User) userOperations.listUsers().getNode(i).data;
                cb_courseName.getItems().add(user.getName());
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("usersupport.fxml");
    }

    @FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("mainPage.fxml");
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        String userType = String.valueOf(UserSession.getInstance().getLoggedUser().getRole());
        String selectedCourseName = cb_courseName.getSelectionModel().getSelectedItem();
        if (selectedCourseName != null || "INSTRUCTOR".equals(userType) || "ADMINISTRATOR".equals(userType)){
            String name = courseNameMap.get(selectedCourseName);
            reportsOperations.generateStudentProgressReport(name);
            UtilityFX.alert("Permiso concedido", "Tienes acceso para editar cursos.");
        } else {
            UtilityFX.alert("Permiso denegado", "No tienes permiso para editar cursos.");
        }
    }

    @FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");
    }

    // Método para marcar una lección como completada
    public void markLessonAsCompleted(int lessonId) {
        try {
            if (lessonOperations.markLessonCompleted(lessonId)) {
                UtilityFX.alert("Éxito", "Lección marcada como completada.");
            } else {
                UtilityFX.alert("Error", "No se pudo marcar la lección como completada.");
            }
        } catch (TreeException e) {
            e.printStackTrace();
            UtilityFX.alert("Error", "Ocurrió un error al marcar la lección como completada.");
        }
    }

    // Método para calcular el progreso del curso
    public void calculateCourseProgress(int courseId) {
        try {
            int totalLessons = lessonOperations.countLessonsByCourse(courseId);
            int completedLessons = lessonOperations.countCompletedLessonsByCourse(courseId);

            if (totalLessons == 0) {
                UtilityFX.alert("Error", "El curso no tiene lecciones.");
                return;
            }

            double progress = (double) completedLessons / totalLessons * 100;
            UtilityFX.alert("Progreso del curso", "El progreso del curso es: " + progress + "%");
        } catch (TreeException e) {
            e.printStackTrace();
            UtilityFX.alert("Error", "Ocurrió un error al calcular el progreso del curso.");
        }
    }
}
