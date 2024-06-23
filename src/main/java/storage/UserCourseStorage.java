package storage;

import domain.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCourseStorage {
    private static final String FILENAME = "user_courses.txt";

    // Cargar todos los usuarios y sus cursos desde el archivo
    public static Map<String, List<Course>> loadAllUserCourses() {
        Map<String, List<Course>> userCourses = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            userCourses = (Map<String, List<Course>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Si el archivo no existe, se crea un nuevo mapa vacío
            userCourses = new HashMap<>();
        }
        return userCourses;
    }

    // Guardar todos los usuarios y sus cursos en el archivo
    public static void saveAllUserCourses(Map<String, List<Course>> userCourses) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(userCourses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Añadir un curso a un usuario y guardar en el archivo
    public static void addCourseToUser(String username, Course course) {
        Map<String, List<Course>> userCourses = loadAllUserCourses();
        userCourses.computeIfAbsent(username, k -> new ArrayList<>()).add(course);
        saveAllUserCourses(userCourses);
    }

    // Obtener los cursos de un usuario desde el archivo
    public static List<Course> getCoursesForUser(String username) {
        Map<String, List<Course>> userCourses = loadAllUserCourses();
        return userCourses.getOrDefault(username, new ArrayList<>());
    }
}
