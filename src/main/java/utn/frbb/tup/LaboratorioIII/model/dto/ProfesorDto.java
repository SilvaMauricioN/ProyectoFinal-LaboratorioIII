package utn.frbb.tup.LaboratorioIII.model.dto;

import java.util.List;

public class ProfesorDto {
    private String nombre;
    private String apellido;
    private String titulo;
    private Integer dni;
    private List<Integer> materiasDictadasID;

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

    public List<Integer> getMateriasDictadasID() {
        return materiasDictadasID;
    }

    public void setMateriasDictadasID(List<Integer> materiasDictadasID) {
        this.materiasDictadasID = materiasDictadasID;
    }
}
