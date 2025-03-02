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
import java.util.HashMap;

public class Generator {

    LocalDate date = LocalDate.now();
    private criteriosInspeccion criteriosInspeccion;
    private dataDocumento dataDocumento;
    private resultadosInspeccion resultadosInspeccion;
    Map<String, String> placeholders = new HashMap<>();

    private String key;
    private String tipoDocumento; // Variable para determinar el tipo de documento

    public Generator(criteriosInspeccion criteriosInspeccion, dataDocumento dataDocumento, resultadosInspeccion resultadosInspeccion, String tipoDocumento) {
        this.criteriosInspeccion = criteriosInspeccion;
        this.dataDocumento = dataDocumento;
        this.resultadosInspeccion = resultadosInspeccion;
        this.tipoDocumento = tipoDocumento;

        key = dataDocumento.getCliente() + " - " + dataDocumento.getFechaEvaluacion();

        // Inicializar el mapa de placeholders
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("nombre_producto", dataDocumento.getNombreProducto());
        placeholders.put("marca", dataDocumento.getMarca());
        placeholders.put("fecha_evaluacion", dataDocumento.getFechaEvaluacion());
        placeholders.put("pn_ids", dataDocumento.getPnIds());
        placeholders.put("numero_lote", dataDocumento.getNumeroLote());
        placeholders.put("cantidad_unidades", String.valueOf(dataDocumento.getCantidadUnidades()));
        placeholders.put("nivel_inspeccion", criteriosInspeccion.getNivelInspeccion());
        placeholders.put("tamano_muestra", String.valueOf(criteriosInspeccion.getTamanoMuestra()));
        placeholders.put("aql_definido", String.valueOf(criteriosInspeccion.getAQLDefinido()));  // Suponiendo que "AQL" es un valor fijo o debe ser calculado/obtenido de alguna manera
        placeholders.put("numero_rechazo", String.valueOf(criteriosInspeccion.getCantidadRechazo()));
        placeholders.put("numero_aceptacion", String.valueOf(criteriosInspeccion.getCantidadAceptacion()));
        placeholders.put("numero_defectos", String.valueOf(resultadosInspeccion.getNumeroDefectos()));
        placeholders.put("porcentaje_defectos", String.format("%.2f", resultadosInspeccion.getPorcentajeDefectos()));
        placeholders.put("resultado", resultadosInspeccion.getResultadoFinal());
        placeholders.put("observaciones", resultadosInspeccion.getObservaciones());

        // Añadir campos específicos según el tipo de documento
        if ("Despacho".equals(tipoDocumento)) {
            placeholders.put("cliente", dataDocumento.getCliente());
            placeholders.put("referencia_cliente", dataDocumento.getReferenciaCliente());
        } else if ("Recepción".equals(tipoDocumento)) {
            placeholders.put("proveedor", dataDocumento.getProveedor());
            placeholders.put("factura", dataDocumento.getFactura());
        }

        this.placeholders = placeholders; // Asumiendo que `placeholders` es un campo de la clase
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

    public void generateDocument(Map<String, String> placeholders) throws Exception {
        LocalDateTime date1 = LocalDateTime.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH;mm;ss");
        String formattedDateTime = date1.format(formatter1);

        // Selección de la plantilla en función del tipo de documento
        InputStream templatePath;
        if (null == tipoDocumento) {
            throw new IllegalArgumentException("Tipo de documento no soportado: " + tipoDocumento);
        } else {
            switch (tipoDocumento) {
                case "Despacho" -> templatePath = getClass().getResourceAsStream("/templates/plantilla_despacho.docx");
                case "Recepción" -> templatePath = getClass().getResourceAsStream("/templates/plantilla_recepcion.docx");
                default -> throw new IllegalArgumentException("Tipo de documento no soportado: " + tipoDocumento);
            }
        }

        String userHome = System.getProperty("user.home");
        String oneDriveDir = "/Onedrive - Inventory and Distribution Services";
        String wordDocsPath = userHome + oneDriveDir + "/AQL's" + "/Word";
        Path folderPath = Paths.get(wordDocsPath);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
            System.out.println("Folder created: " + folderPath.toString());
        }

        String pdfFolderPath = userHome + oneDriveDir + "/AQL's" + "/PDF";
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
            if (runs.size() > 0) {
                String text = runs.get(0).getText(0);
                for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                    String placeholder = "{{" + entry.getKey() + "}}";
                    if (text != null && text.contains(placeholder)) {
                        text = text.replace(placeholder, entry.getValue());
                        runs.get(0).setText(text, 0);
                    }
                }
            }
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
        // Determinar el sistema operativo y establecer la ruta de LibreOffice correspondiente
        String os = System.getProperty("os.name").toLowerCase();
        String libreOfficePath = "/path/to/libreoffice"; // Modificar según la configuración de tu entorno

        if (os.contains("win")) {
            libreOfficePath = "C:\\Program Files\\LibreOffice\\program\\soffice.exe";
        } else if (os.contains("mac")) {
            libreOfficePath = "/Applications/LibreOffice.app/Contents/MacOS/soffice";
        }

        // Comando para convertir el documento Word a PDF
        String command = libreOfficePath + " --headless --convert-to pdf --outdir " + new File(pdfFilePath).getParent() + " " + wordFilePath;

        // Ejecutar el comando
        Runtime.getRuntime().exec(command);
    }
}
