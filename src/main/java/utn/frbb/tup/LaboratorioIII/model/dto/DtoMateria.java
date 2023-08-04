package utn.frbb.tup.LaboratorioIII.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DtoMateria {
    private String nombre;
    private Integer year;
    private Integer cuatrimestre;
    private Integer profesorId;
    @JsonProperty("ListaCorrelatividades")
    private List<Integer> ListaCorrelatividades = new ArrayList<>();
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
    public Integer getProfesorId() {
        return profesorId;
    }
    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
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
