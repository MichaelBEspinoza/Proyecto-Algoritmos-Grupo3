package domain;

import structures.lists.ListException;
import structures.lists.Node;
import structures.lists.SinglyLinkedList;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String password;
    private String email;
    private Role role;
    private String country;
    private String city;
    private String place;
    private SinglyLinkedList courses;

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

    public SinglyLinkedList getCourses() {
        return courses;
    }

    public void setCourses(SinglyLinkedList courses) {
        this.courses = courses;
    }

    public StringBuilder coursesToString() throws ListException {
        StringBuilder list = new StringBuilder();
        for (int i = 1; i <= courses.size(); i++) { // Cambiado a 1-based index
            Node node = courses.getNode(i);
            if (node != null) {
                Course check = (Course) node.data;
                if (check != null) {
                    list.append(check.getId()).append(" - ").append(check.getName()).append("\n");
                }
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
        return id + "," + name + "," + password + "," + email + "," + role + "," + country + "," + city + "," + place;
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
            return new User(id, name, password, email, role, country, city, place);
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
        if (courses == null) courses = new SinglyLinkedList();
        courses.add(course);
    }
}