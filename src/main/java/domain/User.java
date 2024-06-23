package domain;

import structures.lists.ListException;
import structures.lists.Node;
import structures.lists.SinglyLinkedList;
import structures.trees.BTree;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String name;
    private String password;
    private String email;
    private Role role;
    private String country;
    private String city;
    private String place;
    private List<Course> courses;
    private SinglyLinkedList lessons;
    private SinglyLinkedList completedLessons;
    private SinglyLinkedList grades;
    private BTree enrollments;

    @Serial
    private static final long serialVersionUID = 1L;

    // Constructores
    public User() {
    }

    public User(int id, String name, String password, String email, Role role, String country, String city, String place) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.country = country;
        this.city = city;
        this.place = place;
        this.courses = new ArrayList<>();
        this.lessons = new SinglyLinkedList();
        this.completedLessons = new SinglyLinkedList();
        this.grades = new SinglyLinkedList();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public SinglyLinkedList getLessons() {
        return lessons;
    }

    public void setLessons(SinglyLinkedList lessons) {
        this.lessons = lessons;
    }

    public SinglyLinkedList getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(SinglyLinkedList completedLessons) {
        this.completedLessons = completedLessons;
    }

    public SinglyLinkedList getGrades() {
        return grades;
    }

    public void setGrades(SinglyLinkedList grades) {
        this.grades = grades;
    }

    public BTree getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(BTree enrollments) {
        this.enrollments = enrollments;
    }

    public StringBuilder coursesToString() {
        StringBuilder list = new StringBuilder();
        for (Course course : courses) {
            if (course != null) {
                list.append(course.getId()).append(" - ").append(course.getName()).append("\n");
            }
        }
        return list;
    }

    public String roleToString() {
        String roleStr;
        switch (getRole()) {
            case USER -> roleStr = "Usuario";
            case ADMINISTRATOR -> roleStr = "Administrador";
            case INSTRUCTOR -> roleStr = "Instructor";
            case null, default -> roleStr = "N/A";
        }
        return roleStr;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + password + "," + email + "," + role + "," + country + "," + city + "," + place + "," + coursesToString();
    }

    public static User fromString(String userString) {
        String[] parts = userString.split(",");
        if (parts.length >= 8) {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String password = parts[2];
            String email = parts[3];
            Role role = Role.valueOf(parts[4]);
            String country = parts[5];
            String city = parts[6];
            String place = parts[7];
            User user = new User(id, name, password, email, role, country, city, place);
            for (int i = 8; i < parts.length; i++) {
                String[] courseParts = parts[i].split("-");
                if (courseParts.length == 2) {
                    int courseId = Integer.parseInt(courseParts[0].trim());
                    String courseName = courseParts[1].trim();
                    user.addCourse(new Course(courseId, courseName, "", "", "", 0));
                }
            }
            return user;
        } else {
            throw new IllegalArgumentException("Formato de cadena incorrecto para crear un usuario: " + userString);
        }
    }

    public Role stringToRole(String useThis) {
        if (useThis.trim().equalsIgnoreCase("Usuario")) return Role.USER;
        else if (useThis.trim().equalsIgnoreCase("Administrador")) return Role.ADMINISTRATOR;
        else if (useThis.trim().equalsIgnoreCase("Instructor")) return Role.INSTRUCTOR;
        else return null;
    }

    public void addCourse(Course course) {
        if (courses == null) courses = new ArrayList<>();
        courses.add(course);
    }

    public boolean hasCompletedLesson(int lessonId) throws ListException {
        for (int i = 1; i <= completedLessons.size(); i++) {
            Node node = completedLessons.getNode(i);
            if (node != null && (int) node.data == lessonId) {
                return true;
            }
        }
        return false;
    }

    public double getGrade(int lessonId) throws ListException {
        for (int i = 1; i <= grades.size(); i++) {
            Node node = grades.getNode(i);
            if (node != null && node.data instanceof Grade) {
                Grade grade = (Grade) node.data;
                if (grade.getLessonId() == lessonId) {
                    return grade.getGrade();
                }
            }
        }
        return 0.0;
    }

    public void addCompletedLesson(int lessonId, double grade) throws ListException {
        if (!hasCompletedLesson(lessonId)) {
            completedLessons.add(lessonId);
            grades.add(new Grade(lessonId, grade));
        }
    }
}
