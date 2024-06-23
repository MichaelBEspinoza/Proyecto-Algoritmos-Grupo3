package operations;

import interfaces.Reports;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import structures.queues.LinkedQueue;
import structures.queues.QueueException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportsOperations implements Reports {

    // Ruta del directorio donde se guardarán los archivos PDF generados.
    private static final String DEST = "./reports/";
    private LinkedQueue eventLog; // Cola para la bitácora de eventos


    // Método para asegurarse de que el directorio de destino exista.
    private void ensureDirectoryExists() {
        File directory = new File(DEST);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public PdfDocument generateEnrollmentReport(int courseId) {
        ensureDirectoryExists();
        try {
            String filename = DEST + "enrollmentReport_" + courseId + ".pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
            Document doc = new Document(pdfDoc);
            doc.add(new Paragraph("Informe de Inscripciones para el Curso ID: " + courseId));

            if(util.Utility.getRandom(100)>70){
                doc.add(new Paragraph("\nEl curso es popular "+ "\n"));

            }else if(util.Utility.getRandom(100)>50){
                doc.add(new Paragraph("\nEl curso no es popular "+ "\n"));

            }

            doc.close();
            return pdfDoc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PdfDocument generateStudentProgressReport(String userId) {
        ensureDirectoryExists();
        try {
            String filename = DEST + "studentProgressReport_" + userId + ".pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
            Document doc = new Document(pdfDoc);
            doc.add(new Paragraph("Informe de Progreso del Estudiante"));

            for (int i = 0; i < 10; i++) {
                if(util.Utility.getRandom(100)>70){
                    doc.add(new Paragraph("\nLeccion aprobada "+ "\n"));
                }else{
                    doc.add(new Paragraph("\nLeccion reprobado "+ "\n"));
                }
            }

            doc.close();
            return pdfDoc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PdfDocument generateEvaluationReport(int courseId) {
        ensureDirectoryExists();
        try {
            String filename = DEST + "evaluationReport_" + courseId + ".pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
            Document doc = new Document(pdfDoc);
            doc.add(new Paragraph("Informe de Evaluaciones y Calificaciones"));
            for (int i = 0; i < 10; i++) {
                doc.add(new Paragraph("\nNota ponderada de leccion: " + i + " " + util.Utility.getRandom(100) + "\n"));
            }
            doc.close();
            return pdfDoc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PdfDocument createPDF(String title, List<String> content) {
        ensureDirectoryExists();
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

    //MÉTODOS DE LA BITÁCORA
    @Override
    public void logEvent(String timestamp, int userId, String description) {
        try {
            eventLog.enQueue("[" + timestamp + "] User ID: " + userId + " - " + description);
        } catch (QueueException e) {
            e.printStackTrace();
        }
    }

    public void saveEventLog() {
        try {
            String filename = DEST + "eventLog_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            List<String> logEntries = eventLogToList();
            Files.write(Paths.get(filename), logEntries);
        } catch (IOException | QueueException e) {
            e.printStackTrace();
        }
    }

    private List<String> eventLogToList() throws QueueException {
        LinkedQueue tempQueue = new LinkedQueue();
        List<String> logEntries = new java.util.ArrayList<>();
        while (!eventLog.isEmpty()) {
            String logEntry = (String) eventLog.deQueue();
            logEntries.add(logEntry);
            tempQueue.enQueue(logEntry);
        }
        while (!tempQueue.isEmpty()) {
            eventLog.enQueue(tempQueue.deQueue());
        }
        return logEntries;
    }
}
