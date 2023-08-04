package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utn.frbb.tup.LaboratorioIII.business.service.AsignaturaService;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.EstadoAsignatura;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Nota;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadesNoAprobadasException;
import utn.frbb.tup.LaboratorioIII.model.exception.EstadoIncorrectoException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AsignaturaDao;

import java.util.List;

@Component
public class AsignaturaServiceImpl implements AsignaturaService {
    private final AsignaturaDao asignaturaDao;

    @Autowired
    public AsignaturaServiceImpl(AsignaturaDao asignaturaDao){
        this.asignaturaDao = asignaturaDao;
    }
    @Override
    public Asignatura getAsignatura(Integer idAlumno, Integer idAsignatura) throws AsignaturaInexistenteException {

        return asignaturaDao.findAsignatura(idAsignatura,idAsignatura);
    }
    public Asignatura aprobarAsignatura(Integer idAlumno, Integer idAsignatura, Nota nota) throws CorrelatividadesNoAprobadasException, EstadoIncorrectoException, AsignaturaInexistenteException {
        Asignatura asignatura = getAsignatura(idAlumno,idAsignatura);
        asignatura.cursarAsignatura();
        List<Materia> correlativas = asignatura.getMateria().getListaCorrelatividades();

        for(Materia m: correlativas){
            Asignatura correlativa = getAsignatura(idAlumno, m.getMateriaId());
            if (!EstadoAsignatura.APROBADA.equals(correlativa.getEstado())) {
                throw new CorrelatividadesNoAprobadasException("LA MATERIA " + m.getNombre() + " DEBE ESTAR CURSADA PARA APROBAR");
            }
        }
        asignatura.aprobarAsignatura(nota.nota());
        actualizarAsignatura(idAlumno, asignatura);
        return asignatura;
    }
    @Override
    public void actualizarAsignatura(Integer idAlumno, Asignatura asignatura){
        asignaturaDao.upDateAsignatura(idAlumno, asignatura);
    }
}
