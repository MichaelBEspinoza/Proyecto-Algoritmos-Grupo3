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
        saveCoursesToFile("cursos.dat"); // Guarda los cursos en un archivo
        System.out.println("El curso fue agregado");
        return true;

    }//todo funcional

    @Override
    public String readCourse(int courseId) { //Busca y devuelve un curso por su ID.
        for (int i = 0; i < cursos.size(); i++) {
           if( util.Utility.compare(cursos.get(i).getId(),courseId)==0){
                return "The course has been found \n" +  cursos.get(i).toString(); // el curso existe
           }
        }
        return "The course doesn`t exists";// no existe el curso
    }//todo funcional

    @Override
    public boolean updateCourse(Course updatedCourse) { //recibe una instancia nueva de un curso para eliminar y reemplazar el existe con ese id
        try {
            for (int i = 0; i < cursos.size(); i++) {
                Course course = cursos.get(i);
                if (course.getId() == updatedCourse.getId()) {
                    cursos.set(i, updatedCourse);
                    avlTree.remove(course);
                    avlTree.add(updatedCourse);
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

        for (int i = 0; i < cursos.size(); i++) {

            if( util.Utility.compare(cursos.get(i).getId(),courseId)==0){

                cursos.remove(cursos.get(i));
                avlTree.remove(cursos.get(i));
                return true; // el curso existe

            }

        }

        return false;

    }//todo funcional

    @Override
    public List<Course> listCourse() {
        return new ArrayList<>(cursos); // Devolver una copia de la lista de cursos
    }//todo funcional

    public void saveCoursesToFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile(); // Crea el archivo si no existe
            }
            try (FileOutputStream fileOut = new FileOutputStream(filename);
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(cursos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//todo funcional

    public void loadCoursesFromFile(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            cursos = (ArrayList<Course>) in.readObject();
            avlTree.clear(); // Limpiar el árbol AVL antes de recargar los cursos
            for (Course course : cursos) {
                avlTree.add(course);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }//todo funcional

    public void viewAllCourses(String filename) {
        loadCoursesFromFile(filename); // Cargar los cursos desde el archivo
        for (Course course : cursos) {
            System.out.println(course); // Imprimir cada curso

        }
    }//todo funcional

}

