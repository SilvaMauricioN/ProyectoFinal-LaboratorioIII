package utn.frbb.tup.LaboratorioIII.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import utn.frbb.tup.LaboratorioIII.model.EstadoAsignatura;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AsignaturaDtoSalida {
    private String nombre;
    private Integer anio;
    private Integer cuatrimestre;
    private String profesor;
    private List<String> correlativas = new ArrayList<>();
    private EstadoAsignatura estado;
    private Optional<Integer> nota = Optional.empty();
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getAnio() {
        return anio;
    }
    public void setAnio(Integer anio) {
        this.anio = anio;
    }
    public Integer getCuatrimestre() {
        return cuatrimestre;
    }
    public void setCuatrimestre(Integer cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }
    public String getProfesor() {
        return profesor;
    }
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
    public List<String> getCorrelativas() {
        return correlativas;
    }
    public void setCorrelativas(String correlativas) {
        if(correlativas != null){
            this.correlativas.add(correlativas);
        }
    }
    public EstadoAsignatura getEstado() {
        return estado;
    }
    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }
    public Optional<Integer> getNota() {
        return nota;
    }
    public void setNota(Optional<Integer> nota) {
        this.nota = nota;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsignaturaDtoSalida that = (AsignaturaDtoSalida) o;
        return Objects.equals(nombre, that.nombre) && Objects.equals(anio, that.anio) && Objects.equals(cuatrimestre, that.cuatrimestre) && Objects.equals(profesor, that.profesor) && Objects.equals(correlativas, that.correlativas) && estado == that.estado && Objects.equals(nota, that.nota);
    }
    @Override
    public int hashCode() {
        return Objects.hash(nombre, anio, cuatrimestre, profesor, correlativas, estado, nota);
    }
}
