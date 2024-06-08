package operations;

import domain.Course;
import org.junit.jupiter.api.Test;
import structures.trees.TreeException;

import static org.junit.jupiter.api.Assertions.*;

class CourseOperationsTest {
    CourseOperations operations = new CourseOperations();

    @Test
    void createCourse() {
        System.out.println("\n CreateCourseTest:");

        Course curso1 = new Course(5,"Matematicas","Curso de matematicas","3 meses","Dificil",43);
        System.out.println(operations.createCourse(curso1));
        Course curso2 = new Course(10,"Ingles","Curso de Ingles","3 meses","Dificil",43);
        System.out.println(operations.createCourse(curso2));
        Course curso3 = new Course(5,"Ciencias","Curso de Ciencias","3 meses","Dificil",43);
        System.out.println(operations.createCourse(curso3));


        System.out.println("\n");

    }// todo funcional

    @Test
    void readCourse() {

        System.out.println("\n ReadCourseTest:");
        Course curso1 = new Course(5,"Matematicas","Curso de matematicas","3 meses","Dificil",43);

        System.out.println(operations.createCourse(curso1));
        System.out.println(operations.readCourse(5));
        System.out.println(operations.readCourse(7));

        System.out.println("\n");

    }//todo funcional

    @Test
    void updateCourse() {

        System.out.println("\n UpdateCourseTest:");
        Course curso1 = new Course(5,"Matematicas","Curso de matematicas","3 meses","Dificil",43);
        Course curso2 = new Course(10,"Español","Curso de Español","2 meses","Facil",23);
        Course cursoActualizado = new Course(5,"Matematicas","Curso de matematicas","10 meses","Dificil",43);


        System.out.println(operations.createCourse(curso1));
        System.out.println(operations.createCourse(curso2));

        System.out.println(operations.readCourse(5));
        System.out.println(operations.readCourse(10));




        operations.updateCourse(cursoActualizado);
        System.out.println(operations.readCourse(5));


        System.out.println("\n");

    }// todo funcional

    @Test
    void deleteCourse() {

        System.out.println("\n\n DeleteCourseTest:");

        Course curso1 = new Course(5,"Matematicas","Curso de matematicas","3 meses","Dificil",43);
        System.out.println(operations.createCourse(curso1));
        Course curso2 = new Course(10,"Español","Curso de Español","2 meses","Facil",23);
        System.out.println(operations.createCourse(curso2));
        Course curso3 = new Course(15,"Estudios","Curso de Estudios","10 meses","Media",28);
        System.out.println(operations.createCourse(curso3));

        System.out.println(operations.listCourse());

        try {
            System.out.println(operations.deleteCourse(5));
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }

        System.out.println(operations.listCourse());



        System.out.println("\n");

    }// todo funcional

    @Test
    void listCourse() {

        System.out.println("\n\n ReadListCourseTest:");


        // Crear y agregar cursos
        Course course1 = new Course(1, "Matematicas", "Curso de matematicas", "3 meses", "Dificil", 101);
        Course course2 = new Course(2, "Español", "Curso de Español", "2 meses", "Facil", 102);

        operations.createCourse(course1);
        operations.createCourse(course2);

        // Guardar cursos en el archivo
        operations.saveCoursesToFile("cursos.dat");

        // Cargar y visualizar cursos desde el archivo
        operations.viewAllCourses("cursos.dat");

        System.out.println("\nbefore edit");

        Course curso3 = new Course(2,"Estudios","Curso de Estudios","10 meses","Media",28);


        System.out.println(operations.updateCourse(curso3));

        // Guardar cursos en el archivo
        operations.saveCoursesToFile("cursos.dat");

        // Cargar y visualizar cursos desde el archivo
        operations.viewAllCourses("cursos.dat");

        System.out.println("\n");

    }// todo funcional


}