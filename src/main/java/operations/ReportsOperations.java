package operations;

import interfaces.Reports;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ReportsOperations implements Reports {

    // Ruta del directorio donde se guardarán los archivos PDF generados.
    private static final String DEST = "./reports/";

    // Método para asegurarse de que el directorio de destino exista.
    private void ensureDirectoryExists() {
        File directory = new File(DEST);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    @Override
    public PdfDocument generateEnrollmentReport() {
        // Implementación de ejemplo para crear un informe de inscripciones
        try {
            String filename = DEST + "enrollmentReport.pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
            Document doc = new Document(pdfDoc);
            doc.add(new Paragraph("Informe de Inscripciones"));
            // Aquí agregarías el contenido real del informe
            doc.close();
            return pdfDoc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PdfDocument generateStudentProgressReport(int userId) {
        // Implementación de ejemplo para crear un informe del progreso del estudiante
        try {
            String filename = DEST + "studentProgressReport_" + userId + ".pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
            Document doc = new Document(pdfDoc);
            doc.add(new Paragraph("Informe de Progreso del Estudiante"));
            // Aquí agregarías el contenido real del informe
            doc.close();
            return pdfDoc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PdfDocument generateEvaluationReport(int courseId) {
        // Implementación de ejemplo para crear un informe de evaluaciones y calificaciones
        try {
            String filename = DEST + "evaluationReport_" + courseId + ".pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
            Document doc = new Document(pdfDoc);
            doc.add(new Paragraph("Informe de Evaluaciones y Calificaciones"));
            // Aquí agregarías el contenido real del informe
            doc.close();
            return pdfDoc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PdfDocument createPDF(String title, List<String> content) {
        try {
            String filename = DEST + "customReport.pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
            Document doc = new Document(pdfDoc);
            doc.add(new Paragraph(title));
            for (String line : content) {
                doc.add(new Paragraph(line));
            }
            doc.close();
            return pdfDoc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
