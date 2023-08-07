package utn.frbb.tup.LaboratorioIII.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MateriaDto {
    private String nombre;
    private Integer year;
    private Integer cuatrimestre;
    private Integer profesorId;
    @JsonProperty("ListaCorrelatividades")
    private List<Integer> ListaCorrelatividades = new ArrayList<>();
    public MateriaDto(){}
    public MateriaDto(String nombre, Integer year, Integer cuatrimestre, Integer profesorId) {
        this.nombre = nombre;
        this.year = year;
        this.cuatrimestre = cuatrimestre;
        this.profesorId = profesorId;
    }
    public MateriaDto(String nombre, Integer year, Integer cuatrimestre) {
        this.nombre = nombre;
        this.year = year;
        this.cuatrimestre = cuatrimestre;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MateriaDto that = (MateriaDto) o;
        return Objects.equals(nombre, that.nombre) && Objects.equals(year, that.year) && Objects.equals(cuatrimestre, that.cuatrimestre) && Objects.equals(profesorId, that.profesorId) && Objects.equals(ListaCorrelatividades, that.ListaCorrelatividades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, year, cuatrimestre, profesorId, ListaCorrelatividades);
    }
}
