package operations;

import domain.Lesson;
import interfaces.LessonMaintenance;
import structures.trees.BST;
import structures.trees.TreeException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LessonOperations implements LessonMaintenance {

    BST lessons = new BST();

    @Override
    public boolean createLesson(Lesson lesson) throws TreeException {
        loadLessonsFromFile("lessons.txt");

        for (int i = 0; i < lessons.size(); i++)
            if (!lessons.contains(lesson)) {
                lessons.add(lesson);
                saveLessonsToFile("lessons.txt");
                return true;
            }
        return false;
    }// End of method [createLesson].

    @Override
    public Lesson readLesson(int lessonId) throws TreeException {
        loadLessonsFromFile("lessons.txt");

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
        loadLessonsFromFile("lessons.txt");

        if (lessons.isEmpty()) return false;

        for (int i = 0; i < lessons.size(); i++) {
            Lesson check = (Lesson) lessons.get(i);
            if (lesson.getId() == check.getId()) {
                check.setTitle(lesson.getTitle());
                check.setContent(lesson.getContent());
                check.setCourseId(lesson.getCourseId());
                saveLessonsToFile("lessons.txt");
                return true;
            }
        }
        return false;
    }// End of method [updateLesson].

    @Override
    public boolean deleteLesson(int lessonId) throws TreeException {
        loadLessonsFromFile("lessons.txt");

        if (lessons.isEmpty()) return false;

        for (int i = 0; i < lessons.size(); i++) {
            Lesson check = (Lesson) lessons.get(i);
            if (check.getId() == lessonId) {
                lessons.remove(check);
                saveLessonsToFile("lessons.txt");
                return true;
            }
        }
        return false;
    }// End of method [deleteLesson].

    @Override
    public List<Lesson> listLessons() throws TreeException {

        loadLessonsFromFile("lessons.txt");

        List<Lesson> list = new ArrayList<>();
        if (!lessons.isEmpty())
            for (int i = 0; i < lessons.size(); i++) {
                 Lesson check = (Lesson) lessons.get(i);
                 list.add(check);
            }

        return list;
    }// End of method [listLessons].

    public void saveLessonsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < lessons.size(); i++) {
                Lesson addThis = (Lesson) lessons.get(i);
                writer.write(lessonToString(addThis));
                writer.newLine();
            }
        } catch (IOException | TreeException e) {
            e.printStackTrace();
        }
    }

    public void loadLessonsFromFile(String filename) {
        lessons.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null)
                if (!line.trim().isEmpty())
                    try {
                        Lesson lesson = stringToLesson(line);
                        lessons.add(lesson);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Error parsing line: " + line);
                        e.printStackTrace();
                    }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String lessonToString(Lesson lesson) {
        return lesson.getId() + ", " + lesson.getTitle() + ", " + lesson.getContent() + ", from course #" + lesson.getCourseId();
    }

    private Lesson stringToLesson(String str) {
        try {
            String[] parts = str.split(",");
            int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String content = parts[2];
            int courseId = Integer.parseInt(parts[3]);
            return new Lesson(id, title, content, courseId);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid course data: " + str, e);
        }
    }

    public boolean checkIfExistsById(int searchThis) throws TreeException {
        loadLessonsFromFile("lessons.txt");
        for (int i = 0; i < lessons.size(); i++) {
            Lesson check = (Lesson) lessons.get(i);
            if (check.getCourseId() == searchThis) return true;
        }
        return false;
    }

}// End of class [LessonOperations].
