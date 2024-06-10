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

    @Override
    public boolean createCourse(Course course) {
        // Cargar cursos desde el archivo para asegurar que la verificación sea completa
        loadCoursesFromFile("cursos.txt");

        // Verifica si el curso con el mismo ID ya existe
        try {
            List<Course> courseList = avlTree.inOrderUsage();
            for (Course existingCourse : courseList) {
                if (existingCourse.getId() == course.getId()) {
                    System.out.println("El curso con este ID ya existe");
                    return false; // El curso con este ID ya existe
                }
            }
        } catch (TreeException e) {
            e.printStackTrace();
        }

        // Si el curso no existe, se agrega
        avlTree.add(course);
        saveCoursesToFile("cursos.txt"); // Guarda los cursos en un archivo
        System.out.println("El curso fue agregado");
        return true;
    }

    @Override
    public String readCourse(int courseId) { //Busca y devuelve un curso por su ID.
        loadCoursesFromFile("cursos.txt"); // Cargar cursos desde el archivo
        try {
            for (Course c : avlTree.inOrderUsage()) {
                if (c.getId() == courseId) {
                    return "The course has been found \n" + c.toString(); // el curso existe
                }
            }
        } catch (TreeException e) {
            e.printStackTrace();
        }
        return "The course doesn’t exist"; // no existe el curso
    }

    @Override
    public boolean updateCourse(Course updatedCourse) { //recibe una instancia nueva de un curso para eliminar y reemplazar el existe con ese id
        loadCoursesFromFile("cursos.txt"); // Cargar cursos desde el archivo
        try {
            for (Course course : avlTree.inOrderUsage()) {
                if (course.getId() == updatedCourse.getId()) {
                    avlTree.remove(course);
                    avlTree.add(updatedCourse);
                    saveCoursesToFile("cursos.txt"); // Guarda los cursos en un archivo
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
    public boolean deleteCourse(int courseId) throws TreeException {
        loadCoursesFromFile("cursos.txt"); // Cargar cursos desde el archivo
        try {
            for (Course course : avlTree.inOrderUsage()) {
                if (course.getId() == courseId) {
                    avlTree.remove(course);
                    saveCoursesToFile("cursos.txt"); // Guarda los cursos en un archivo
                    System.out.println("El curso fue eliminado");
                    return true; // el curso existe
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
        loadCoursesFromFile("cursos.txt"); // Cargar cursos desde el archivo
        try {
            if (avlTree.isEmpty()) {
                throw new TreeException("El árbol de cursos está vacío.");
            }
            return avlTree.inOrderUsage(); // Devolver una lista de los cursos en orden
        } catch (TreeException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Devolver una lista vacía en caso de error
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
                if (!line.trim().isEmpty()) { // Verificar que la línea no esté vacía
                    try {
                        Course course = stringToCourse(line);
                        avlTree.add(course);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Error parsing line: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void viewAllCourses(String filename) {
        loadCoursesFromFile(filename); // Cargar los cursos desde el archivo
        try {
            for (Course course : avlTree.inOrderUsage()) {
                System.out.println(course); // Imprimir cada curso
            }
        } catch (TreeException e) {
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

}
