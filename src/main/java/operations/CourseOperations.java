package operations;

import domain.Course;
import interfaces.CourseMaintenance;
import structures.trees.AVL;
import structures.trees.TreeException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseOperations implements CourseMaintenance {

    AVL avlTree = new AVL();
    ArrayList<Course> cursos = new ArrayList<>();

    @Override
    public boolean createCourse(Course course) {
        // Cargar cursos desde el archivo para asegurar que la verificación sea completa
        loadCoursesFromFile("cursos.txt");

        // Verifica si el curso con el mismo ID ya existe
        for (Course c : cursos) {
            if (c.getId() == course.getId()) {
                System.out.println("El curso con este ID ya existe");
                return false; // El curso con este ID ya existe
            }
        }

        // Si el curso no existe, se agrega
        avlTree.add(course);
        cursos.add(course);
        saveCoursesToFile("cursos.txt"); // Guarda los cursos en un archivo
        System.out.println("El curso fue agregado");
        return true;
    } //todo funcional

    @Override
    public String readCourse(int courseId) { //Busca y devuelve un curso por su ID.
        loadCoursesFromFile("cursos.txt"); // Cargar cursos desde el archivo
        for (Course c : cursos) {
            if (c.getId() == courseId) {
                return "The course has been found \n" + c.toString(); // el curso existe
            }
        }
        return "The course doesn’t exist"; // no existe el curso
    }//todo funcional

    @Override
    public boolean updateCourse(Course updatedCourse) { //recibe una instancia nueva de un curso para eliminar y reemplazar el existe con ese id
        loadCoursesFromFile("cursos.txt"); // Cargar cursos desde el archivo
        try {
            for (int i = 0; i < cursos.size(); i++) {
                Course course = cursos.get(i);
                if (course.getId() == updatedCourse.getId()) {
                    cursos.set(i, updatedCourse);
                    avlTree.remove(course);
                    avlTree.add(updatedCourse);
                    saveCoursesToFile("cursos.txt"); // Guarda los cursos en un archivo
                    System.out.println("El curso fue actualizado");
                    return true;
                }
            }
            System.out.println("El curso no se encontró");
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return false;
    }//todo funcional

    @Override
    public boolean deleteCourse(int courseId) throws TreeException {
        loadCoursesFromFile("cursos.txt"); // Cargar cursos desde el archivo
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getId() == courseId) {
                Course courseToRemove = cursos.get(i);
                avlTree.remove(courseToRemove);
                cursos.remove(i);
                saveCoursesToFile("cursos.txt"); // Guarda los cursos en un archivo
                System.out.println("El curso fue eliminado");
                return true; // el curso existe
            }
        }
        System.out.println("El curso no se encontró");
        return false;
    }

    @Override
    public List<Course> listCourse() {
        loadCoursesFromFile("cursos.txt"); // Cargar cursos desde el archivo
        return new ArrayList<>(cursos); // Devolver una copia de la lista de cursos
    }

    public void saveCoursesToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Course course : cursos) {
                writer.write(courseToString(course));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCoursesFromFile(String filename) {
        cursos.clear();
        avlTree.clear();
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile(); // Crea el archivo si no existe
            } catch (IOException e) {
                e.printStackTrace();
            }
            return; // Si el archivo no existía, no hay nada más que hacer
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Course course = stringToCourse(line);
                cursos.add(course);
                avlTree.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewAllCourses(String filename) {
        loadCoursesFromFile(filename); // Cargar los cursos desde el archivo
        for (Course course : cursos) {
            System.out.println(course); // Imprimir cada curso
        }
    }

    private String courseToString(Course course) {
        return course.getId() + "," + course.getName() + "," + course.getDescription() + ","
                + course.getCourseLength() + "," + course.getLevel() + "," + course.getInstructorId();
    }

    private Course stringToCourse(String str) {
        String[] parts = str.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String description = parts[2];
        String courseLength = parts[3];
        String level = parts[4];
        int instructorId = Integer.parseInt(parts[5]);
        return new Course(id, name, description, courseLength, level, instructorId);
    }
}
