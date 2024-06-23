package operations;

import domain.Course;
import domain.Role;
import domain.User;
import interfaces.UserMaintenance;
import structures.lists.CircularDoublyLinkedList;
import structures.lists.ListException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UserOperations implements UserMaintenance {
    private static final Logger logger = Logger.getLogger(UserOperations.class.getName());
    private CircularDoublyLinkedList users = new CircularDoublyLinkedList();

    static {
        try {
            FileHandler fileHandler = new FileHandler("user_operations.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error configuring logger", e);
        }
    }

    @Override
    public boolean createUser(User user) throws ListException {
        loadUsersFromFile("users.txt");

        for (int i = 1; i <= users.size(); i++) {
            User u = (User) users.getNode(i).data;
            if (u.getId() == user.getId()) {
                return false;
            }
        }

        users.add(user);
        saveUsersToFile("users.txt");

        String longMessage = "Dear " + user.getName() + ",\n" +
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
                "The MyOnlineLearning Team\n" + "Sent at: " + LocalDate.now() + ", " + LocalTime.now();
        sendEmailNotification(user, longMessage);
        viewAllUsers("users.txt");
        return true;
    }

    @Override
    public User readUser(int userId) {
        loadUsersFromFile("users.txt");
        try {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                if (user.getId() == userId)
                    return user;
            }
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while reading user.", e);
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            for (int i = 1; i <= users.size(); i++) {
                User currentUser = (User) users.getNode(i).data;
                if (currentUser.getId() == user.getId()) {
                    users.getNode(i).data = user;
                    saveUsersToFile("users.txt");
                    return true;
                }
            }
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while updating user.", e);
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        try {
            if (users.isEmpty()) return false;

            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                if (user.getId() == userId) {
                    users.remove(user);
                    saveUsersToFile("users.txt");
                    return true;
                }
            }
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while deleting user.", e);
        }
        return false;
    }

    @Override
    public CircularDoublyLinkedList listUsers() {
        return users;
    }

    @Override
    public void sendEmailNotification(User user, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("myonlinelearning229@gmail.com", "rdrf zmfa szrk ifiy");
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail(), true));
            mimeMessage.setSubject("MyOnlineLearning Authentication");
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException me) {
            logger.log(Level.SEVERE, "Error while sending e-mail.", me);
        }
    }

    @Override
    public boolean updateProfile(User user) {
        return updateUser(user);
    }

    @Override
    public boolean changePassword(int userId, String newPassword) {
        try {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                if (user.getId() == userId && !user.getPassword().equals(newPassword)) {
                    user.setPassword(newPassword);
                    saveUsersToFile("users.txt");
                    return true;
                }
            }
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error changing password.", e);
        }
        return false;
    }

    public boolean userExists(User userToSearch) {
        try {
            if (userToSearch == null) return false;
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                if (user.getId() == userToSearch.getId())
                    return true;
            }
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while checking if user exists.", e);
        }
        return false;
    }

    public void saveUsersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                writer.write(userToString(user));
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
            if (file.length() == 0) {
                logger.log(Level.INFO, "File is empty. Starting with an empty user list.");
                users = new CircularDoublyLinkedList();
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                users = new CircularDoublyLinkedList();
                String line;
                while ((line = reader.readLine()) != null) {
                    User user = userFromString(line);
                    users.add(user);
                }
                logger.log(Level.INFO, "Users loaded from file successfully.");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error while loading users from file.", e);
                users = new CircularDoublyLinkedList();
            }
        }
    }

    private String userToString(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getId()).append(",").append(user.getName()).append(",")
                .append(user.getPassword()).append(",").append(user.getEmail()).append(",")
                .append(user.getRole()).append(",").append(user.getCountry()).append(",")
                .append(user.getCity()).append(",").append(user.getPlace());

        sb.append(",{");
        for (Course course : user.getCourses()) {
            sb.append(course.getId()).append(":").append(course.getName()).append(";");
        }
        sb.append("}");

        return sb.toString();
    }

    private User userFromString(String str) {
        String[] parts = str.split(",\\{");
        String[] userDetails = parts[0].split(",");
        if (userDetails.length < 8) {
            throw new IllegalArgumentException("Invalid user data: " + str);
        }

        int id = Integer.parseInt(userDetails[0]);
        String name = userDetails[1];
        String password = userDetails[2];
        String email = userDetails[3];
        Role role = Role.valueOf(userDetails[4]);
        String country = userDetails[5];
        String city = userDetails[6];
        String place = userDetails[7];
        User user = new User(id, name, password, email, role, country, city, place);

        if (parts.length > 1) {
            String coursesPart = parts[1].substring(0, parts[1].length() - 1);
            String[] courses = coursesPart.split(";");
            for (String courseStr : courses) {
                if (!courseStr.trim().isEmpty()) {
                    String[] courseDetails = courseStr.split(":");
                    int courseId = Integer.parseInt(courseDetails[0].trim());
                    String courseName = courseDetails[1].trim();
                    user.addCourse(new Course(courseId, courseName));
                }
            }
        }

        return user;
    }

    public void viewAllUsers(String filename) throws ListException {
        loadUsersFromFile(filename);
        for (int i = 1; i < users.size(); i++) {
            User user = (User) users.getNode(i).data;
            System.out.println(user);
        }
    }

    public List<User> listUsersArray() {
        List<User> userList = new ArrayList<>();
        try {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                userList.add(user);
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<String> getUserNames() {
        List<String> userNames = new ArrayList<>();
        try {
            for (int i = 1; i <= users.size(); i++) {
                User user = (User) users.getNode(i).data;
                userNames.add(user.getName());
            }
        } catch (ListException e) {
            logger.log(Level.SEVERE, "Error while getting user names.", e);
        }
        return userNames;
    }
}
