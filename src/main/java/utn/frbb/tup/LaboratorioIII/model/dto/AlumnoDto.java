package utn.frbb.tup.LaboratorioIII.model.dto;

import java.util.ArrayList;
import java.util.List;

public class AlumnoDto {
    String nombre;
    String apellido;
    Long dni;
    List<Integer> asignaturasId = new ArrayList<>();

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

    public void setDni(long dni) {
        this.dni = dni;
    }
    public List<Integer> getAsignaturasId() {
        return asignaturasId;
    }
    public void setAsignaturasId(List<Integer> asignaturasId) {
        this.asignaturasId = asignaturasId;
    }

}
