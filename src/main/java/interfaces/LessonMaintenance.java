package interfaces;

import domain.Lesson;

import java.util.List;

public interface LessonMaintenance {
    /**### 4. Mantenimiento de Lecciones
     - *CRUD de lecciones:*
     - createLesson(Lesson lesson): boolean: Agrega una nueva lección a un curso y devuelve true si se crea con éxito.
    **/
    public boolean crateLesson(Lesson lesson);
    /**- readLesson(int lessonId): Lesson: Busca y devuelve una lección por su ID.
     **/
    public Lesson readLesson(int lessonId);
    /**- updateLesson(Lesson lesson): boolean: Actualiza el contenido de una lección existente y devuelve true si se actualiza con éxito.
    **/
    public boolean updateLesson(Lesson lesson);
    /** - deleteLesson(int lessonId): boolean: Elimina una lección por su ID y devuelve true si se elimina con éxito.
    **/
    public boolean deleteLesson(int lessonId);
    /**- listLessons(int courseId): List<Lesson>: Devuelve una lista de todas las lecciones de un curso.
     **/
    public List<Lesson> listLessons(int courseId);

}
