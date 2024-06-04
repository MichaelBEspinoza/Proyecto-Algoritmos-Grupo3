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

    // Importante: como la mayoría de los métodos de la clase 'UserOperations' devuelven un valor booleano, considero que
    // es imperativo usar los 'assertTrue', 'assertFalse', y sus derivados. Lo dejo a su decisión.
    //                                                                                                          -- David

    @BeforeEach
    public void setUp() {
        userOperations = new UserOperations();
    }// End of method [setUp].

    @Test
    public void testCreateUser() {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER);
        assertTrue(userOperations.createUser(user));
        assertFalse(userOperations.createUser(user)); // No se puede crear el mismo usuario dos veces.
    }// Test #1.

    @Test
    public void testReadUser() {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER);
        userOperations.createUser(user);
        User retrievedUser = userOperations.readUser(1);
        assertNotNull(retrievedUser);
        assertEquals(1, retrievedUser.getId());
    }// Test #2.

    @Test
    public void testUpdateUser() throws ListException {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER);
        userOperations.createUser(user);
        User updatedUser = new User(1, "Carlos Bonilla", "newpassword", "car.bon@prueba.com", Role.ADMINISTRATOR);
        assertTrue(userOperations.updateUser(updatedUser));
        User retrievedUser = userOperations.readUser(1);
        assertEquals("newpassword", retrievedUser.getPassword());
        assertEquals(Role.ADMINISTRATOR, retrievedUser.getRole());
    }// Test #3.

    @Test
    public void testDeleteUser() throws ListException {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER);
        userOperations.createUser(user);
        System.out.println(userOperations.listUsers());
        assertTrue(userOperations.deleteUser(1));
        System.out.println(userOperations.listUsers());
        assertNull(userOperations.readUser(1));
    }// Test #4.

    @Test
    public void testChangePassword() {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER);
        userOperations.createUser(user);
        assertTrue(userOperations.changePassword(1, "newpassword123"));
        User retrievedUser = userOperations.readUser(1);
        assertEquals("newpassword123", retrievedUser.getPassword());
    }// Test #5.

    @Test
    public void testUserExists() {
        User user = new User(1, "Carlos Bonilla", "password123", "car.bon@prueba.com", Role.USER);
        userOperations.createUser(user);
        assertTrue(userOperations.userExists(user));
        User nonExistentUser = new User(2, "Sonya Bonilla", "password456", "so.bon@example.com", Role.USER);
        assertFalse(userOperations.userExists(nonExistentUser));
    }// Test #6.

    @Test
    void testSaveAndLoadUsers() throws ListException {
        String testFile = "archivoUsuariosPrueba.dat";

        User user1 = new User(1, "David Calvo", "password123", "deivid@test.com", Role.USER);
        User user2 = new User(2, "Katherine Brenes", "password456", "katharin@test.com", Role.INSTRUCTOR);

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

        assertEquals(user2.getId(), loadedUser2.getId());
        assertEquals(user2.getName(), loadedUser2.getName());
        assertEquals(user2.getPassword(), loadedUser2.getPassword());
        assertEquals(user2.getEmail(), loadedUser2.getEmail());
        assertEquals(user2.getRole(), loadedUser2.getRole());

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

}// End of class [UserOperationsTest].