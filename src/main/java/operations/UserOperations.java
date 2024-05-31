package operations;

import domain.User;
import interfaces.UserMaintenance;
import structures.lists.ListException;
import structures.lists.SinglyLinkedList;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UserOperations implements UserMaintenance {
    // Implementación de librería Logger para manejar excepciones de una forma más robusta, siguiendo recomendaciones de IntelliJ.
    private static final Logger logger = Logger.getLogger(UserOperations.class.getName());
    // Instancia de la clase de 'SinglyLinkedList' que va a mantener todos los usuarios.
    private final SinglyLinkedList users = new SinglyLinkedList();

    static { // Bloque estático asegura que se pueda llamar a lo largo de la clase.
        try {
            // Manipulador de archivos que guarda las excepciones registradas en un archivo de extensión 'log'.
            FileHandler fileHandler = new FileHandler("user_operations.log", true);
            // Declara archivo con nombre y habilita el amontonamiento de excepciones.
            fileHandler.setLevel(Level.ALL); // Asegura que recibe todos los niveles de advertencia en las excepciones.
            fileHandler.setFormatter(new SimpleFormatter()); // Formatea el archivo para que se vea legible y lindo.
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error configuring logger", e);
        }// End of 'catch'.
    }// End of static block.

    @Override
    public boolean createUser(User user) {
        /* Método que verifica si un usuario es nulo o ya existe en la lista antes de crearlo según el parámetro recibido.
          @param user Usuario a insertar en la lista.
          @return true si el usuario se inserta efectivamente, de otro modo @return false*/
        if (user == null || userExists(user)) return false;
        users.add(new User(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getRole()));
        return true;
    }// End of method [createUser].

    @Override
    public User readUser(int userId) {
        /* Método que retorna al usuario correspondiente al ID que recibe por parámetro. De no encontrarlo, retorna 'null'
           @param userID ID del usuario a buscar y retornar.
           @return check Que corresponde al usuario encontrado.*/
        try {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                if (user.getId() == userId)
                    return user;
            }// End of 'for' loop.
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while reading user.", e);
        }// End of 'catch'.
        return null;
    }// End of method [readUser].

    @Override
    public boolean updateUser(User user) {
        /* Método que actualiza los datos de un usuario según los recibidos como parámetro.
           @param user Datos del usuario que reemplazarán los antiguos encontrados en la lista.
           @return true Si se actualizan los datos exitosamente, false si no.*/
        try {
            for (int i = 1; i <= users.size(); i++) {
                User currentUser = (User) users.getNode(i).data;
                if (currentUser.getId() == user.getId()) {
                    users.getNode(i).data = new User(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getRole());
                    return true;
                }// End of 'if'.
            }// End of 'for' loop.
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while updating user.", e);
        }// End of 'catch'.
        return false;
    }// End of method [updateUser].

    @Override
    public boolean deleteUser(int userId) {
        /* Método que suprime a un usuario si lo encuentra en la lista, según el ID recibido por parámetro.
           @param userID ID del usuario a buscar y borrar.
           @return true Si se borra exitosamente, false si no se suprime nada.*/
        try {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                if (user.getId() == userId) {
                    users.remove(user);
                    return true;
                }// End of 'if'.
            }// End of 'for' loop.
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while deleting user.", e);
        }// End of 'catch'.
        return false;
    }// End of method [deleteUser].

    @Override
    public SinglyLinkedList listUsers() {
        /* Método que retorna la lista que contiene todos los usuarios ingresados hasta un momento en particular.
           @return List<User> Lista con los usuarios agregados. */
        return users;
    }// End of method [listUsers].

    @Override
    public void sendEmailNotification(User user, String message) {
        /* Método que envía una notificación por correo electrónico a la dirección del usuario pasado como parámetro.
           @param user Usuario al que se le enviará la notificación, por medio de su correo electrónico.
           @param message Mensaje incluido en el correo enviado al usuario.*/

        // Tomado de: ! https://www.geeksforgeeks.org/send-email-using-java-program/ !
        String recipient = user.getEmail();
        String sender = "myonlinelearning@gmail.com";
        String host = "127.0.0.1";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(sender));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mimeMessage.setSubject("MyOnlineLearning Notification System");
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException mex) {
            logger.log(Level.SEVERE, "Error sending e-mail.", mex);
        }// End of 'catch'.
    }// End of method [sendEmailNotifications].

    @Override
    public boolean updateProfile(User user) {
        /* Método que permite al usuario editar su propio perfil.
           @param user Usuario cuyo perfil será actualizado.
           @return true/false, Véase 'updateUser'.*/
        return updateUser(user);
    }// End of method [updateProfile].

    @Override
    public boolean changePassword(int userId, String newPassword) {
        /* Método que cambia únicamente la contraseña del usuario cuya ID es igual a la recibida como parámetro.
          @param userId Identificador que distingue al usuario cuya contraseña se ha de borrar.
          @param newPassword Nueva contraseña que debe reemplazar a la vieja.
          @return true Si la contraseña se cambia con éxito, false si el cambio no pudo ser efectuado. */
        try {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                if (user.getId() == userId && !user.getPassword().equals(newPassword)) {
                    users.getNode(i).data = new User(user.getId(), user.getName(), newPassword, user.getEmail(), user.getRole());
                    return true;
                }// End of 'if'.
            }// End of 'for' loop.
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error changing password.", e);
        }// End of 'catch'.
        return false;
    }// End of method [changePassword].

    public boolean userExists(User userToSearch) {
        /* Método de utilidad que comprueba si un usuario existe (está incluido en la lista) o no.
           @param userToSearch Usuario que se debe comprobar si está añadido a la lista principal.
           @return true Si el usuario existe, false si no.*/
        try {
            if (userToSearch == null) return false;
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                if (user.getId() == userToSearch.getId())
                    return true;
            }// End of 'for' loop.
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while checking if user exists.", e);
        }// End of 'catch'.
        return false;
    }// End of method [userExists].
}// End of class [UserOperations].