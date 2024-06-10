package operations;

import domain.Course;
import domain.User;
import interfaces.UserMaintenance;
import structures.lists.CircularDoublyLinkedList;
import structures.lists.ListException;
import structures.lists.SinglyLinkedList;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UserOperations implements UserMaintenance {
    // Implementación de librería Logger para manejar excepciones de una forma más robusta, siguiendo recomendaciones de IntelliJ.
    private static final Logger logger = Logger.getLogger(UserOperations.class.getName());
    // Instancia de la clase de 'CircularDoublyLinkedList' que va a mantener todos los usuarios.
    private CircularDoublyLinkedList users = new CircularDoublyLinkedList();
    // Instancia de la clase de 'SecurityOperations' para encriptar la contraseña del usuario.
    private final SecurityOperations encrypt = new SecurityOperations();

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
    public boolean createUser(User user) throws ListException {
        /* Método que verifica si un usuario es nulo o ya existe en la lista antes de crearlo según el parámetro recibido.
          @param user Usuario a insertar en la lista.
          @return true si el usuario se inserta efectivamente, de otro modo @return false*/

        loadUsersFromFile("users.txt");

        for (int i = 1; i < users.size(); i++) {
            User u = (User) users.getNode(i).data;
            if (u.getId() == user.getId()) {
                System.out.println("El usuario con este ID ya existe");
                return false; // El curso con este ID ya existe
            }
        }

        users.add(user);
        saveUsersToFile("users.txt"); // Guarda los cursos en un archivo
        System.out.println("El usuario fue agregado");

        String longMessage = "Dear " + user.getName() +",\n" +
                "\n" +
                "We are thrilled to welcome you to MyOnlineLearning! Thank you for creating an account with us. We are excited to have you as a part of our community and look forward to supporting you on your learning journey.\n" +
                "\n" +
                "MyOnlineLearning provides a wide range of courses designed to help you develop new skills and advance your career. Whether you're looking to learn a new language, improve your coding skills, or gain knowledge in business management, we have the right course for you.\n" +
                "\n" +
                "To get started, simply log in to your account and explore the various courses available. You can also customize your learning experience by setting goals and tracking your progress through our user-friendly dashboard.\n" +
                "\n" +
                "If you have any questions or need assistance, our support team is here to help. Feel free to reach out to us at support@myonlinelearning.com.\n" +
                "\n" +
                "We are committed to providing you with the best learning experience possible and can't wait to see you succeed.\n" +
                "\n" +
                "Welcome aboard!\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "The MyOnlineLearning Team";
        sendEmailNotification(user,longMessage);
        viewAllUsers("users.txt");
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
            if (users.isEmpty()) return false; // La lista está vacía, no hay usuarios que borrar.

            // Buscar el usuario con el ID proporcionado.
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;

                if (user == users.getNode(1).data) { // Si es el primer elemento en la lista.
                    users.removeFirst();
                    return true;
                }// End of 'if'.
                else if (user == users.getNode(users.size()).data) { // Si es el último elemento en la lista.
                    users.removeLast();
                    return true;
                }// End of 'if'
                else if (user.getId() == userId) { // Si no es ninguno de los dos escenarios anteriores.
                    users.remove(user);
                    return true; // Usuario borrado exitosamente.
                }// End of 'if'.
            }// End of 'for' loop.
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while deleting user.", e);
        }// End of 'catch'.
        return false; // No se encontró el usuario a borrar
    }// End of method [deleteUser].

    @Override
    public CircularDoublyLinkedList listUsers() {
        /* Método que retorna la lista que contiene todos los usuarios ingresados hasta un momento en particular.
           @return List<User> Lista con los usuarios agregados. */
        return users;
    }// End of method [listUsers].

    @Override
    public void sendEmailNotification(User user, String message) {
        /* Método que envía una notificación por correo electrónico a la dirección del usuario pasado como parámetro.
           @param user Usuario al que se le enviará la notificación, por medio de su correo electrónico.
           @param message Mensaje incluido en el correo enviado al usuario.*/

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("myonlinelearning229@gmail.com", "rdrf zmfa szrk ifiy"); // No compartan esta info. porfa.
            }// End of 'PasswordAuthentication'.
        });

        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail(), true));
            mimeMessage.setSubject("MyOnlineLearning Authentication");
            mimeMessage.setText(message);
            System.out.println("sending...");
            Transport.send(mimeMessage);
            System.out.println("Sent message successfully....");
        }catch (MessagingException me){logger.log(Level.SEVERE, "Error while sending e-mail.", me);
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
                    user.setPassword(newPassword);
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

    public void saveUsersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                writer.write(user.toString());
                writer.newLine();
            }
            logger.log(Level.INFO, "Users saved to file successfully.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while writing users to file.", e);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadUsersFromFile(String filename) {
        users.clear();
        File file = new File(filename);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    logger.log(Level.INFO, "File created: " + file.getName());
                    users = new CircularDoublyLinkedList();
                } else {
                    logger.log(Level.WARNING, "Failed to create file.");
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error while creating the file.", e);
            }
        } else {
            if (file.length() == 0) { // Si el archivo está vacío
                logger.log(Level.INFO, "File is empty. Starting with an empty user list.");
                users = new CircularDoublyLinkedList();
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                users = new CircularDoublyLinkedList();
                String line;
                while ((line = reader.readLine()) != null) {
                    User user = User.fromString(line);
                    users.add(user);
                }
                logger.log(Level.INFO, "Users loaded from file successfully.");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error while loading users from file.", e);
                users = new CircularDoublyLinkedList();
            }
        }
    }


    public User getUserByUsername(String username) throws ListException {
        for (int i = 0; i < users.size(); i++) {
            User user = (User) users.getNode(i).data;
            if (user.getName().equals(username))
                return user;
        }
        return null;
    }

    public void viewAllUsers(String filename) throws ListException {
        loadUsersFromFile(filename); // Cargar los cursos desde el archivo
        for (int i = 1; i < users.size(); i++) {
            User user = (User) users.getNode(i).data;
            System.out.println(user); // Imprimir cada curso
        }
    }

    public boolean assignCourseToUser(int userId, Course course) {
        try {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                if (user.getId() == userId) {
                    user.addCourse(course);
                    return true;
                }
            }
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while assigning course to user.", e);
        }
        return false;
    }
    public CircularDoublyLinkedList listAllCourses() {
        CircularDoublyLinkedList allCourses = new CircularDoublyLinkedList();
        try {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                SinglyLinkedList userCourses = user.getCourses();
                if (userCourses != null) {
                    for (int j = 1; j <= userCourses.size(); j++) {
                        Course course = (Course) userCourses.getNode(j).data;
                        allCourses.add(course);
                    }
                }
            }
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while listing all courses.", e);
        }
        return allCourses;
    }

}// End of class [UserOperations].