package operations;

import domain.Lesson;
import interfaces.LessonMaintenance;
import structures.trees.BST;
import structures.trees.TreeException;

import java.util.ArrayList;
import java.util.List;

public class LessonOperations implements LessonMaintenance {

    BST lessons = new BST();

    @Override
    public boolean createLesson(Lesson lesson) throws TreeException {
        for (int i = 0; i < lessons.size(); i++)
            if (!lessons.contains(lesson)) {
                lessons.add(lesson);
                return true;
            }
        return false;
    }// End of method [createLesson].

    @Override
    public Lesson readLesson(int lessonId) throws TreeException {

        if (lessons.isEmpty()) return null;

        for (int i = 0; i < lessons.size(); i++) {
            Lesson check = (Lesson) lessons.get(i);
            if (lessonId == check.getId())
                return check;
        }
        return null;
    }// End of method [readLesson].

    @Override
    public boolean updateLesson(Lesson lesson) throws TreeException {
        if (lessons.isEmpty()) return false;

        for (int i = 0; i < lessons.size(); i++) {
            Lesson check = (Lesson) lessons.get(i);
            if (lesson.getId() == check.getId()) {
                check.setTitle(lesson.getTitle());
                check.setContent(lesson.getContent());
                check.setCourseId(lesson.getCourseId());
                return true;
            }
        }
        return false;
    }// End of method [updateLesson].

    @Override
    public boolean deleteLesson(int lessonId) throws TreeException {
        if (lessons.isEmpty()) return false;

        for (int i = 0; i < lessons.size(); i++) {
            Lesson check = (Lesson) lessons.get(i);
            if (check.getId() == lessonId) {
                lessons.remove(check);
                return true;
            }
        }
        return false;
    }// End of method [deleteLesson].

    @Override
    public List<Lesson> listLessons() throws TreeException {
        List<Lesson> list = new ArrayList<>();
        list.clear();

        for (int i = 0; i < lessons.size(); i++) {
            Lesson check = (Lesson) lessons.get(i);
            list.add(check);
        }

        return list;
    }// End of method [listLessons].
}// End of class [LessonOperations].
