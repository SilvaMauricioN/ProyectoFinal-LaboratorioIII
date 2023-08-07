package utn.frbb.tup.LaboratorioIII.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfesorDto {
    private String nombre;
    private String apellido;
    private String titulo;
    private Integer dni;
    private List<Integer> materiasDictadasID;
    public ProfesorDto(){}
    public ProfesorDto(String nombre, String apellido, String titulo, Integer dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulo = titulo;
        this.dni = dni;
        this.materiasDictadasID = new ArrayList<>();
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

    public List<Integer> getMateriasDictadasID() {
        return materiasDictadasID;
    }

    public void setMateriasDictadasID(List<Integer> materiasDictadasID) {
        this.materiasDictadasID = materiasDictadasID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfesorDto that = (ProfesorDto) o;
        return Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido) && Objects.equals(titulo, that.titulo) && Objects.equals(dni, that.dni) && Objects.equals(materiasDictadasID, that.materiasDictadasID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, titulo, dni, materiasDictadasID);
    }
}
