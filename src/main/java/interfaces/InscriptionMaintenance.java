package interfaces;

import domain.Course;
import domain.Enrollment;
import domain.User;

import java.util.List;


public interface InscriptionMaintenance {
    /**### 5. Mantenimiento de Inscripciones
     - *Gestión de inscripciones:*
     - enrollStudent(int userId, int courseId): boolean: Inscribe a un estudiante en un curso y devuelve true si se inscribe con éxito.
    **/
    public boolean enrollStudent(int userId, int courseId);
    /** - listEnrollments(): List<Enrollment>: Devuelve una lista de todas las inscripciones.
    **/
    public List<Enrollment> listEnrollments();
    /** - approveEnrollment(int enrollmentId): boolean: Aprueba una inscripción pendiente y devuelve true si se aprueba con éxito.
    **/
    public boolean approveEnrollment(int enrollmentId);
    /** - rejectEnrollment(int enrollmentId): boolean: Rechaza una inscripción pendiente y devuelve true si se rechaza con éxito.
    **/
    public boolean cancalEnrollment(int userId, int courseId);
    /** - cancelEnrollment(int userId, int courseId): boolean: Permite a un estudiante cancelar su inscripción en un curso y devuelve true si se cancela con éxito.
    **/
    public boolean cancelEnrollment(int userId, int courseId);
    /**- *Notificaciones de inscripciones:*
     - sendEnrollmentConfirmation(User user, Course course): void: Envía una confirmación por correo electrónico al estudiante y al administrador.
     **/
    public void sendEnrollmentConfirmation(User user, Course course);
}
