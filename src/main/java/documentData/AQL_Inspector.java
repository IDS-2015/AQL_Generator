package documentData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class AQL_Inspector {

    private int cantidadUnidadesLote;
    private int cantidadUnidadesAMuestrear, cantidadAceptacion, cantidadRechazo;
    private String codigoInspeccion;
    private double porcentajeSeleccionado; // Porcentaje elegido por el usuario

    // Tabla 1: Determinar el código de inspección basado en el tamaño del lote
    private static final Map<Integer, String> tablaInspeccion = new HashMap<>();

    static {
        tablaInspeccion.put(8, "A");

        tablaInspeccion.put(15, "B");

        tablaInspeccion.put(25, "C");
        tablaInspeccion.put(50, "D");
        tablaInspeccion.put(90, "E");
        tablaInspeccion.put(150, "F");
        tablaInspeccion.put(280, "G");
        tablaInspeccion.put(500, "H");
        tablaInspeccion.put(1200, "J");
        tablaInspeccion.put(3200, "K");
        tablaInspeccion.put(10000, "L");
        tablaInspeccion.put(35000, "M");
        tablaInspeccion.put(150000, "N");
        tablaInspeccion.put(500000, "P");
        tablaInspeccion.put(500001, "Q");

    }

    // Mapa que relaciona cada letra con los porcentajes permitidos.
    private static final Map<String, double[]> porcentajesPermitidos = new HashMap<>();

    static {
        porcentajesPermitidos.put("A", new double[]{6.5});
        porcentajesPermitidos.put("B", new double[]{4.0});
        porcentajesPermitidos.put("C", new double[]{2.5});
        porcentajesPermitidos.put("D", new double[]{1.5, 6.5});
        porcentajesPermitidos.put("E", new double[]{1.0, 4.0, 6.5});
        porcentajesPermitidos.put("F", new double[]{0.65, 2.5, 4.0, 6.5});
        porcentajesPermitidos.put("G", new double[]{0.40, 1.5, 2.5, 4.0, 6.5});
        porcentajesPermitidos.put("H", new double[]{0.25, 1.0, 1.5, 2.5, 4.0, 6.5});
        porcentajesPermitidos.put("J", new double[]{0.15, 0.65, 1.5, 2.5, 4.0, 6.5});
        porcentajesPermitidos.put("K", new double[]{0.10, 0.40, 0.65, 1.0, 1.5, 2.5, 4.0, 6.5});
        porcentajesPermitidos.put("L", new double[]{0.065, 0.25, 0.40, 0.65, 1.0, 1.5, 2.5, 4.0, 6.5});
        porcentajesPermitidos.put("M", new double[]{0.15, 0.25, 0.40, 0.65, 1.0, 1.5, 2.5, 4.0});
        porcentajesPermitidos.put("N", new double[]{0.10, 0.25, 0.40, 0.65, 1.0, 1.5, 2.5});
        porcentajesPermitidos.put("P", new double[]{0.065, 0.10, 0.25, 0.40, 0.65, 1.0, 1.5});
        porcentajesPermitidos.put("Q", new double[]{0.065, 0.10, 0.25, 0.40, 0.65, 1.0});
    }

    /* 
     * Tabla dinámica de muestreo: para cada código (letra) y cada porcentaje permitido,
     * se definen:
     * [cantidad a muestrear, límite de aceptación, límite de rechazo].
     * (Los valores aquí son de ejemplo y deben ajustarse a tus requerimientos reales.)
     */
    private static final Map<String, Map<Double, int[]>> tablaMuestreoDinamica = new HashMap<>();

    static {
        // Letra A: solo 6.5%
        Map<Double, int[]> tableA = new HashMap<>();
        tableA.put(6.5, new int[]{2, 0, 1});
        tablaMuestreoDinamica.put("A", tableA);

        // Letra B: solo 4.0%
        Map<Double, int[]> tableB = new HashMap<>();
        tableB.put(4.0, new int[]{3, 0, 1});
        tablaMuestreoDinamica.put("B", tableB);

        // Letra C: solo 2.5%
        Map<Double, int[]> tableC = new HashMap<>();
        tableC.put(2.5, new int[]{5, 0, 1});
        tablaMuestreoDinamica.put("C", tableC);

        // Letra D: 1.5% y 6.5% (valores de ejemplo)
        Map<Double, int[]> tableD = new HashMap<>();
        tableD.put(1.5, new int[]{8, 0, 1});
        tableD.put(6.5, new int[]{8, 1, 2});
        tablaMuestreoDinamica.put("D", tableD);

        // Letra E: 1.0, 4.0 y 6.5%
        Map<Double, int[]> tableE = new HashMap<>();
        tableE.put(1.0, new int[]{13, 0, 1});
        tableE.put(4.0, new int[]{13, 1, 2});
        tableE.put(6.5, new int[]{13, 2, 3});
        tablaMuestreoDinamica.put("E", tableE);

        // Letra F: 0.65, 2.5, 4.0 y 6.5%
        Map<Double, int[]> tableF = new HashMap<>();
        tableF.put(0.65, new int[]{20, 0, 1});
        tableF.put(2.5, new int[]{20, 1, 2});
        tableF.put(4.0, new int[]{20, 2, 3});
        tableF.put(6.5, new int[]{20, 3, 4});
        tablaMuestreoDinamica.put("F", tableF);

        // Letra G: 0.40, 1.5, 2.5, 4.0 y 6.5%
        Map<Double, int[]> tableG = new HashMap<>();
        tableG.put(0.40, new int[]{32, 0, 1});
        tableG.put(1.5, new int[]{32, 1, 2});
        tableG.put(2.5, new int[]{32, 2, 3});
        tableG.put(4.0, new int[]{32, 3, 4});
        tableG.put(6.5, new int[]{32, 5, 6});
        tablaMuestreoDinamica.put("G", tableG);

        // Letra H: 0.25, 1.0, 1.5, 2.5, 4.0 y 6.5%
        Map<Double, int[]> tableH = new HashMap<>();
        tableH.put(0.25, new int[]{50, 0, 1});
        tableH.put(1.0, new int[]{50, 1, 2});
        tableH.put(1.5, new int[]{50, 2, 3});
        tableH.put(2.5, new int[]{50, 3, 4});
        tableH.put(4.0, new int[]{50, 5, 6});
        tableH.put(6.5, new int[]{50, 7, 8});
        tablaMuestreoDinamica.put("H", tableH);

        // Letra J: 0.15, 0.65, 1.5, 2.5, 4.0 y 6.5%
        Map<Double, int[]> tableJ = new HashMap<>();
        tableJ.put(0.15, new int[]{80, 0, 1});
        tableJ.put(0.65, new int[]{80, 1, 2});
        tableJ.put(1.0, new int[]{80, 2, 3});
        tableJ.put(1.5, new int[]{80, 3, 4});
        tableJ.put(2.5, new int[]{80, 5, 6});
        tableJ.put(4.0, new int[]{80, 7, 8});
        tableJ.put(6.5, new int[]{80, 10, 11});
        tablaMuestreoDinamica.put("J", tableJ);

        // Letra K: 0.10, 0.40, 0.65, 1.0, 1.5, 2.5, 4.0 y 6.5%
        Map<Double, int[]> tableK = new HashMap<>();
        tableK.put(0.10, new int[]{125, 0, 1});
        tableK.put(0.40, new int[]{125, 1, 2});
        tableK.put(0.65, new int[]{125, 2, 3});
        tableK.put(1.0, new int[]{125, 3, 4});
        tableK.put(1.5, new int[]{125, 5, 6});
        tableK.put(2.5, new int[]{125, 7, 8});
        tableK.put(4.0, new int[]{125, 10, 11});
        tableK.put(6.5, new int[]{125, 14, 15});
        tablaMuestreoDinamica.put("K", tableK);

        // Letra L: 0.065, 0.25, 0.40, 0.65, 1.0, 1.5, 2.5, 4.0 y 6.5%
        Map<Double, int[]> tableL = new HashMap<>();
        tableL.put(0.065, new int[]{200, 0, 1});
        tableL.put(0.25, new int[]{200, 1, 2});
        tableL.put(0.40, new int[]{200, 2, 3});
        tableL.put(0.65, new int[]{200, 3, 4});
        tableL.put(1.0, new int[]{200, 3, 3});
        tableL.put(1.5, new int[]{200, 5, 6});
        tableL.put(2.5, new int[]{200, 7, 8});
        tableL.put(4.0, new int[]{200, 14, 15});
        tableL.put(6.5, new int[]{200, 21, 22});
        tablaMuestreoDinamica.put("L", tableL);

        // Letra M: 0.15, 0.25, 0.40, 0.65, 1.0, 1.5, 2.5 y 4.0%
        Map<Double, int[]> tableM = new HashMap<>();
        tableM.put(0.15, new int[]{315, 1, 2});
        tableM.put(0.25, new int[]{315, 2, 3});
        tableM.put(0.40, new int[]{315, 3, 4});
        tableM.put(0.65, new int[]{315, 5, 6});
        tableM.put(1.0, new int[]{315, 7, 8});
        tableM.put(1.5, new int[]{315, 10, 11});
        tableM.put(2.5, new int[]{315, 14, 15});
        tableM.put(4.0, new int[]{315, 21, 22});
        tablaMuestreoDinamica.put("M", tableM);

        // Letra N: 0.10, 0.25, 0.40, 0.65, 1.0, 1.5 y 2.5%
        Map<Double, int[]> tableN = new HashMap<>();
        tableN.put(0.10, new int[]{500, 1, 2});
        tableN.put(0.15, new int[]{500, 2, 3});
        tableN.put(0.25, new int[]{500, 3, 4});
        tableN.put(0.40, new int[]{500, 5, 6});
        tableN.put(0.65, new int[]{500, 7, 8});
        tableN.put(1.0, new int[]{500, 10, 11});
        tableN.put(1.5, new int[]{500, 14, 15});
        tableN.put(2.5, new int[]{500, 21, 22});
        tablaMuestreoDinamica.put("N", tableN);

        // Letra P: 0.065, 0.10, 0.25, 0.40, 0.65 y 1.5%
        Map<Double, int[]> tableP = new HashMap<>();
        tableP.put(0.065, new int[]{800, 1, 2});
        tableP.put(0.10, new int[]{800, 2, 3});
        tableP.put(0.15, new int[]{800, 3, 4});
        tableP.put(0.25, new int[]{800, 5, 6});
        tableP.put(0.40, new int[]{800, 7, 8});
        tableP.put(0.65, new int[]{800, 10, 11});
        tableP.put(1.0, new int[]{800, 14, 15});
        tableP.put(1.5, new int[]{800, 21, 22});
        tablaMuestreoDinamica.put("P", tableP);

        // Letra Q: 0.065, 0.10, 0.25, 0.40 y 0.65%
        Map<Double, int[]> tableQ = new HashMap<>();
        tableQ.put(0.065, new int[]{1250, 2, 3});
        tableQ.put(0.10, new int[]{1250, 3, 4});
        tableQ.put(0.15, new int[]{1250, 5, 6});
        tableQ.put(0.25, new int[]{1250, 7, 8});
        tableQ.put(0.40, new int[]{1250, 10, 11});
        tableQ.put(0.65, new int[]{1250, 14, 15});
        tableQ.put(1.0, new int[]{1250, 21, 22});
        tablaMuestreoDinamica.put("Q", tableQ);
    }

    // Constructor que recibe la cantidad de unidades del lote
    public AQL_Inspector(int cantidadUnidadesLote) {
        this.cantidadUnidadesLote = cantidadUnidadesLote;
        this.codigoInspeccion = obtenerCodigoInspeccion(cantidadUnidadesLote);

        // Inicializar el porcentaje seleccionado con el primer porcentaje permitido
        double[] opciones = porcentajesPermitidos.get(codigoInspeccion);
        if (opciones != null && opciones.length > 0) {
            porcentajeSeleccionado = opciones[0]; // Seleccionar el primer porcentaje
        } else {
            System.out.println("No hay porcentajes disponibles para el código " + codigoInspeccion);
        }

        // Actualizar los valores de muestreo con el porcentaje seleccionado
        actualizarMuestreoSegunPorcentaje();
    }

    // Determina el código de inspección según la cantidad del lote
    private String obtenerCodigoInspeccion(int cantidad) {
        // Ordenar las entradas de la tabla de inspección por tamaño de lote
        List<Map.Entry<Integer, String>> entries = new ArrayList<>(tablaInspeccion.entrySet());
        entries.sort(Map.Entry.comparingByKey());

        // Buscar la letra correspondiente
        for (Map.Entry<Integer, String> entry : entries) {
            if (cantidad <= entry.getKey()) {
                return entry.getValue();
            }
        }
        // Si la cantidad es mayor que el máximo en la tabla, retornar el último código
        return entries.get(entries.size() - 1).getValue(); // Retorna la letra correspondiente al tamaño máximo
    }

    /**
     * Permite al usuario seleccionar un porcentaje permitido para el código
     * actual, y actualiza los valores de muestreo, aceptación y rechazo según
     * la tabla dinámica.
     *
     * @param porcentaje
     */
    public void seleccionarPorcentajePermitido(double porcentaje) {
        // Verificar si el porcentaje es válido para el código de inspección actual
        double[] opciones = porcentajesPermitidos.get(codigoInspeccion);
        if (opciones == null || opciones.length == 0) {
            System.out.println("No existen opciones definidas para el código " + codigoInspeccion);
            return;
        }

        // Verificar si el porcentaje seleccionado es válido
        boolean esValido = false;
        for (double op : opciones) {
            if (op == porcentaje) {
                esValido = true;
                break;
            }
        }

        if (!esValido) {
            System.out.println("Porcentaje no válido.");
            return;
        }

        // Actualizar el porcentaje seleccionado
        porcentajeSeleccionado = porcentaje;
        System.out.println("Ha seleccionado el porcentaje: " + porcentajeSeleccionado + "%");
        actualizarMuestreoSegunPorcentaje();
    }

    /**
     * Actualiza la tabla de muestreo (cantidad a muestrear, aceptación y
     * rechazo) según el porcentaje seleccionado y el código de inspección.
     */
    private void actualizarMuestreoSegunPorcentaje() {
        Map<Double, int[]> tablaLetra = tablaMuestreoDinamica.get(codigoInspeccion);
        if (tablaLetra == null) {
            System.out.println("No se encontró una tabla dinámica para el código " + codigoInspeccion);
            return;
        }
        int[] valores = tablaLetra.get(porcentajeSeleccionado);
        if (valores == null) {
            System.out.println("No existen valores definidos para el porcentaje " + porcentajeSeleccionado + "% en el código " + codigoInspeccion);
            return;
        }
        this.cantidadUnidadesAMuestrear = valores[0];
        this.cantidadAceptacion = valores[1];
        this.cantidadRechazo = valores[2];
    }

    /**
     * Método auxiliar para inicializar los valores de muestreo con un
     * porcentaje por defecto.Por ejemplo, se puede elegir el valor máximo (o el
     * único) permitido para la letra.
     *
     * @param porcentajeSeleccionado
     */
    public void actualizarMuestreoConPorcentaje(double porcentajeSeleccionado) {
        // Verificar si el porcentaje es válido para el código de inspección actual
        double[] opciones = porcentajesPermitidos.get(codigoInspeccion);
        if (opciones != null && opciones.length > 0) {
            // Verificar si el porcentaje seleccionado es válido
            boolean esValido = false;
            for (double op : opciones) {
                if (op == porcentajeSeleccionado) {
                    esValido = true;
                    break;
                }
            }

            if (esValido) {
                this.porcentajeSeleccionado = porcentajeSeleccionado;
                actualizarMuestreoSegunPorcentaje(); // Actualizar los valores de aceptación y rechazo
            } else {
                System.out.println("Porcentaje no válido para el código de inspección " + codigoInspeccion);
            }
        }
    }

    public double[] obtenerPorcentajesPorLetra(String letra) {
        return porcentajesPermitidos.get(letra);
    }

    // Getters
    public int getCantidadUnidadesAMuestrear() {
        return cantidadUnidadesAMuestrear;
    }

    public int getCantidadAceptacion() {
        return cantidadAceptacion;
    }

    public int getCantidadRechazo() {
        return cantidadRechazo;
    }

    public String getCodigoInspeccion() {
        return codigoInspeccion;
    }

    public double getPorcentajeSeleccionado() {
        return porcentajeSeleccionado;
    }

    // Ejemplo de uso en el método main
    public static void main(String[] args) {
        // Se define un lote de ejemplo
        int cantidadLote = 100000;
        AQL_Inspector inspector = new AQL_Inspector(cantidadLote);

        System.out.println("Cantidad de unidades del lote: " + cantidadLote);
        System.out.println("Código de Inspección: " + inspector.getCodigoInspeccion());
        System.out.println("Cantidad a Muestrear (valor por defecto): " + inspector.getCantidadUnidadesAMuestrear());
        System.out.println("Límite de Aceptación (valor por defecto): " + inspector.getCantidadAceptacion());
        System.out.println("Límite de Rechazo (valor por defecto): " + inspector.getCantidadRechazo());

        // Permitir al usuario seleccionar el porcentaje permitido y actualizar los valores
        // Aquí puedes elegir un porcentaje válido, por ejemplo, el primero de la lista
        double[] opciones = porcentajesPermitidos.get(inspector.getCodigoInspeccion());
        if (opciones != null && opciones.length > 0) {
            inspector.seleccionarPorcentajePermitido(opciones[0]); // Seleccionar el primer porcentaje
        }

        System.out.println("\nValores actualizados según el porcentaje seleccionado:");
        System.out.println("Porcentaje Seleccionado: " + inspector.getPorcentajeSeleccionado() + "%");
        System.out.println("Cantidad a Muestrear: " + inspector.getCantidadUnidadesAMuestrear());
        System.out.println("Límite de Aceptación: " + inspector.getCantidadAceptacion());
        System.out.println("Límite de Rechazo: " + inspector.getCantidadRechazo());
    }
}
