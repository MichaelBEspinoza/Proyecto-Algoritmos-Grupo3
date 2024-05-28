package interfaces;

import domain.Course;
import domain.Lesson;
import domain.Progress;

import java.util.List;

public interface MyOnlineLearning {

    /**
     ### 6. Mi Aprendizaje en Línea
     - *Operaciones para usuarios inscritos:*
     - accessCourseContent(int userId, int courseId): List<Lesson>: Permite a los usuarios ver el contenido del curso y devuelve una lista de lecciones.
    **/
    public List<Lesson> accessCourseContent(int userId, int courseId);
    /** - trackProgress(int userId, int courseId): Progress: Muestra el progreso del usuario en el curso y devuelve un objeto de progreso.
    **/
    public Progress trackProgress(int userId, int courseId);
    /** - listUserCourses(int userId): List<Course>: Devuelve una lista de cursos en los que el usuario está inscrito.
     **/
    public List<Course> listUserCourses(int userId);
}
