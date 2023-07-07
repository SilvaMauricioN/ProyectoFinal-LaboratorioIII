package utn.frbb.tup.LaboratorioIII.model;

import java.util.List;

public class Profesor {
    private final String nombre;
    private final String apellido;
    private final String titulo;
    private final int dni;
    private List<Materia> materiasDictadas;
    public Profesor(String nombre, String apellido, String titulo, int dni) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.titulo = titulo;
        this.dni = dni;
    }
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDni() {
        return dni;
    }

    public List<Materia> getMateriasDictadas() {
        return materiasDictadas;
    }

    public void setMateriasDictadas(List<Materia> materiasDictadas) {
        this.materiasDictadas = materiasDictadas;
    }
}
