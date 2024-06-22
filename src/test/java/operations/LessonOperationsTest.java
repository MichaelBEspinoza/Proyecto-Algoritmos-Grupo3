package operations;

import static org.junit.jupiter.api.Assertions.*;

import domain.Lesson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.trees.TreeException;

import static org.junit.jupiter.api.Assertions.*;

public class LessonOperationsTest {
    private LessonOperations lessonOperations;
//
//    @BeforeEach
//    public void setUp() throws TreeException {
//        lessonOperations = new LessonOperations();
//        lessonOperations.createLesson(new Lesson(1, "Lesson 1", "Content 1", "Course 1", 1));
//        lessonOperations.createLesson(new Lesson(2, "Lesson 2", "Content 2", "Course 2", 2));
//        lessonOperations.createLesson(new Lesson(3, "Lesson 3", "Content 3", "Course 3", 3));
//    }
//
//    @Test
//    public void testDeleteLessonSuccess() throws TreeException {
//        boolean result = lessonOperations.deleteLesson(2);
//        assertTrue(result, "La lección debería eliminarse correctamente.");
//
//        Lesson lesson = lessonOperations.readLesson(2);
//        assertNull(lesson, "La lección no debería existir después de la eliminación.");
//    }
//
//    @Test
//    public void testDeleteLessonNonExistent() throws TreeException {
//        boolean result = lessonOperations.deleteLesson(4);
//        assertFalse(result, "La eliminación debería fallar para una lección inexistente.");
//    }
//
//    @Test
//    public void testDeleteLessonAndCheckSize() throws TreeException {
//        lessonOperations.deleteLesson(2);
//        int size = lessonOperations.listLessons().size();
//        assertEquals(2, size, "El tamaño del BST debería ser 2 después de eliminar una lección.");
//    }

    @Test
    public void testDeleteLesson() throws TreeException {
        LessonOperations operations = new LessonOperations();

        // Crea algunas lecciones y agrégalas al archivo
        Lesson lesson1 = new Lesson(1, "Lesson 1", "Content 1", "Course 1", 101);
        Lesson lesson2 = new Lesson(2, "Lesson 2", "Content 2", "Course 2", 102);

        operations.createLesson(lesson1);
        operations.createLesson(lesson2);

        // Verifica que las lecciones se hayan agregado
        assertTrue(operations.checkIfExistsById(1));
        assertTrue(operations.checkIfExistsById(2));

        // Elimina una lección y verifica que se haya eliminado
        operations.deleteLesson(1);
        assertFalse(operations.checkIfExistsById(1));
        assertTrue(operations.checkIfExistsById(2));

        // Verifica que el archivo se haya actualizado
        operations.loadLessonsFromFile("lessons.txt");
        assertFalse(operations.checkIfExistsById(1));
        assertTrue(operations.checkIfExistsById(2));
    }
}
