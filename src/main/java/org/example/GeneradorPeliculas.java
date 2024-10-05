package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class GeneradorPeliculas {


    // Patrón para detectar caracteres no alfanuméricos pero permite letras numéricas, espacios, tildes y ñ
    private static final Pattern PATRON_INVALIDO = Pattern.compile("[^a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]");


        // Verificar que no haya campos vacíos
    public static boolean verificarHuecos(String archivoLectura) {
        boolean vacio = false;
            List<String> camposInvalidos = verificarCSV(archivoLectura);

            if (!camposInvalidos.isEmpty()) {
                System.out.println("Se encontraron campos vacios:");
                for (String campo : camposInvalidos) {
                    System.out.println(campo);
                    vacio = true;
                }
            } else {
                System.out.println("Todos los campos son válidos.");
            }
            return vacio;


        }

        // Verificar que no haya campos inválidos

        public static List<String> verificarCSV(String archivoLectura) {
            List<String> camposInvalidos = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(archivoLectura))) {
                String linea;
                int filaNum = 0;

                while ((linea = br.readLine()) != null) {
                    filaNum++;
                    String[] campos = linea.split(",");

                    for (int colNum = 0; colNum < campos.length; colNum++) {
                        String campo = campos[colNum].trim();

                        //Validar que el campo no esté vacío o contenga caracteres no alfanuméricos con la condición de arriba
                        // Considerar como vacío si el campo está vacío o solo contiene comas y lo muestra cual es
                        if (campo.isEmpty() || campo.equals(",")) {
                            camposInvalidos.add("Fila " + filaNum + ", Columna " + (colNum + 1) + ": (vacío)");
                        } else if (PATRON_INVALIDO.matcher(campo).find()) {
                            camposInvalidos.add("Fila " + filaNum + ", Columna " + (colNum + 1) + ": '" + campo + "'");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return camposInvalidos;
        }






    public static void generarPeliculas(String archivoLectura, String carpetaContenedora) {



        // Validar que el archivo no sea nulo
        File archivo = new File(archivoLectura);
        if (archivo == null)
            throw new IllegalArgumentException("El archivo no puede ser nulo");
        // Validar que el archivo exista
        if (!archivo.exists())
            throw new IllegalArgumentException("El archivo no existe");


        archivo = new File(carpetaContenedora);
        // Validar que la carpeta contenedora exista y si no la creamos
        if (!archivo.exists()) {
            archivo.mkdirs();
            System.out.println("El directorio se ha creado correctamente");
        } else {
            System.out.println("El directorio ya existe,esta siendo eliminado...");
            archivo.exists();
            System.out.println("El directorio se ha eliminado correctamente");
            System.out.println("Generando directorio de nuevo...");
            archivo.mkdirs();
            System.out.println("El directorio se vuelto a crear correctamente");

        }










        //Generar archivo html leyendo la plantilla

        String templateFile = "templates/template.html";


        try {
            // Leer la plantilla
            String template = new String(Files.readAllBytes(Paths.get(templateFile)));

            BufferedReader br = new BufferedReader(new FileReader(archivoLectura));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");


                // Generar HTML reemplazando los marcadores

                String html = template;

                html=html.replace("%%2%%", datos[1]) // Titulo
                        .replace("%%3%%", datos[2]) // Año
                        .replace("%%4%%", datos[3]) // Director
                        .replace("%%5%%", datos[4]); // Género



                String outputFileName = carpetaContenedora+"/"+ datos[1] +" - "+datos[0]+ ".html";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
                    writer.write(html);

                }
            }


        } catch (IOException e) {
            throw new RuntimeException("Error de entrada/salida", e);
        }
    }












    }










