package interfaces;

import domain.Course;

import java.util.List;

public interface CourseMaintenance {

    /**
     * ### 3. Mantenimiento de Cursos
     - *CRUD de cursos:***/
    /**
     - createCourse(Course course): boolean: Agrega un nuevo curso al árbol AVL y devuelve true si se crea con éxito.
    **/
    public boolean createCourse(Course course);

    /**
     - readCourse(int courseId): Course: Busca y devuelve un curso por su ID.
    **/
    public Course readCourse(int courseId);
    /**-
     * updateCourse(Course course): boolean: Actualiza la información de un curso existente y devuelve true si se actualiza con éxito.
    **/
    public boolean updateCourse(Course course);
    /** - deleteCourse(int courseId): boolean: Elimina un curso por su ID y devuelve true si se elimina con éxito.
     **/
    public boolean deleteCourse(int courseId);
    /**
     - listCourses(): List<Course>: Devuelve una lista de todos los cursos.
     **/
    public List<Course> listCourse();

}
