package operations;

import domain.Role;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.lists.CircularDoublyLinkedList;
import structures.lists.ListException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UserOperationsTest {

    private UserOperations userOperations;

    @BeforeEach
    public void setUp() {
        userOperations = new UserOperations();
    }

    @Test
    public void testCreateUser() throws ListException {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER, "Costa Rica", "San Jose", "Place1");
        assertTrue(userOperations.createUser(user));
        assertFalse(userOperations.createUser(user)); // No se puede crear el mismo usuario dos veces.
    }

    @Test
    public void testReadUser() throws ListException {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER, "Costa Rica", "San Jose", "Place1");
        userOperations.createUser(user);
        User retrievedUser = userOperations.readUser(1);
        assertNotNull(retrievedUser);
        assertEquals(1, retrievedUser.getId());
    }

    @Test
    public void testUpdateUser() throws ListException {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER, "Costa Rica", "San Jose", "Place1");
        userOperations.createUser(user);
        User updatedUser = new User(1, "Carlos Bonilla", "newpassword", "car.bon@prueba.com", Role.ADMINISTRATOR, "USA", "New York", "Place2");
        assertTrue(userOperations.updateUser(updatedUser));
        User retrievedUser = userOperations.readUser(1);
        assertEquals("newpassword", retrievedUser.getPassword());
        assertEquals(Role.ADMINISTRATOR, retrievedUser.getRole());
        assertEquals("USA", retrievedUser.getCountry());
        assertEquals("New York", retrievedUser.getCity());
        assertEquals("Place2", retrievedUser.getPlace());
    }

    @Test
    public void testDeleteUser() throws ListException {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER, "Costa Rica", "San Jose", "Place1");
        userOperations.createUser(user);
        assertTrue(userOperations.deleteUser(1));
        assertNull(userOperations.readUser(1));
    }

    @Test
    public void testChangePassword() throws ListException {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER, "Costa Rica", "San Jose", "Place1");
        userOperations.createUser(user);
        assertTrue(userOperations.changePassword(1, "newpassword123"));
        User retrievedUser = userOperations.readUser(1);
        assertEquals("newpassword123", retrievedUser.getPassword());
    }

    @Test
    public void testUserExists() throws ListException {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER, "Costa Rica", "San Jose", "Place1");
        userOperations.createUser(user);
        assertTrue(userOperations.userExists(user));
        User nonExistentUser = new User(2, "Sonya Bonilla", "password456", "so.bon@example.com", Role.USER, "USA", "New York", "Place2");
        assertFalse(userOperations.userExists(nonExistentUser));
    }

    @Test
    void testSaveAndLoadUsers() throws ListException {
        String testFile = "archivoUsuariosPrueba.dat";

        User user1 = new User(1, "David Calvo", "password123", "deivid@test.com", Role.USER, "Costa Rica", "San Jose", "Place1");
        User user2 = new User(2, "Katherine Brenes", "password456", "katharin@test.com", Role.INSTRUCTOR, "USA", "New York", "Place2");

        userOperations.createUser(user1);
        userOperations.createUser(user2);

        // Guarda usuarios en el archivo
        userOperations.saveUsersToFile(testFile);

        // Crear nueva instancia de UserOperations para simular reinicio de la aplicación
        UserOperations newUserOperations = new UserOperations();

        // Cargar usuarios desde el archivo
        newUserOperations.loadUsersFromFile(testFile);

        // Comprobar que los usuarios fueron cargados correctamente
        CircularDoublyLinkedList loadedUsers = newUserOperations.listUsers();

        assertNotNull(loadedUsers);
        assertEquals(2, loadedUsers.size());

        User loadedUser1 = (User) loadedUsers.getNode(1).data;
        User loadedUser2 = (User) loadedUsers.getNode(2).data;

        assertEquals(user1.getId(), loadedUser1.getId());
        assertEquals(user1.getName(), loadedUser1.getName());
        assertEquals(user1.getPassword(), loadedUser1.getPassword());
        assertEquals(user1.getEmail(), loadedUser1.getEmail());
        assertEquals(user1.getRole(), loadedUser1.getRole());
        assertEquals(user1.getCountry(), loadedUser1.getCountry());
        assertEquals(user1.getCity(), loadedUser1.getCity());
        assertEquals(user1.getPlace(), loadedUser1.getPlace());

        assertEquals(user2.getId(), loadedUser2.getId());
        assertEquals(user2.getName(), loadedUser2.getName());
        assertEquals(user2.getPassword(), loadedUser2.getPassword());
        assertEquals(user2.getEmail(), loadedUser2.getEmail());
        assertEquals(user2.getRole(), loadedUser2.getRole());
        assertEquals(user2.getCountry(), loadedUser2.getCountry());
        assertEquals(user2.getCity(), loadedUser2.getCity());
        assertEquals(user2.getPlace(), loadedUser2.getPlace());

        // Eliminar archivo de prueba
        File file = new File(testFile);
        if (!file.delete())
            System.out.println("Failed to delete test file.");
    }

    @Test
    void testLoadUsersFileNotFound() {
        // Intentar cargar un archivo inexistente
        userOperations.loadUsersFromFile("vacio.dat");

        // Comprobar que la lista de usuarios está vacía
        CircularDoublyLinkedList users = userOperations.listUsers();
        assertNotNull(users);
        assertEquals(0, users.size());
    }
}
