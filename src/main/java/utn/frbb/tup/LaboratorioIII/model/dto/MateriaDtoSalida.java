package utn.frbb.tup.LaboratorioIII.model.dto;

import utn.frbb.tup.LaboratorioIII.model.Materia;

import java.util.ArrayList;
import java.util.List;

public class MateriaDtoSalida implements Comparable<MateriaDtoSalida>{
    String nombre;
    Integer year;
    Integer cuatrimestre;
    List<MateriaDtoSalida> Correlativas;

    public MateriaDtoSalida() {
    }

    public MateriaDtoSalida(String nombre, Integer year, Integer cuatrimestre) {
        this.nombre = nombre;
        this.year = year;
        this.cuatrimestre = cuatrimestre;
        Correlativas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(Integer cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public List<MateriaDtoSalida> getCorrelativas() {
        return Correlativas;
    }

    public void setCorrelativas(List<MateriaDtoSalida> correlativas) {
        Correlativas = correlativas;
    }

    @Override
    public int compareTo(MateriaDtoSalida materiaSalida) {
        int valor = this.nombre.compareTo(materiaSalida.nombre);

        if(valor==0){
            valor = Integer.compare(this.year, materiaSalida.year);
        }
        return valor;
    }
}
