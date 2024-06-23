package operations;

import domain.Enrollment;
import domain.User;
import domain.UserSession;
import interfaces.InscriptionMaintenance;
import structures.trees.BTree;
import structures.trees.BTreeNode;
import structures.trees.TreeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InscriptionOperations implements InscriptionMaintenance {
    BTree enrollments = new BTree();
    UserOperations userOperations = new UserOperations(); // Assuming this exists to manage user-specific operations

    @Override
    public boolean enrollStudent(int userId, int courseId) {
        Enrollment newEnrollment = new Enrollment(userId, courseId);
        try {
            if (!enrollments.contains(newEnrollment)) {
                enrollments.add(newEnrollment);
                newEnrollment.incrementEnrollment();
                return true;
            } else {
                Enrollment existing = (Enrollment) enrollments.find(newEnrollment);
                if (existing != null) {
                    existing.incrementEnrollment();
                    return true;
                }
            }
        } catch (TreeException e) {
            System.err.println("Error processing enrollment: " + e.getMessage());
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
            Enrollment enrollment = new Enrollment(userId, courseId);
            if (enrollments.contains(enrollment)) {
                Enrollment existing = (Enrollment) enrollments.find(enrollment);
                if (existing != null && existing.getEnrolledCount() > 0) {
                    existing.setEnrolledCount(existing.getEnrolledCount() - 1);
                    if (existing.getEnrolledCount() == 0) {
                        enrollments.remove(existing);
                    }
                    return true;
                }
            }
        } catch (TreeException e) {
            System.err.println("Error canceling enrollment: " + e.getMessage());
        }
        return false;
    }
}