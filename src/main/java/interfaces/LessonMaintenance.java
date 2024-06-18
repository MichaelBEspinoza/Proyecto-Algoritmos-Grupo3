package interfaces;

import domain.Lesson;
import structures.trees.TreeException;

import java.util.List;

public interface LessonMaintenance {
    /**### 4. Mantenimiento de Lecciones
     - *CRUD de lecciones:*
     - createLesson(Lesson lesson): boolean: Agrega una nueva lección a un curso y devuelve true si se crea con éxito.
    **/
    public boolean createLesson(Lesson lesson) throws TreeException;
    /**- readLesson(int lessonId): Lesson: Busca y devuelve una lección por su ID.
     **/
    public Lesson readLesson(int lessonId) throws TreeException;
    /**- updateLesson(Lesson lesson): boolean: Actualiza el contenido de una lección existente y devuelve true si se actualiza con éxito.
    **/
    public boolean updateLesson(Lesson lesson) throws TreeException;
    /** - deleteLesson(int lessonId): boolean: Elimina una lección por su ID y devuelve true si se elimina con éxito.
    **/
    public boolean deleteLesson(int lessonId) throws TreeException;
    /**- listLessons(int courseId): List<Lesson>: Devuelve una lista de todas las lecciones de un curso.
     **/
    public List<Lesson> listLessons() throws TreeException;

}
