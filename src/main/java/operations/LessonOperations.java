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

    public int size() throws TreeException {
        return lessons.size();
    }

    @Override
    public boolean createLesson(Lesson lesson) throws TreeException {
        loadLessonsFromFile("lessons.txt");

        if (lessons.isEmpty()) {
            lessons.add(lesson);
            saveLessonsToFile("lessons.txt");
            return true;
        }

        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
            saveLessonsToFile("lessons.txt");
            return true;
        }
        return false;
    }

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
    }

    @Override
    public boolean updateLesson(Lesson lesson) throws TreeException {
        loadLessonsFromFile("lessons.txt");

        if (lessons.isEmpty()) return false;

        if (lesson != null)
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
    }

    @Override
    public boolean deleteLesson(int lessonId) throws TreeException {
        loadLessonsFromFile("lessons.txt");
        Lesson lessonToRemove = null;

        for (Lesson check : listLessons()) {
            if (check.getId() == lessonId) {
                lessonToRemove = check;
                break;
            }
        }

        if (lessonToRemove != null) {
            lessons.remove(lessonToRemove);
            saveLessonsToFile("lessons.txt");
            return true;
        }

        return false;
    }

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
    }

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
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty())
                    try {
                        Lesson lesson = stringToLesson(line);
                        lessons.add(lesson);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String lessonToString(Lesson lesson) {
        return lesson.getId() + "<>" + lesson.getTitle() + "<>" + lesson.getContent() + "<>" + lesson.getCourse() + "<>" + lesson.getCourseId();
    }

    private Lesson stringToLesson(String str) {
        try {
            String[] parts = str.split("<>");
            if (parts.length != 5)
                throw new IllegalArgumentException("Invalid data format: " + str);

            int id = Integer.parseInt(parts[0].trim());
            String title = parts[1].trim();
            String content = parts[2].trim();
            String course = parts[3].trim();
            int courseId = Integer.parseInt(parts[4].trim());
            return new Lesson(id, title, content, course, courseId, false);
        } catch (NumberFormatException e) {
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

    public List<Lesson> listLessonsByCourse(int courseId) throws TreeException {
        loadLessonsFromFile("lessons.txt");

        List<Lesson> list = new ArrayList<>();
        for (Lesson obj : lessons.inOrderUsage()) {
            if (obj.getCourseId() == courseId) {
                list.add(obj);
            }
        }
        return list;
    }

    public boolean isEmpty() {
        return lessons.isEmpty();
    }

    public boolean isLessonIdUnique(int lessonId) throws TreeException {
        if (!lessons.isEmpty())
            for (Lesson lesson : lessons.inOrderUsage())
                if (lesson.getId() == lessonId)
                    return false; // ID no es único, ya existe
        return true; // ID es único
    }

    public boolean markLessonCompleted(int lessonId) throws TreeException {
        loadLessonsFromFile("lessons.txt");

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = (Lesson) lessons.get(i);
            if (lesson.getId() == lessonId) {
                lesson.setStateCompleted(true);
                saveLessonsToFile("lessons.txt");
                return true;
            }
        }
        return false;
    }

    public int countCompletedLessonsByCourse(int courseId) throws TreeException {
        loadLessonsFromFile("lessons.txt");

        int completedCount = 0;
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = (Lesson) lessons.get(i);
            if (lesson.getCourseId() == courseId && lesson.isStateCompleted()) {
                completedCount++;
            }
        }
        return completedCount;
    }

    public int countLessonsByCourse(int courseId) throws TreeException {
        loadLessonsFromFile("lessons.txt");

        int totalCount = 0;
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = (Lesson) lessons.get(i);
            if (lesson.getCourseId() == courseId) {
                totalCount++;
            }
        }
        return totalCount;
    }
}
