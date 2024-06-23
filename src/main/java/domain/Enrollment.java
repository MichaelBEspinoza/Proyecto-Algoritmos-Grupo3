package domain;

import java.time.LocalDate;
import java.util.Objects;

public class Enrollment {
    private int userId;
    private int courseId;
    private int enrolledCount;

    public Enrollment() {
    }

    public Enrollment(int userId, int courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void incrementEnrollment() {
        this.enrolledCount++;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(int enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enrollment that = (Enrollment) obj;
        return userId == that.userId && courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "userId=" + userId +
                ", courseId=" + courseId +
                '}';
    }
}