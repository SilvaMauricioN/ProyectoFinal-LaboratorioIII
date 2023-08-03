package utn.frbb.tup.LaboratorioIII.business.implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.business.service.AlumnoService;
import utn.frbb.tup.LaboratorioIII.business.service.AsignaturaService;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.EstadoAsignatura;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDto;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadesNoAprobadasException;
import utn.frbb.tup.LaboratorioIII.model.exception.EstadoIncorrectoException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AlumnoDao;
import java.util.List;
import java.util.Random;

@Component
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoDao alumnoDao;
    private final AsignaturaService asignaturaService;
    @Autowired
    public AlumnoServiceImpl(AlumnoDao alumnoDao, AsignaturaService asignaturaService){
        this.alumnoDao = alumnoDao;
        this.asignaturaService = asignaturaService;
    }
    @Override
    public void aprobarAsignatura(int materiaId, int nota, long dni) throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException {
        Asignatura a = asignaturaService.getAsignatura(materiaId, dni);
        for (Materia m:
             a.getMateria().getListaCorrelatividades()) {
            Asignatura correlativa = asignaturaService.getAsignatura(m.getMateriaId(), dni);
            if (!EstadoAsignatura.APROBADA.equals(correlativa.getEstado())) {
                throw new CorrelatividadesNoAprobadasException("La materia " + m.getNombre() + " debe estar aprobada para aprobar " + a.getNombreAsignatura());
            }
        }
        a.aprobarAsignatura(nota);
        asignaturaService.actualizarAsignatura(a);
        Alumno alumno = alumnoDao.loadAlumno(dni);
        alumno.actualizarAsignatura(a);
        alumnoDao.saveAlumno(alumno);
    }
    @Override
    public Alumno crearAlumno(AlumnoDto alumno) {
        Alumno a = new Alumno();
        a.setNombre(alumno.getNombre());
        a.setApellido(alumno.getApellido());
        a.setDni(alumno.getDni());
        Random random = new Random();
        a.setId(random.nextInt(99) + 1);

        alumnoDao.saveAlumno(a);
        return a;
    }
    @Override
    public List<Alumno> buscarAlumno(String apellido) throws AlumnoNotFoundException {
        return alumnoDao.findAlumno(apellido);
    }
    public void hola(){}

}
