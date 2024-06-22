package domain;

import java.io.Serializable;

public class Grade implements Serializable {
    private int lessonId; // Identificador de la lección
    private double grade; // Calificación obtenida en la lección

    public Grade(int lessonId, double grade) {
        this.lessonId = lessonId;
        this.grade = grade;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
