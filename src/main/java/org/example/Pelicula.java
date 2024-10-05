package org.example;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pelicula implements Serializable {
    private Integer id;
    private String titulo;
    private Integer año;
    private String director;
    private String genero;


    public String toCSV(){
        return id+","+titulo+","+año+","+director+","+genero;
    }

    public String toString(){
        return id+","+titulo+","+año+","+director+","+genero+"\n";
    }



}
