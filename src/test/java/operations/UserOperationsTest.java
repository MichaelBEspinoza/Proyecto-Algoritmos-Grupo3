package operations;

import domain.User;
import domain.Role;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.Assert.*;

public class UserOperationsTest {
    private UserOperations userOperations;
    private User user1;
    private User user2;

    //TODO: verificar pruebas, corregirlas y hacer que pasen. Ahora mismo solo están como placeholders.

    @BeforeEach
    public void setUp() {
        userOperations = new UserOperations();
        user1 = new User(1, "John Doe", "password1", "davidcalvogam@example.com", Role.USER);
        user2 = new User(2, "Jane Smith", "password2", "jane@example.com", Role.ADMINISTRATOR);
    }

    @Test
    public void testCreateUser() {
        assertTrue(userOperations.createUser(user1));
        assertFalse(userOperations.createUser(user1));
    }

    @Test
    public void testReadUser() {
        userOperations.createUser(user1);
        User retrievedUser = userOperations.readUser(1);
        assertEquals(user1, retrievedUser);
        assertNull(userOperations.readUser(3));
    }

    @Test
    public void testUpdateUser() {
        userOperations.createUser(user1);
        User updatedUser = new User(1, "John Updated", "newpassword", "johnupdated@example.com", Role.USER);
        assertTrue(userOperations.updateUser(updatedUser));
        User retrievedUser = userOperations.readUser(1);
        assertEquals("John Updated", retrievedUser.getName());
        assertEquals("newpassword", retrievedUser.getPassword());
        assertEquals("johnupdated@example.com", retrievedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        userOperations.createUser(user1);
        assertTrue(userOperations.deleteUser(1));
        assertFalse(userOperations.deleteUser(1));
    }

    @Test
    public void testListUsers() {
        userOperations.createUser(user1);
        userOperations.createUser(user2);
        List<User> users = userOperations.listUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    public void testSendEmailNotification() {
        userOperations.createUser(user1);
        userOperations.sendEmailNotification(user1, "Test message");
    }

    @Test
    public void testUpdateProfile() {
        userOperations.createUser(user1);
        User updatedUser = new User(1, "John Updated", "newpassword", "johnupdated@example.com", Role.USER);
        assertTrue(userOperations.updateProfile(updatedUser));
        User retrievedUser = userOperations.readUser(1);
        assertEquals("John Updated", retrievedUser.getName());
    }

    @Test
    public void testChangePassword() {
        userOperations.createUser(user1);
        assertTrue(userOperations.changePassword(1, "newpassword"));
        User retrievedUser = userOperations.readUser(1);
        assertEquals("newpassword", retrievedUser.getPassword());
        assertFalse(userOperations.changePassword(1, "newpassword"));
    }

    @Test
    public void testUserExists() {
        userOperations.createUser(user1);
        assertTrue(userOperations.userExists(user1));
        assertFalse(userOperations.userExists(user2));
    }
}// End of class [UserOperationsTest].