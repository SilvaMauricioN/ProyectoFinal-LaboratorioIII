package utn.frbb.tup.LaboratorioIII.model;


import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "materiaId")
public class Materia implements Comparable<Materia> {
    private int materiaId;
    private String nombre;
    private int anio;
    private int cuatrimestre;
   // @JsonBackReference
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
    public Materia(String nombre, int anio, int cuatrimestre) {
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
    }
    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }

    public String getNombre() {
        return nombre.toUpperCase();
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
        if (profesor != null && !profesor.getMateriasDictadas().contains(this)) {
            profesor.setMateria(this);
        }
    }
    public List<Materia> getListaCorrelatividades() {
        return listaCorrelatividades;
    }

    public void setListaCorrelatividades(List<Materia> listaCorrelatividades) {
        this.listaCorrelatividades = listaCorrelatividades;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materia materia = (Materia) o;
        return anio == materia.anio && cuatrimestre == materia.cuatrimestre && Objects.equals(nombre, materia.nombre) && Objects.equals(profesor, materia.profesor) && Objects.equals(listaCorrelatividades, materia.listaCorrelatividades);
    }
    @Override
    public int hashCode() {
        return Objects.hash(materiaId, nombre, anio, cuatrimestre);
    }
    @Override
    public int compareTo(Materia materia){
        int valor = this.nombre.compareTo(materia.getNombre());

        if(valor==0){
            valor = Integer.compare(this.materiaId, materia.getMateriaId());
        }
        return valor;
    }
    @Override
    public String toString() {
        return "Materia{" +
                "materiaId=" + materiaId +
                ", nombre='" + nombre + '\'' +
                ", anio=" + anio +
                ", cuatrimestre=" + cuatrimestre +
                ", profesor=" + profesor +
                ", listaCorrelatividades=" + listaCorrelatividades +
                '}';
    }
}
