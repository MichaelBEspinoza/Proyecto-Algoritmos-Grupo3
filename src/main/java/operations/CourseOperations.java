package operations;

import domain.Course;
import domain.Lesson;
import interfaces.CourseMaintenance;
import structures.trees.AVL;
import structures.trees.TreeException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseOperations implements CourseMaintenance {

    AVL avlTree = new AVL();
    LessonOperations lessonOperations = new LessonOperations();

    @Override
    public boolean createCourse(Course course) {
        loadCoursesFromFile("cursos.txt");

        try {
            List<Course> courseList = avlTree.inOrderUsage();
            for (Course existingCourse : courseList) {
                if (existingCourse.getId() == course.getId()) {
                    System.out.println("El curso con este ID ya existe");
                    return false;
                }
            }
        } catch (TreeException e) {
            e.printStackTrace();
        }

        avlTree.add(course);
        saveCoursesToFile("cursos.txt");
        System.out.println("El curso fue agregado");
        return true;
    }

    @Override
    public String readCourse(int courseId) {
        loadCoursesFromFile("cursos.txt");
        try {
            for (Course c : avlTree.inOrderUsage()) {
                if (c.getId() == courseId) {
                    return "The course has been found \n" + c;
                }
            }
        } catch (TreeException e) {
            e.printStackTrace();
        }
        return "The course doesn’t exist";
    }

    @Override
    public boolean updateCourse(Course updatedCourse) {
        loadCoursesFromFile("cursos.txt");
        try {
            for (Course course : avlTree.inOrderUsage()) {
                if (course.getId() == updatedCourse.getId()) {
                    avlTree.remove(course);
                    avlTree.add(updatedCourse);
                    saveCoursesToFile("cursos.txt");
                    System.out.println("El curso fue actualizado");
                    return true;
                }
            }
            System.out.println("El curso no se encontró");
        } catch (TreeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCourse(int courseId) {
        loadCoursesFromFile("cursos.txt");
        try {
            for (Course course : avlTree.inOrderUsage()) {
                if (course.getId() == courseId) {
                    avlTree.remove(course);
                    saveCoursesToFile("cursos.txt");
                    System.out.println("El curso fue eliminado");
                    return true;
                }
            }
        } catch (TreeException e) {
            e.printStackTrace();
        }
        System.out.println("El curso no se encontró");
        return false;
    }

    @Override
    public List<Course> listCourse() {
        loadCoursesFromFile("cursos.txt");
        try {
            if (avlTree.isEmpty()) {
                System.out.println("El árbol de cursos está vacío.");
                return new ArrayList<>();
            }
            return avlTree.inOrderUsage();
        } catch (TreeException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveCoursesToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Course course : avlTree.inOrderUsage()) {
                writer.write(courseToString(course));
                writer.newLine();
            }
        } catch (IOException | TreeException e) {
            e.printStackTrace();
        }
    }

    public void loadCoursesFromFile(String filename) {
        avlTree.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        Course course = stringToCourse(line);
                        List<Lesson> lessons = lessonOperations.listLessonsByCourse(course.getId());
                        course.setLessons(lessons);
                        avlTree.add(course);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Error parsing line: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException | TreeException e) {
            e.printStackTrace();
        }
    }

    private String courseToString(Course course) {
        return course.getId() + "," + course.getName() + "," + course.getDescription() + ","
                + course.getCourseLength() + "," + course.getLevel() + "," + course.getInstructorId();
    }

    private Course stringToCourse(String str) {
        try {
            String[] parts = str.split(",");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String description = parts[2];
            String courseLength = parts[3];
            String level = parts[4];
            int instructorId = Integer.parseInt(parts[5]);
            return new Course(id, name, description, courseLength, level, instructorId);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid course data: " + str, e);
        }
    }

    public String courseByName(String name) {
        loadCoursesFromFile("cursos.txt");
        try {
            for (Course c : avlTree.inOrderUsage()) {
                if (c.getName().equalsIgnoreCase(name)) {
                    return "The course has been found \n" + c;
                }
            }
        } catch (TreeException e) {
            e.printStackTrace();
        }
        return "The course doesn’t exist";
    }
}
