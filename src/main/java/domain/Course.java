package domain;

import structures.lists.ListException;
import structures.lists.Node;
import structures.lists.SinglyLinkedList;

import java.io.Serializable;
import java.util.Objects;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id; /**Identificador del curso**/
    private String name; /**Nombre del curso**/
    private String description; /**Descripcion del curso**/
    private String courseLength; /**Duracion del curso**/
    private String level; /**Nivel de dificultad (Low, Medium, High)**/
    private int instructorId; /**Identificador del instructor que imparte el curso**/
    private SinglyLinkedList lessons;
    private int enrollmentCount;

    public Course() {
    }
    public Course(int id) {
        this.id = id;
    }
    public Course(int id,String name) {
        this.id = id;
    }
    public Course(int id, String name, String description, String courseLength, String level, int instructorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.courseLength = courseLength;
        this.level = level;
        this.instructorId = instructorId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(String courseLength) {
        this.courseLength = courseLength;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public SinglyLinkedList getLessons() {
        return lessons;
    }

    public void setLessons(SinglyLinkedList lessons) {
        this.lessons = lessons;
    }

    public void incrementEnrollment() {
        this.enrollmentCount++;
    }

    public void decrementEnrollment() {
        if (this.enrollmentCount > 0) {
            this.enrollmentCount--;
        }
    }

    public int getEnrollmentCount() {
        return this.enrollmentCount;
    }

    public void setEnrollmentCount(int count) {
        this.enrollmentCount = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && instructorId == course.instructorId && Objects.equals(name, course.name) && Objects.equals(description, course.description) && Objects.equals(courseLength, course.courseLength) && Objects.equals(level, course.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, courseLength, level, instructorId);
    }

    @Override
    public String toString() {
        return "Course Details{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", courseLength='" + courseLength + '\'' +
                ", level='" + level + '\'' +
                ", instructorId=" + instructorId +
                '}';
    }

    public StringBuilder lessonsToString() throws ListException {
        StringBuilder list = new StringBuilder();
        for (int i = 1; i <= lessons.size(); i++) { // Cambiado a 1-based index
            Node node = lessons.getNode(i);
            if (node != null) {
                Lesson check = (Lesson) node.data;
                if (check != null) {
                    list.append(check.getId()).append(" - ").append(check.getTitle()).append("\n").append(check.getContent());
                }
            }
        }
        return list;
    }

    public void addLessons(Lesson lesson) {
        if (lessons == null) lessons = new SinglyLinkedList();
        lessons.add(lesson);
    }



}
