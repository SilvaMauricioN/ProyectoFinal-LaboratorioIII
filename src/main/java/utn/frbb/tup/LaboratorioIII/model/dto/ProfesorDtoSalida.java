package utn.frbb.tup.LaboratorioIII.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@JsonPropertyOrder({"nombre", "apellido", "titulo", "dni", "materias", "status"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProfesorDtoSalida{
    String nombre;
    String apellido;
    String titulo;
    Integer dni;
    List<MateriaDtoSalida> materias = new ArrayList<>();
    List<Map<String,String>> status = new ArrayList<>();
    public ProfesorDtoSalida(){}
    public ProfesorDtoSalida(String nombre, String apellido, String titulo, Integer dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulo = titulo;
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
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getDni() {
        return dni;
    }
    public void setDni(Integer dni) {
        this.dni = dni;
    }
    public List<MateriaDtoSalida> getMaterias() {
        return materias;
    }
    public void setMaterias(List<MateriaDtoSalida> materias) {
        this.materias = materias;
    }
    public List<Map<String, String>> getStatus() {
        return status;
    }
    public void setStatus(List<Map<String, String>> status) {
        this.status = status;
    }
}


