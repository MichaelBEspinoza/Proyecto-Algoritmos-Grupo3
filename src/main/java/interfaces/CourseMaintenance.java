package interfaces;

import domain.Course;
import structures.trees.TreeException;

import java.util.List;

public interface CourseMaintenance {

    /**
     * ### 3. Mantenimiento de Cursos
     - *CRUD de cursos:***/

    /**
     * createCourse(Course course): boolean: Agrega un nuevo curso al árbol AVL y devuelve true si se crea con éxito.
     * @param course El curso a ser creado.
     * @return true si el curso fue creado exitosamente, false de lo contrario.
     */
    public boolean createCourse(Course course);

    /**
     * readCourse(int courseId): String: Busca y devuelve un curso por su ID.
     * @param courseId El ID del curso a ser leído.
     * @return Una cadena con la información del curso si se encuentra, de lo contrario un mensaje indicando que el curso no existe.
     */
    public String readCourse(int courseId);

    /**
     * updateCourse(Course course): boolean: Actualiza la información de un curso existente y devuelve true si se actualiza con éxito.
     * @param course El curso con la información actualizada.
     * @return true si el curso fue actualizado exitosamente, false de lo contrario.
     */
    public boolean updateCourse(Course course);

    /**
     * deleteCourse(int courseId): boolean: Elimina un curso por su ID y devuelve true si se elimina con éxito.
     * @param courseId El ID del curso a ser eliminado.
     * @return true si el curso fue eliminado exitosamente, false de lo contrario.
     * @throws TreeException Si ocurre un error al eliminar el curso del árbol AVL.
     */
    public boolean deleteCourse(int courseId) throws TreeException;

    /**
     * listCourses(): List<Course>: Devuelve una lista de todos los cursos.
     * @return Una lista de todos los cursos.
     */
    public List<Course> listCourse();

}
