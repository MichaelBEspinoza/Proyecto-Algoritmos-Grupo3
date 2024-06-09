package controllers;

import domain.User;
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
    private TableColumn<User, String> tc_cedula;
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
    private TableColumn<User, String> tc_rol;

    public UsersMaintenanceController() {
        userOperations = new UserOperations();
    }

    public void initialize() {
        // Configurar las columnas del TableView
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tc_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        tc_country.setCellValueFactory(new PropertyValueFactory<>("country"));
        tc_cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        tc_rol.setCellValueFactory(new PropertyValueFactory<>("role"));
        tc_place.setCellValueFactory(new PropertyValueFactory<>("place"));

        loadUsersIntoTableView();
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
    public void mainPageOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");}

    @FXML
    public void editInformationOnAction(ActionEvent actionEvent) {loadPage("userEditProfile.fxml");}

    private void loadUsersIntoTableView() {
        ObservableList<User> usersList = FXCollections.observableArrayList();

        // Cargar usuarios desde el archivo
        userOperations.loadUsersFromFile("users.txt");

        try {
            for (int i = 1; i <= userOperations.listUsers().size(); i++) {
                User user = (User) userOperations.listUsers().getNode(i).data;
                usersList.add(user);
            }
        } catch (ListException e) {
            e.printStackTrace();
        }

        // Establecer los datos en el TableView
        tv.setItems(usersList);
    }
}