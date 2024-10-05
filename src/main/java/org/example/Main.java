package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {



        GeneradorPeliculas peliculas = new GeneradorPeliculas();

        //He añadido esto ya que si no había que escribir muchas veces la ruta del archivo, igualmente los metodos deben recibir la ruta pasada por
        // el usuario , que es lo que se pedia, esto es una mejera */
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la ruta del archivo CSV: "+"  (peliculas.csv en este caso)");
        String ruta = sc.nextLine();

        //Antes de generar las peliculas se verifica que el archivo CSV no tenga fallos

        if (peliculas.verificarCSV(ruta)!=null && !peliculas.verificarHuecos(ruta)){
            peliculas.generarPeliculas(ruta, "salida/");
        }
        else {
            System.out.println("No se puede generar el archivo corrige el archivo CSV antes");
        }
            




    }
}