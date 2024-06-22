package controllers;

import domain.Course;
import domain.Lesson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import operations.CourseOperations;
import operations.LessonOperations;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

public class AddLessonsController {

    @javafx.fxml.FXML
    private TextField txf_title;
    @javafx.fxml.FXML
    private TextField txf_id;
    @javafx.fxml.FXML
    private TextArea txa_Content;
    @javafx.fxml.FXML
    private BorderPane bp;
    @FXML
    private Button addButton;

    private final CourseOperations courses = new CourseOperations();
    private final LessonOperations lessons = new LessonOperations();
    @FXML
    private Menu menuPaginaPrincipal;
    @FXML
    private Menu menuAyuda;
    @FXML
    private MenuItem mn_mainPage;
    @FXML
    private ComboBox<Course> cb_Course;
    @FXML
    private Menu menuCursos;
    @FXML
    private MenuItem mn_courses;

    public void initialize() {
        txf_id.setDisable(true);
        txf_title.setDisable(true);
        txa_Content.setDisable(true);
        addButton.setDisable(true);

        txf_title.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚ ]*")) {
                txf_title.setText(oldValue);
            }
        });

        txf_id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_id.setText(oldValue);
            }
        });

        loadCoursesIntoComboBox();

        cb_Course.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                enableLessonFields();
            } else {
                disableLessonFields();
            }
        });
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) throws TreeException {
        if (txf_title.getText().isEmpty() || txf_id.getText().isEmpty() || txa_Content.getText().isEmpty()) {
            util.UtilityFX.alert("Error al insertar", "Todos los campos deben estar llenos. Inténtelo de nuevo.");
            return;
        }

        try {
            int lessonId = Integer.parseInt(txf_id.getText());

            if (!isLessonIdUnique(lessonId)) {
                util.UtilityFX.alert("Error al insertar", "Una lección con esta ID ya existe.");
                return;
            }

            try {
                Course selectedCourse = cb_Course.getValue();
                if (selectedCourse == null) {
                    util.UtilityFX.alert("Error al insertar", "Debe seleccionar un curso válido.");
                    return;
                }

                int courseId = selectedCourse.getId();

                Lesson addThis = new Lesson(lessonId, txf_title.getText(), txa_Content.getText(), selectedCourse.getName(), courseId);
                lessons.createLesson(addThis);
                util.UtilityFX.alert("Éxito al insertar", "Se ha insertado la lección.");
            } catch (NumberFormatException e) {
                util.UtilityFX.alert("Error al insertar", "El campo de Curso debe contener solo números enteros.");
            }
        } catch (NumberFormatException e) {
            util.UtilityFX.alert("Error al insertar", "El campo de ID debe contener solo números enteros.");
        }
    }

//    // Método para buscar cursos y habilitar campos para agregar una lección
//    @FXML
//    public void searchOnAction(ActionEvent actionEvent) {
//        Course selectedCourse = cb_Course.getValue();
//        if (selectedCourse == null) {
//            util.UtilityFX.alert("Error al buscar", "Debe seleccionar un curso.");
//            return;
//        }
//
//        util.UtilityFX.alert("Éxito", "Se ha encontrado el curso.\nHabilitando campos...");
//        txf_id.setDisable(false);
//        txf_title.setDisable(false);
//        txa_Content.setDisable(false);
//        addButton.setDisable(false);
//    }


    private void loadCoursesIntoComboBox() {
        for (Course course : courses.listCourse()) {
            cb_Course.getItems().add(course);
        }
        // Establecer un cell factory para mostrar el ID y el nombre del curso
        cb_Course.setCellFactory(lv -> new ListCell<Course>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                setText(empty ? null : course.getId() + " - " + course.getName());
            }
        });
        // Mostrar el ID y el nombre del curso seleccionado en el ComboBox
        cb_Course.setButtonCell(new ListCell<Course>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                setText(empty ? null : course.getId() + " - " + course.getName());
            }
        });
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
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");
    }

    public boolean isLessonIdUnique(int lessonId) throws TreeException {
        try {
            lessons.loadLessonsFromFile("lessons.txt");
            for (Lesson check : lessons.listLessons())
                if (check.getId() == lessonId)
                    return false;
            return true;
        } catch (TreeException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void enableLessonFields() {
        util.UtilityFX.alert("Éxito", "Se ha encontrado el curso.\nHabilitando campos...");
        txf_id.setDisable(false);
        txf_title.setDisable(false);
        txa_Content.setDisable(false);
        addButton.setDisable(false);
    }

    // Método para deshabilitar los campos de lección
    private void disableLessonFields() {
        txf_id.setDisable(true);
        txf_title.setDisable(true);
        txa_Content.setDisable(true);
        addButton.setDisable(true);
    }

}// End of class [AddLessonsController].
