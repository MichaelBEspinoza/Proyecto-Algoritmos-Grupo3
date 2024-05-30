package operations;

import domain.User;
import interfaces.UserMaintenance;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.LinkedList;

public class UserOperations implements UserMaintenance {
    private final List<User> users = new LinkedList<>();

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
        for (User check : users)
            if (check.getId() == userId)
                return check;
        return null;
    }// End of method [readUser].

    @Override
    public boolean updateUser(User user) {
        /* Método que actualiza los datos de un usuario según los recibidos como parámetro.
           @param user Datos del usuario que reemplazarán los antiguos encontrados en la lista.
           @return true Si se actualizan los datos exitosamente, false si no.*/
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getId() == user.getId()) {
                users.set(i, new User(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getRole()));
                return true;
            }// End of 'if'.
        return false;
    }// End of method [updateUser].

    @Override
    public boolean deleteUser(int userId) {
        /* Método que suprime a un usuario si lo encuentra en la lista, según el ID recibido por parámetro.
           @param userID ID del usuario a buscar y borrar.
           @return true Si se borra exitosamente, false si no se suprime nada.*/
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getId() == userId) {
                users.remove(i);
                return true;
            }// End of 'if'.
        return false;
    }// End of method [deleteUser].

    @Override
    public List<User> listUsers() {
        /* Método que retorna la lista que contiene todos los usuarios ingresados hasta un momento en particular.
           @return List<User> Lista con los usuarios agregados. */
        return new ArrayList<>(users);
    }// End of method [listUsers].

    @Override
    public void sendEmailNotification(User user, String message) {
        /* Método que envía una notificación por correo electrónico a la dirección del usuario pasado como parámetro.
           @param user Usuario al que se le enviará la notificación, por medio de su correo electrónico.
           @param message Mensaje incluido en el correo enviado al usuario.*/

        // Tomado de: ! https://www.geeksforgeeks.org/send-email-using-java-program/ !
        String recipient = user.getEmail(), sender = "myonlinelearning@gmail.com", host = "127.0.0.1";

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
        } catch (MessagingException mex) {mex.printStackTrace();}
    }// End of method [sendEmailNotification].

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
        for (int i = 0; i < users.size(); i++) {
            User check = users.get(i);
            if (check.getId() == userId && !Objects.equals(check.getPassword(), newPassword)) {
                users.set(i, new User(check.getId(), check.getName(), newPassword, check.getEmail(), check.getRole()));
                return true;
            }// End of 'if'.
        }// End of 'for'.
        return false;
    }// End of method [changePassword].

    public boolean userExists(User userToSearch) {
        /* Método de utilidad que comprueba si un usuario existe (está incluido en la lista) o no.
           @param userToSearch Usuario que se debe comprobar si está añadido a la lista principal.
           @return true Si el usuario existe, false si no.*/
        if (userToSearch == null) return false;
        for (User user : users)
            if (user.getId() == userToSearch.getId())
                return true;
        return false;
    }// End of method [userExists].
}// End of class [UserOperations].

