package operations;

import domain.Course;
import interfaces.CourseMaintenance;
import structures.trees.AVL;
import structures.trees.TreeException;

import java.util.ArrayList;
import java.util.List;

public class CourseOperations implements CourseMaintenance {
    AVL avlTree = new AVL();
    ArrayList<Course> cursos = new ArrayList<>();
    @Override
    public boolean createCourse(Course course) { //Agrega un nuevo curso al árbol AVL y devuelve true si se crea con éxito.

        try {
            if(avlTree.isEmpty()){
                System.out.println("El curso fue agregado");
                avlTree.add(course); //el curso fue agregado con exito
                cursos.add(course);
                return true;
            }else if(avlTree.contains(course)){
                System.out.println("El curso ya existe");
                return false; //el curso ya existe

            }else if (!avlTree.contains(course)){
                System.out.println("El curso fue agregado");
                avlTree.add(course); //el curso fue agregado con exito
                cursos.add(course);
                return true;
            }

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return false;
    }//todo condicion de mismos ID

    @Override
    public String readCourse(int courseId) { //Busca y devuelve un curso por su ID.
        for (int i = 0; i < cursos.size(); i++) {
           if( util.Utility.compare(cursos.get(i).getId(),courseId)==0){
                return "The course has been found \n" +  cursos.get(i).toString(); // el curso existe
           }
        }
        return "The course doesn`t exists";// no existe el curso
    }

    @Override
    public boolean updateCourse(Course course) {
        return false;
    }

    @Override
    public boolean deleteCourse(int courseId) {
        return false;
    }

    @Override
    public List<Course> listCourse() {
        return null;
    }
}
