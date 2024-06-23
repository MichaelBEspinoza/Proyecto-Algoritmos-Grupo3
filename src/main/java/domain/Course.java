package domain;

import structures.lists.ListException;
import structures.lists.Node;
import structures.lists.SinglyLinkedList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id; /**Identificador del curso**/
    private String name; /**Nombre del curso**/
    private String description; /**Descripcion del curso**/
    private String courseLength; /**Duracion del curso**/
    private String level; /**Nivel de dificultad (Low, Medium, High)**/
    private int instructorId; /**Identificador del instructor que imparte el curso**/
    private List<Lesson> lessons;

    public Course() {
        lessons = new ArrayList<>();
    }

    public Course(int id) {
        this.id = id;
        lessons = new ArrayList<>();
    }

    public Course(int id, String name, String description, String courseLength, String level, int instructorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.courseLength = courseLength;
        this.level = level;
        this.instructorId = instructorId;
        this.lessons = new ArrayList<>();
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

    public void addLesson(Lesson lesson) {
        if (lesson != null && lesson.getCourseId() == this.id) {
            lessons.add(lesson);
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public StringBuilder lessonsToString() {
        StringBuilder list = new StringBuilder();
        for (Lesson lesson : lessons) {
            list.append(lesson.getId()).append(" - ").append(lesson.getTitle()).append("\n").append(lesson.getContent());
        }
        return list;
    }
}
