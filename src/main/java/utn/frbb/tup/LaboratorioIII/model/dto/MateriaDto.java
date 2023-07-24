package utn.frbb.tup.LaboratorioIII.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import utn.frbb.tup.LaboratorioIII.model.Materia;

import java.util.ArrayList;
import java.util.List;

public class MateriaDto {
    private int materiaId;
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private int profesorDni;
    @JsonProperty("ListaCorrelatividades")
    private List<Integer> ListaCorrelatividades = new ArrayList<>();

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

    public int getProfesorDni() {
        return profesorDni;
    }

    public void setProfesorDni(int profesorDni) {
        this.profesorDni = profesorDni;
    }

    public List<Integer> getListaCorrelatividades() {
        return ListaCorrelatividades;
    }

    @JsonProperty
    public void setListaCorrelatividades(List<Integer> listaCorrelatividades) {
        if (listaCorrelatividades == null) {
            this.ListaCorrelatividades = new ArrayList<>();
        } else {
            this.ListaCorrelatividades = listaCorrelatividades;
        }
    }
}
