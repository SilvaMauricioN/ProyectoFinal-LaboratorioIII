package utn.frbb.tup.LaboratorioIII.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AlumnoDtoSalida {
    String nombre;
    String apellido;
    Long dni;
    List<AsignaturaDtoSalida> asignaturas = new ArrayList<>();
    List<Map<String,String>> status = new ArrayList<>();
    public AlumnoDtoSalida(){}

    public AlumnoDtoSalida(String nombre, String apellido, Long dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public Long getDni() {
        return dni;
    }
    public void setDni(Long dni) {
        this.dni = dni;
    }
    public List<AsignaturaDtoSalida> getAsignaturas() {
        return asignaturas;
    }
    public void setAsignaturas(List<AsignaturaDtoSalida> asignaturas) {
        this.asignaturas = asignaturas;
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
        AlumnoDtoSalida that = (AlumnoDtoSalida) o;
        return Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido) && Objects.equals(dni, that.dni) && Objects.equals(asignaturas, that.asignaturas) && Objects.equals(status, that.status);
    }
    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, dni, asignaturas, status);
    }

    @Override
    public String toString() {
        return "AlumnoDtoSalida{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                '}';
    }
}
