package interfaces;

import com.itextpdf.kernel.pdf.PdfDocument;

import java.util.List;

public interface Reports {

    /**
     ### 7. Reportes
     - *Generación de reportes en PDF:*
     - generateEnrollmentReport(): PDFDocument: Crea un informe de inscripciones por curso y devuelve el documento PDF generado.
     **/
    public PdfDocument generateEnrollmentReport(int courseID);

    /**- generateStudentProgressReport(int userId): PDFDocument: Crea un informe del progreso del estudiante y devuelve el documento PDF generado.
    **/
    public PdfDocument generateStudentProgressReport(int userID);

    /** - generateEvaluationReport(int courseId): PDFDocument: Crea un informe de evaluaciones y calificaciones y devuelve el documento PDF generado.
    **/
    public PdfDocument generateEvaluationReport(int courseID);

    /** - *Utilidades de reportes:*
     - createPDF(String title, List<String> content): PDFDocument: Genera un archivo PDF con el contenido proporcionado y devuelve el documento PDF.
     **/

    public PdfDocument createPDF(String title, List<String> content);
}
