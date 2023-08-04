package utn.frbb.tup.LaboratorioIII.business.implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.business.service.AlumnoService;
import utn.frbb.tup.LaboratorioIII.business.service.AsignaturaService;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.EstadoAsignatura;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.*;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadesNoAprobadasException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AlumnoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoDao alumnoDao;
    private final AsignaturaService asignaturaService;
//    private final MateriaService materiaService;
    private final CastingDtos castingDtos;
    @Autowired
    public AlumnoServiceImpl(AlumnoDao alumnoDao, AsignaturaService asignaturaService, CastingDtos castingDtos){
        this.alumnoDao = alumnoDao;
        this.asignaturaService = asignaturaService;
//        this.materiaService = materiaService;
        this.castingDtos = castingDtos;
    }
    @Override
    public void aprobarAsignatura(int materiaId, int nota, long dni) throws CorrelatividadesNoAprobadasException, AlumnoNotFoundException {
        Asignatura a = asignaturaService.getAsignatura(materiaId, dni);
        for (Materia m:
             a.getMateria().getListaCorrelatividades()) {
            Asignatura correlativa = asignaturaService.getAsignatura(m.getMateriaId(), dni);
            if (!EstadoAsignatura.APROBADA.equals(correlativa.getEstado())) {
                throw new CorrelatividadesNoAprobadasException("La materia " + m.getNombre() + " debe estar aprobada para aprobar " );
            }
        }
        a.aprobarAsignatura(nota);
        asignaturaService.actualizarAsignatura(a);
//        Alumno alumno = alumnoDao.findAlumno(alumno.getId());
//        alumno.actualizarAsignatura(a);
//        alumnoDao.saveAlumno(alumno);
    }
    @Override
    public AlumnoDtoSalida crearAlumno(AlumnoDto dtoAlumno) throws AsignaturaInexistenteException, AlumnoNotFoundException {
        Alumno alumno = new Alumno();
        List<Map<String, String>> errores = castingDtos.aAlumnoDto(alumno, dtoAlumno);
        alumnoDao.saveAlumno(alumno);

        AlumnoDtoSalida alumnoDtoSalida = castingDtos.aAlumnoDtoSalida(alumno);
        alumnoDtoSalida.setStatus(errores);

        return alumnoDtoSalida;
    }
    @Override
    public List<Alumno> buscarAlumno(String apellido) throws AlumnoNotFoundException {
        return alumnoDao.findAlumno(apellido);
    }

    @Override
    public AlumnoDtoSalida actualizarMateria(Integer id, AlumnoDto alumnoDto) throws AsignaturaInexistenteException, AlumnoNotFoundException {
        Alumno alumno = alumnoDao.findAlumno(id);

        List<Map<String,String>> posiblesErrores = castingDtos.aAlumnoDto(alumno,alumnoDto);

        alumnoDao.upDateAlumno(alumno);

        AlumnoDtoSalida alumnoDtoSalida = castingDtos.aAlumnoDtoSalida(alumno);
        alumnoDtoSalida.setStatus(posiblesErrores);

        return alumnoDtoSalida;
    }

    @Override
    public void deleteAlumno(Integer idAlumno) throws AlumnoNotFoundException {
        alumnoDao.findAlumno(idAlumno);
        alumnoDao.deleteAlumno(idAlumno);
    }


}
