package interfaces;

import domain.Course;
import domain.Enrollment;
import domain.User;
import structures.trees.TreeException;

import java.util.List;

public interface InscriptionMaintenance {
    /**### 5. Mantenimiento de Inscripciones
     - *Gestión de inscripciones:*
     - enrollStudent(int userId, int courseId): boolean: Inscribe a un estudiante en un curso y devuelve true si se inscribe con éxito.
    **/
    public boolean enrollStudent(int userId, int courseId) throws TreeException;
    /** - listEnrollments(): List<Enrollment>: Devuelve una lista de todas las inscripciones.
    **/
    public List<Enrollment> listEnrollments();
    /** - cancelEnrollment(int userId, int courseId): boolean: Permite a un estudiante cancelar su inscripción en un curso y devuelve true si se cancela con éxito.
    **/
    public boolean cancelEnrollment(int userId, int courseId) throws TreeException;
}
