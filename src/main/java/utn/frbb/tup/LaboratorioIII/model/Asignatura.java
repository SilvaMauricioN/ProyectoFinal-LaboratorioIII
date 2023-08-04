package utn.frbb.tup.LaboratorioIII.model;

import java.util.Optional;

public class Asignatura {
    private Materia materia;
    private EstadoAsignatura estado;
    private Integer nota;

    public Asignatura() {
    }
    public Asignatura(Materia materia) {
        this.materia = materia;
        this.estado = EstadoAsignatura.NO_CURSADA;
    }

    public Optional<Integer> getNota() {
        return Optional.ofNullable(nota);
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public EstadoAsignatura getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }

    public void aprobarAsignatura(int nota) {
    }
//    public String getNombreAsignatura(){
//        return this.materia.getNombre();
//    }
}
