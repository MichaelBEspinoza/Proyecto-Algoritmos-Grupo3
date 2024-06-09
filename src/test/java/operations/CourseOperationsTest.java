package operations;

import domain.Course;
import org.junit.jupiter.api.Test;
import structures.trees.TreeException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CourseOperationsTest {

    CourseOperations operations = new CourseOperations();

    @Test
    void createCourse(){

        System.out.println("\n\n///// Create test: /////\n\n ");
        Course course1 = new Course(5,"Math","Math Course","3 meses","Hard",43);
        System.out.println(operations.createCourse(course1));

        System.out.println("\n\n///// Create test end. /////");


    }//todo funcional

    @Test
    void readCourse(){

        System.out.println("\n\n///// Read test: /////\n\n ");

        System.out.println(operations.readCourse(5));
        System.out.println(operations.readCourse(50));


        System.out.println("\n\n///// Read test end. /////");

    }//todo funcional

    @Test
    void updateCourse(){

        System.out.println("\n\n///// Update test: /////\n\n ");

        Course course1 = new Course(5,"Science","Science Course","3 meses","Hard",43);


        System.out.println(operations.updateCourse(course1));
        System.out.println(operations.readCourse(5));

        System.out.println("\n\n///// Update test end. /////");

    } //todo funcional

    @Test
    void deleteCourse(){

        System.out.println("\n\n///// Delete test: /////\n\n ");

        Course course1 = new Course(10,"Math","Math Course","5 months","Hard",43);
        operations.createCourse(course1);

        try {
            System.out.println(operations.deleteCourse(10));
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        System.out.println(operations.readCourse(10));

        System.out.println("\n\n///// Delete test end. /////");

    } // todo funcional

    @Test
    void viewCourses(){

        System.out.println("\n\n///// View test: /////\n\n ");

        Course course1 = new Course(15,"Math","Math Course","5 months","Hard",43);
        operations.createCourse(course1);



        operations.viewAllCourses("cursos.txt");

        System.out.println("\n\n///// View test end. /////");

    }


}
