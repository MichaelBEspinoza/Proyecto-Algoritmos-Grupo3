package domain;

import structures.lists.ListException;
import structures.lists.SinglyLinkedList;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    private int id; /**Identificador del usuario**/

    private String name; /**Nombre del usuario**/

    private String password; /**Contraseña del usuario (se guarda encriptada)**/

    private String email; /**Correo electronico del usuario**/

    private Role role; /**Rol del usuario (Administrador,Instructor, usuario**/

    private SinglyLinkedList courses;

    @Serial
    private static final long serialVersionUID = 1L;

    public User() {
    }

    public User(int id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(int id, String name, String password, String email, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

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

    public SinglyLinkedList getCourses() {
        return courses;
    }

    public void setCourses(SinglyLinkedList courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public StringBuilder coursesToString() throws ListException {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < courses.size(); i++) {
            Course check = (Course) courses.getNode(i).data;
            if (check != null)
                list.append(check.getId()).append(" - ").append(check.getName());
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

    public Role stringToRole(String useThis) {
        if (useThis.trim().equalsIgnoreCase("Usuario")) return Role.USER;
        else if (useThis.trim().equalsIgnoreCase("Administrador")) return Role.ADMINISTRATOR;
        else if (useThis.trim().equalsIgnoreCase("Instructor")) return Role.INSTRUCTOR;
        else return null;
    }

}
