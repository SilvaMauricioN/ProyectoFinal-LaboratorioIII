package utn.frbb.tup.LaboratorioIII.model;

import java.util.ArrayList;
import java.util.List;

public class Materia {
    private int materiaId;
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private Profesor profesor;
    private List<Materia> listaCorrelatividades;
    public Materia(){}
    public Materia(String nombre, int anio, int cuatrimestre, Profesor profesor) {
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.nombre = nombre;
        this.profesor = profesor;
        listaCorrelatividades = new ArrayList<>();
    }

    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Materia> getListaCorrelatividades() {
        return listaCorrelatividades;
    }

    public void setListaCorrelatividades(List<Materia> listaCorrelatividades) {
        this.listaCorrelatividades = listaCorrelatividades;
    }
}
