package operations;

import domain.Course;
import domain.Enrollment;
import interfaces.InscriptionMaintenance;
import structures.trees.BTree;
import structures.trees.TreeException;

import java.util.ArrayList;
import java.util.List;

public class InscriptionOperations implements InscriptionMaintenance {
    CourseOperations courseOperations = new CourseOperations();  // Suponiendo que maneja operaciones relacionadas a cursos
    BTree enrollments = new BTree();

    @Override
    public boolean enrollStudent(int userId, int courseId) {
        try {
            Course course = courseOperations.getCourseById(courseId);
            if (course != null) {
                course.incrementEnrollment();
                courseOperations.updateCourse(course);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error enrolling student: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Enrollment> listEnrollments() {
        try {
            return enrollments.inOrderUsage();
        } catch (TreeException e) {
            System.err.println("Error listing enrollments: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean cancelEnrollment(int userId, int courseId) {
        try {
            Course course = courseOperations.getCourseById(courseId);
            if (course != null && course.getEnrollmentCount() > 0) {
                course.decrementEnrollment();
                courseOperations.updateCourse(course);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error canceling enrollment: " + e.getMessage());
        }
        return false;
    }
}