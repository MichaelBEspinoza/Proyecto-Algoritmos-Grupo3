package domain;

public class Progress {
    private int userId; /**Identificador del usuario**/

    private int courseId; /**Identificador del curso**/

    private int completedLessons; /**Numero de lecciones completadas**/

    private double grade; /**Calificacion obtenida en cada curso**/

    public Progress() {
    }

    public Progress(int userId, int courseId, int completedLessons, double grade) {
        this.userId = userId;
        this.courseId = courseId;
        this.completedLessons = completedLessons;
        this.grade = grade;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = completedLessons;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
