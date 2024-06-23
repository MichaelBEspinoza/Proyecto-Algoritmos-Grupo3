package controllers;

import domain.Course;
import domain.Lesson;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import operations.CourseOperations;
import operations.LessonOperations;
import structures.trees.TreeException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class courseLessonsController {
    @javafx.fxml.FXML
    private Menu menuPaginaPrincipal;
    @javafx.fxml.FXML
    private Menu menuAyuda;
    @javafx.fxml.FXML
    private Pane pane1;
    @javafx.fxml.FXML
    private TableColumn<Lesson, String> tc_contenido;
    @javafx.fxml.FXML
    private TableView<Lesson> tableView;
    @javafx.fxml.FXML
    private TableColumn<Lesson, Integer> tc_id;
    @javafx.fxml.FXML
    private TableColumn<Lesson, String> tc_name;
    @javafx.fxml.FXML
    private TableColumn<Lesson, String> tc_curso;
    @javafx.fxml.FXML
    private Menu menuCursos;
    @javafx.fxml.FXML
    private TableColumn<Lesson, String> tc_estado;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TableColumn<Lesson, Integer> tc_instructorId;

    private final LessonOperations lessonsOperations = new LessonOperations();
    @javafx.fxml.FXML
    private ComboBox<Course> cb_Course;
    private CourseOperations CO = new CourseOperations();

    public void initialize() {
        loadCoursesIntoComboBox();
        setupTableView();
        cb_Course.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillTableViewWithCourseLessons(newSelection);
            }
        });

        if (!cb_Course.getItems().isEmpty()) {
            cb_Course.getSelectionModel().selectFirst();
        }
    }

    private void loadCoursesIntoComboBox() {
        List<Course> courses = CO.listCourse();
        cb_Course.setItems(FXCollections.observableArrayList(courses));
        cb_Course.setCellFactory(lv -> new ListCell<Course>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getId() + " - " + item.getName());
            }
        });
        cb_Course.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course object) {
                return object == null ? "" : object.getId() + " - " + object.getName();
            }
            @Override
            public Course fromString(String string) {
                return null;
            }
        });
    }

    private void setupTableView() {
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_name.setCellValueFactory(new PropertyValueFactory<>("title"));
        tc_contenido.setCellValueFactory(new PropertyValueFactory<>("content"));
        tc_curso.setCellValueFactory(new PropertyValueFactory<>("course"));
        tc_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tc_instructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));

        tableView.setRowFactory(tv -> {
            TableRow<Lesson> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Lesson clickedRow = row.getItem();
                    try {
                        showCompletionDialog(clickedRow);
                    } catch (TreeException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
        fillTableView();
    }

    private void fillTableView() {
        try {
            tableView.setItems(FXCollections.observableArrayList(lessonsOperations.listLessons()));
        } catch (TreeException e) {
            e.printStackTrace();
        }
    }

    private void showCompletionDialog(Lesson lesson) throws TreeException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de completitud");
        alert.setHeaderText("¿Lección completada?");
        alert.setContentText("¿Ha completado la lección '" + lesson.getTitle() + "'?");

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            boolean validInput = false;
            int grade = 0;
            while (!validInput) {
                String gradeStr = util.UtilityFX.dialog("Asignar nota", "Califique al estudiante según su rendimiento en la lección. (1-100)");
                try {
                    grade = Integer.parseInt(gradeStr);
                    if (grade >= 1 && grade <= 100) {
                        lesson.setGrade(grade);
                        validInput = true;
                    } else {
                        util.UtilityFX.alert("Error en la calificación", "La calificación debe estar entre 1 y 100.");
                    }
                } catch (NumberFormatException e) {
                    util.UtilityFX.alert("Entrada Inválida", "Por favor, introduzca un número válido para la calificación.");
                }
            }
            lesson.setGrade(grade);
            lessonsOperations.updateLesson(lesson);
            lessonsOperations.saveLessonsToFile("lessons.txt");
        } else {
            util.UtilityFX.alert("No completada", "La lección no ha sido completada.");
        }
    }

    @javafx.fxml.FXML
    public void ayudaOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("usersupport.fxml");
    }

    @javafx.fxml.FXML
    public void pagePrincipalOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("mainPage.fxml");
    }

    @javafx.fxml.FXML
    public void editLessonsOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cursosOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("userCourses.fxml");
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTableViewWithCourseLessons(Course course) {
        try {
            List<Lesson> lessons = lessonsOperations.listLessonsByCourse(course.getId());
            tableView.setItems(FXCollections.observableArrayList(lessons));
        } catch (TreeException e) {
            e.printStackTrace();
        }
    }


}
