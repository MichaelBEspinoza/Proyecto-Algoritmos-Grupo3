package controllers;

import domain.Role;
import domain.User;
import domain.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import operations.UserOperations;
import structures.lists.ListException;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import util.UtilityFX;

import java.io.IOException;

public class UsersMaintenanceController {
    @FXML
    private Pane p_anunciosInteres;
    @FXML
    private TableColumn<User, String> tc_city;
    @FXML
    private Pane pane1;
    @FXML
    private TableColumn<User, String> tc_email;
    @FXML
    private TableColumn<User, String> tc_country;
    @FXML
    private TableColumn<User, String> tc_name;
    @FXML
    private TableColumn<User, String> tc_place;
    @FXML
    private BorderPane bp;
    @FXML
    private TableView<User> tv;

    private final UserOperations userOperations;
    @FXML
    private TableColumn<User, Integer> tc_cedula;
    @FXML
    private TableColumn<User, Role> tc_rol;

    private User loggedUser = UserSession.getInstance().getLoggedUser();


    public UsersMaintenanceController() {
        userOperations = new UserOperations();
    }

    public void initialize() {
        tc_cedula.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tc_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        tc_country.setCellValueFactory(new PropertyValueFactory<>("country"));
        tc_place.setCellValueFactory(new PropertyValueFactory<>("place"));
        tc_rol.setCellValueFactory(new PropertyValueFactory<>("role"));
        loadUsersIntoTableView();
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
    public void mainPageOnAction(ActionEvent actionEvent) {
        loadPage("mainPage.fxml");
    }

    @FXML
    public void editInformationOnAction(ActionEvent actionEvent) {
        if (loggedUser.getRole() != Role.ADMINISTRATOR)
            loadPage("userEditProfile.fxml");
        else loadPage("adminEditProfiles.fxml");
    }

    private void loadUsersIntoTableView() {
        ObservableList<User> usersList = FXCollections.observableArrayList();

        userOperations.loadUsersFromFile("users.txt");

        try {
            for (int i = 1; i <= userOperations.listUsers().size(); i++) {
                User user = (User) userOperations.listUsers().getNode(i).data;
                usersList.add(user);
            }
        } catch (ListException e) {
            e.printStackTrace();
        }

        tv.setItems(usersList);
    }


    @FXML
    public void inscriptiontoCourseOnAction(ActionEvent actionEvent) {
        String userType = String.valueOf(UserSession.getInstance().getLoggedUser().getRole()); // Obtén el tipo de usuario actual
        if ("INSTRUCTOR".equals(userType) || "ADMINISTRATOR".equals(userType)) {
            bp.getChildren().clear();
            loadPage("inscriptionPerCourseReport.fxml");
        } else
            UtilityFX.alert("Permiso denegado", "No tiene los permisos necesarios para acceder a reportes.");


    }

    @FXML
    public void progressOnAction(ActionEvent actionEvent) {


    }

    @FXML
    public void evaluationOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void reportsOnAction(ActionEvent actionEvent) {

    }
}
