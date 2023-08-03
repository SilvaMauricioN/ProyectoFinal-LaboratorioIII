package utn.frbb.tup.LaboratorioIII.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@JsonPropertyOrder({"nombre", "anio", "cuatrimestre", "profesor", "correlativas", "status"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MateriaDtoSalida implements Comparable<MateriaDtoSalida>{
    String nombre;
    Integer anio;
    Integer cuatrimestre;
    ProfesorDtoSalida profesor;
    List<MateriaDtoSalida> correlativas = new ArrayList<>();
    List<Map<String,String>> status = new ArrayList<>();

    public MateriaDtoSalida() {
    }
    public MateriaDtoSalida(String nombre, Integer year, Integer cuatrimestre) {
        this.nombre = nombre;
        this.anio = year;
        this.cuatrimestre = cuatrimestre;
    }
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

    public ProfesorDtoSalida getProfesor() {
        return profesor;
    }
    public void setProfesor(ProfesorDtoSalida profesor) {
        this.profesor = profesor;
    }
    public List<MateriaDtoSalida> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(List<MateriaDtoSalida> correlativas) {
        this.correlativas = correlativas;
    }
    public List<Map<String, String>> getStatus() {
        return status;
    }
    public void setStatus(List<Map<String, String>> status) {
        this.status = status;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MateriaDtoSalida materia = (MateriaDtoSalida) o;
        return this.anio == materia.anio && this.cuatrimestre == materia.cuatrimestre && Objects.equals(nombre, materia.nombre) && Objects.equals(correlativas, materia.correlativas);
    }
    @Override
    public int hashCode() {
        return Objects.hash(nombre, anio, cuatrimestre);
    }
    @Override
    public int compareTo(MateriaDtoSalida materiaSalida) {
        int valor = this.nombre.compareTo(materiaSalida.nombre);

        if(valor==0){
            valor = Integer.compare(this.anio, materiaSalida.anio);
        }
        return valor;
    }
    @Override
    public String toString() {
        return "MateriaDtoSalida{" +
                "nombre='" + nombre + '\'' +
                ", year=" + anio +
                ", cuatrimestre=" + cuatrimestre +
                ", Correlativas=" + correlativas +
                '}';
    }
}
