package operations;

import domain.Enrollment;
import interfaces.InscriptionMaintenance;
import structures.trees.BTree;
import structures.trees.BTreeNode;
import structures.trees.TreeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InscriptionOperations implements InscriptionMaintenance {

    BTree btree = new BTree();
    UserOperations UO = new UserOperations();
    CourseOperations CO = new CourseOperations();

    @Override
    public boolean enrollStudent(int userId, int courseId) throws TreeException {
        if (UO.readUser(userId) != null && !Objects.equals(CO.readCourse(courseId), "The course doesn’t exist")) {
            Enrollment enrollment = new Enrollment(userId, courseId);
            if (!btree.contains(enrollment)) {
                btree.add(enrollment);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Enrollment> listEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        if (!btree.isEmpty()) {
            collectEnrollments(btree.getRoot(), enrollments);
        }
        return enrollments;
    }

    private void collectEnrollments(BTreeNode node, List<Enrollment> enrollments) {
        if (node != null) {
            collectEnrollments(node.left, enrollments);
            if (node.data instanceof Enrollment) {
                enrollments.add((Enrollment) node.data);
            }
            collectEnrollments(node.right, enrollments);
        }
    }

    @Override
    public boolean cancelEnrollment(int userId, int courseId) throws TreeException {
        Enrollment enrollment = new Enrollment(userId, courseId);
        if (btree.contains(enrollment)) {
            btree.remove(enrollment);
            return true;
        }
        return false;
    }
}

