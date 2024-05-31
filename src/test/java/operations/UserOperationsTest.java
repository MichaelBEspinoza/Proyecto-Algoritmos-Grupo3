package operations;

import domain.Role;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.lists.ListException;

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
        assertTrue(userOperations.deleteUser(1));
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
}// End of class [UserOperationsTest].