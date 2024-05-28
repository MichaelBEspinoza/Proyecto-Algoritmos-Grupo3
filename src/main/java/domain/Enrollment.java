package domain;

import java.time.LocalDate;

public class Enrollment {
    private int id; /**Identificador del curso**/

    private int userId; /**Identificador del usuario escrito**/

    private int courseId; /**identificador del curso al que escribe**/

    private LocalDate registrationDate; /**Fecha de inscipcion**/

    public Enrollment() {
    }

    public Enrollment(int id, int userId, int courseId, LocalDate registrationDate) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
