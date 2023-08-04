package utn.frbb.tup.LaboratorioIII.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AlumnoDtoSalida {
    String nombre;
    String apellido;
    Long dni;
    List<AsignaturaDtoSalida> asignaturas = new ArrayList<>();
    List<Map<String,String>> status = new ArrayList<>();

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
}
