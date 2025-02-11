package Generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.awt.Desktop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import documentData.*;
import java.util.ArrayList;

public class Generator {

    LocalDate date = LocalDate.now();
    private criteriosInspeccion criteriosInspeccion;
    private dataDocumento dataDocumento;
    private resultadosInspeccion resultadosInspeccion;
    
    private List<String> infoGeneralList = new ArrayList<>();
    private List<String> criteriosInspeccionList = new ArrayList<>();
    private List<String> resultadoInspeccionList = new ArrayList<>();
    
            

    public Generator(criteriosInspeccion criteriosInspeccion, dataDocumento dataDocumento, resultadosInspeccion resultadosInspeccion) {
        this.criteriosInspeccion = criteriosInspeccion;
        this.dataDocumento = dataDocumento;
        this.resultadosInspeccion = resultadosInspeccion;
    }

    public void openFolder(String filePath) {
        File file = new File(filePath);
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
        }
    }
    
   

    public void generateDocument(String key, Map<String, String> placeholders, List<String> specificationsContent) throws Exception {
        LocalDateTime date1 = LocalDateTime.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH;mm;ss");
        String formattedDateTime = date1.format(formatter1);

        InputStream templatePath = getClass().getResourceAsStream("/templates/templateDoc.docx");

        String userHome = System.getProperty("user.home");
        String oneDriveDir = "/Onedrive - Inventory and Distribution Services";
        String wordDocsPath = userHome + oneDriveDir + "/COC's" + "/Word";
        Path folderPath = Paths.get(wordDocsPath);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
            System.out.println("Folder created: " + folderPath.toString());
        }

        String pdfFolderPath = userHome + oneDriveDir + "/COC's" + "/PDF";
        Path pdfFolder = Paths.get(pdfFolderPath);
        if (!Files.exists(pdfFolder)) {
            Files.createDirectories(pdfFolder);
            System.out.println("PDF folder created: " + pdfFolderPath);
        }

        // Crear los nombres de archivo dinámicamente
        String wordOutputPath = wordDocsPath + "/" + key + ", " + formattedDateTime + ".docx";
        String pdfFilePath = pdfFolderPath + "/" + key + ", " + formattedDateTime + ".pdf";

        XWPFDocument document = new XWPFDocument(templatePath);
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        // Reemplazo de placeholders en el documento
        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            String paragraphText = paragraph.getText();

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                String value = entry.getValue();
                paragraphText = paragraphText.replace(placeholder, value);

                for (XWPFRun run : runs) {
                    String runText = run.getText(0);
                    if (runText != null && runText.contains(placeholder)) {
                        run.setText(runText.replace(placeholder, value), 0);
                        run.setBold(true);
                    }
                }
            }

            // Reemplazo de la fecha de creación
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es"));
            String formattedDate = currentDate.format(formatter2);
            String capitalizedDay = formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
            paragraphText = paragraphText.replace("{{creation_date}}", capitalizedDay);

            // Eliminar runs vacíos de imágenes
            for (int i = runs.size() - 1; i >= 0; i--) {
                XWPFRun run = runs.get(i);
                if (run.getEmbeddedPictures().size() == 0) {
                    paragraph.removeRun(i);
                }
            }

            // Crear el run con el texto modificado
            XWPFRun run = paragraph.createRun();
            run.setText(paragraphText);
        }

        // Reemplazo de especificaciones
        XWPFParagraph specificationsParagraph = null;
        for (XWPFParagraph paragraph : paragraphs) {
            if (paragraph.getText().contains("{{specifications}}")) {
                specificationsParagraph = paragraph;
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                break;
            }
        }

        if (specificationsParagraph != null) {
            List<XWPFRun> runs = specificationsParagraph.getRuns();
            for (int i = runs.size() - 1; i >= 0; i--) {
                XWPFRun run = runs.get(i);

                if (run.getText(0).contains("{{specifications}}")) {
                    specificationsParagraph.removeRun(i);
                }
            }

            for (String specLine : specificationsContent) {
                XWPFRun specRun = specificationsParagraph.createRun();
                String[] specParts = specLine.split(":");
                specRun.setText(specParts[0] + ": " + specParts[1]);
                specRun.addCarriageReturn();
            }
        } else {
            System.out.println("Placeholder not found");
        }

        // Guardar el documento de Word
        try (FileOutputStream out = new FileOutputStream(wordOutputPath)) {
            document.write(out);
        }

        // Guardar el documento como PDF
        saveAsPdf(wordOutputPath, pdfFilePath);
        openFolder(wordDocsPath);
        openFolder(pdfFolderPath);
    }

    public void saveAsPdf(String wordFilePath, String pdfFilePath) throws IOException {
        // Obtener el directorio base donde se ejecuta la aplicación
        String appDir = System.getProperty("user.dir");
        String libreOfficePath;

        // Determinar el sistema operativo (Windows o macOS)
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            // En Windows, asumimos que LibreOfficePortable.exe está en resources/libreoffice
            libreOfficePath = appDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "libreoffice" + File.separator + "LibreOfficePortable.exe";
        } else if (os.contains("mac")) {
            // En macOS, el archivo soffice debe estar en resources/libreoffice/MacOS
            libreOfficePath = appDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "libreoffice" + File.separator + "MacOS" + File.separator + "soffice";
        } else {
            throw new UnsupportedOperationException("Sistema operativo no soportado: " + os);
        }

        // Verificar si el archivo de LibreOffice existe
        File libreOffice = new File(libreOfficePath);
        if (!libreOffice.exists()) {
            throw new IOException("LibreOffice no encontrado en: " + libreOfficePath);
        }

        // Comando para convertir el archivo a PDF
        String[] command = {
            libreOfficePath,
            "--headless",
            "--convert-to", "pdf:writer_pdf_Export",
            "--outdir", new File(pdfFilePath).getParent(),
            wordFilePath
        };

        // Ejecutar el comando
        Process process = new ProcessBuilder(command).start();

        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Conversión exitosa a PDF.");
            } else {
                System.err.println("Error en la conversión. Código de salida: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaurar estado de interrupción
            System.err.println("Error esperando la finalización del proceso: " + e.getMessage());
        }
    }
}
