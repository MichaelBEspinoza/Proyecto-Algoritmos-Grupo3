package operations;

import domain.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseOperationsTest {
    CourseOperations operations = new CourseOperations();

    @Test
    void createCourse() {
        System.out.println("\n CreateCourseTest:");

        Course curso1 = new Course(5,"Matematicas","Curso de matematicas","3 meses","Dificil",43);
        System.out.println(operations.createCourse(curso1));
        Course curso2 = new Course(5,"Matematicas","Curso de matematicas","3 meses","Dificil",43);
        System.out.println(operations.createCourse(curso2));

        System.out.println("\n");

    }

    @Test
    void readCourse() {

        System.out.println("\n ReadCourseTest:");
        Course curso1 = new Course(5,"Matematicas","Curso de matematicas","3 meses","Dificil",43);

        System.out.println(operations.createCourse(curso1));
        System.out.println(operations.readCourse(5));
        System.out.println(operations.readCourse(7));

        System.out.println("\n");

    }

    @Test
    void updateCourse() {

        System.out.println("\n UpdateCourseTest:");

        System.out.println("\n");

    }

    @Test
    void deleteCourse() {

        System.out.println("\n DeleteCourseTest:");

        System.out.println("\n");

    }

    @Test
    void listCourse() {

        System.out.println("\n ReadListCourseTest:");

        System.out.println("\n");

    }
}