package Generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.awt.Desktop;

import documentData.*;

public class Generator {

    private final criteriosInspeccion criteriosInspeccion;
    private final dataDocumento dataDocumento;
    private resultadosInspeccion resultadosInspeccion;
    private final Map<String, String> placeholders;

    private final String key;
    private final String tipoDocumento; // Variable para determinar el tipo de documento

    public Generator(criteriosInspeccion criteriosInspeccion, dataDocumento dataDocumento, resultadosInspeccion resultadosInspeccion, String tipoDocumento) {
        this.placeholders = new HashMap<>();
        this.criteriosInspeccion = criteriosInspeccion;
        this.dataDocumento = dataDocumento;
        this.resultadosInspeccion = resultadosInspeccion;
        this.tipoDocumento = tipoDocumento;

        key = dataDocumento.getPnIds() + " - " + dataDocumento.getFechaEvaluacion();

    }

    public void printPlaceholders() {
        System.out.println("Printing all placeholders and their values:");
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void fillPlaceholders() {
        // Formatear la fecha actual
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Inicializar el mapa de placeholders con la fecha formateada
        placeholders.put("fecha_elaborado", formattedDate);
        placeholders.put("fecha_revisado", formattedDate);
        // Continuar agregando otros placeholders aquí...

        placeholders.put("nombre_producto", dataDocumento.getNombreProducto());
        placeholders.put("marca", dataDocumento.getMarca());
        placeholders.put("pn_ids", dataDocumento.getPnIds());
        placeholders.put("numero_lote", dataDocumento.getNumeroLote());
        placeholders.put("cantidad_unidades", String.valueOf(dataDocumento.getCantidadUnidades()));
        placeholders.put("fecha_evaluacion", dataDocumento.getFechaEvaluacion());
        placeholders.put("nivel_inspeccion", criteriosInspeccion.getNivelInspeccion());
        placeholders.put("tamano_muestra", String.valueOf(criteriosInspeccion.getTamanoMuestra()));
        placeholders.put("aql_definido", String.valueOf(criteriosInspeccion.getAQLDefinido()));  // Assuming "AQL" is a fixed value or needs to be calculated/obtained somehow
        placeholders.put("numero_rechazo", String.valueOf(criteriosInspeccion.getCantidadRechazo()));
        placeholders.put("numero_aceptacion", String.valueOf(criteriosInspeccion.getCantidadAceptacion()));
        placeholders.put("numero_defectos", String.valueOf(resultadosInspeccion.getNumeroDefectos()));
        placeholders.put("porcentaje_defectos", String.format("%.2f", resultadosInspeccion.getPorcentajeDefectos()));
        placeholders.put("resultado", resultadosInspeccion.getResultadoFinal());
        placeholders.put("observaciones", resultadosInspeccion.getObservaciones());

        if ("Despacho".equals(tipoDocumento)) {
            placeholders.put("cliente", dataDocumento.getCliente());
            placeholders.put("referencia_cliente", dataDocumento.getReferenciaCliente());
        } else if ("Recepción".equals(tipoDocumento)) {
            placeholders.put("proveedor", dataDocumento.getProveedor());
            placeholders.put("factura", dataDocumento.getFactura());
        }
    }

    public void generateDocument() throws Exception {
        fillPlaceholders();  // Llena los placeholders antes de procesar el documento

        InputStream templatePath;
        if ("Despacho".equals(tipoDocumento)) {
            templatePath = getClass().getResourceAsStream("/templates/plantilla_despacho.docx");
        } else if ("Recepción".equals(tipoDocumento)) {
            templatePath = getClass().getResourceAsStream("/templates/plantilla_recepcion.docx");
        } else {
            throw new IllegalArgumentException("Unsupported document type: " + tipoDocumento);
        }

        XWPFDocument document = new XWPFDocument(templatePath);

        // Procesar los párrafos en el cuerpo del documento
        processParagraphs(document.getParagraphs());

        // Procesar cada tabla en el documento
        document.getTables().forEach(table -> {
            table.getRows().forEach(row -> {
                row.getTableCells().forEach(cell -> {
                    processParagraphs(cell.getParagraphs());
                });
            });
        });

        // Guardar el documento modificado
        saveDocument(document);
    }

    private void processParagraphs(List<XWPFParagraph> paragraphs) {
        for (XWPFParagraph paragraph : paragraphs) {
            String fullText = paragraph.getText();  // Get full text of the paragraph
            boolean modified = false;

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                if (fullText.contains(placeholder)) {
                    fullText = fullText.replace(placeholder, entry.getValue());
                    modified = true;
                }
            }

            if (modified) {
                List<XWPFRun> runs = paragraph.getRuns();
                int runsSize = runs.size();
                for (int i = runsSize - 1; i >= 0; i--) {
                    paragraph.removeRun(i);  // Remove old runs
                }
                // Set new text with specified font size
                XWPFRun newRun = paragraph.createRun();
                newRun.setText(fullText);
                newRun.setFontSize(8);  // Set the font size to 8 points
            }
        }
    }

    private void saveDocument(XWPFDocument document) throws IOException {
        String userHome = System.getProperty("user.home");
        String oneDriveDir = "/Onedrive - Inventory and Distribution Services";
        String wordDocsPath = userHome + oneDriveDir + "/AQL's" + "/Word";
        Path wordOutputPath = Paths.get(wordDocsPath, key + ", " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH;mm;ss")) + ".docx");
        Files.createDirectories(wordOutputPath.getParent());
        try (FileOutputStream out = new FileOutputStream(wordOutputPath.toFile())) {
            document.write(out);
        }
        openFolder(wordOutputPath.getParent().toString());
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
