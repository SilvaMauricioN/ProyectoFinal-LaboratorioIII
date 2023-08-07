package utn.frbb.tup.LaboratorioIII.business.implement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.EstadoAsignatura;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Nota;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadesNoAprobadasException;
import utn.frbb.tup.LaboratorioIII.model.exception.EstadoIncorrectoException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AsignaturaDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsignaturaServiceImplTest {
    @Mock
    private AsignaturaDao asignaturaDao;
    @InjectMocks
    private AsignaturaServiceImpl asignaturaServiceImpl;

    @Test
    void getAsignatura() throws AsignaturaInexistenteException {
        Materia materia = new Materia("Ingles",1,1);
        materia.setMateriaId(1);
        Asignatura asignatura = new Asignatura(materia);

        when(asignaturaDao.findAsignatura(eq(1),eq(2))).thenReturn(asignatura);

        Asignatura asignaturaObtenida = asignaturaServiceImpl.getAsignatura(1,2);

        verify(asignaturaDao,times(1)).findAsignatura(eq(1),eq(2));
        assertEquals(asignatura,asignaturaObtenida);
    }
    @Test
    void aprobarAsignatura() throws AsignaturaInexistenteException, CorrelatividadesNoAprobadasException, EstadoIncorrectoException {
        Materia materia = new Materia("Ingles",1,1);
        Asignatura asignatura = new Asignatura(materia);
        Nota nota = new Nota(7, EstadoAsignatura.CURSADA);
        when(asignaturaDao.findAsignatura(eq(1),eq(2))).thenReturn(asignatura);



        Asignatura aObtenida = asignaturaServiceImpl.aprobarAsignatura(1,2,nota);
        Asignatura aEsperada = new Asignatura(materia);
        aEsperada.setNota(7);
        aEsperada.setEstado(EstadoAsignatura.APROBADA);

        assertEquals(aEsperada,aObtenida);
    }
    @Test
    void aprobarAsignaturaSinCorrelativas() throws AsignaturaInexistenteException {
        Materia mC = new Materia("Ingles I",1,1);
        mC.setMateriaId(3);
        Asignatura asignaturaCorrelativa = new Asignatura(mC);
        List<Materia> correlativas = new ArrayList<>(){{
            add(mC);
        }};
        Materia materia = new Materia("Ingles II",1,2);
        Nota nota = new Nota(7,EstadoAsignatura.CURSADA);
        materia.setListaCorrelatividades(correlativas);
        Asignatura asignatura = new Asignatura(materia);

        when(asignaturaDao.findAsignatura(eq(1),eq(2))).thenReturn(asignatura);
        when(asignaturaDao.findAsignatura(eq(1),eq(3))).thenReturn(asignaturaCorrelativa);

        Exception exception = assertThrows(CorrelatividadesNoAprobadasException.class, () -> {
            asignaturaServiceImpl.aprobarAsignatura(1,2,nota);
        });

        String MensajeEsperado = "LA MATERIA Ingles I DEBE ESTAR CURSADA PARA APROBAR";
        String MensajeObtenido = exception.getMessage();

        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
}