package utn.frbb.tup.LaboratorioIII.controller.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import utn.frbb.tup.LaboratorioIII.model.exception.*;

@ControllerAdvice
public class UtnResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class,ProfesorException.class,
            CorrelatividadException.class,Exception.class, EstadoIncorrectoException.class,
            CorrelatividadesNoAprobadasException.class, MethodArgumentTypeMismatchException.class, AsignaturaInexistenteException.class})
    protected ResponseEntity<Object> handleConflict(Exception exception, WebRequest request) {

        HttpStatus status;
        String mensajeError = exception.getMessage();

        if(exception instanceof MateriaNotFoundException || exception instanceof AlumnoNotFoundException || exception instanceof AsignaturaInexistenteException){
            status = HttpStatus.NOT_FOUND;
        }else if(exception instanceof IllegalArgumentException ||
                exception instanceof ProfesorException ||
                exception instanceof CorrelatividadException ||
                exception instanceof EstadoIncorrectoException ||
                exception instanceof CorrelatividadesNoAprobadasException){

            status = HttpStatus.BAD_REQUEST;
        }else if( exception instanceof MethodArgumentTypeMismatchException){
            status = HttpStatus.BAD_REQUEST;

            String mensaje = "ARGUMENTO INVÁLIDO " +  ((MethodArgumentTypeMismatchException) exception).getValue();
            CustomApiError error = new CustomApiError();
            error.setErrorCode(status.value());
            error.setErrorMessage(mensaje);
            return handleExceptionInternal(exception, error, new HttpHeaders(), status, request);

        }else{
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        CustomApiError error = new CustomApiError();
        String uri = request.getContextPath();

        error.setErrorCode(status.value());
        error.setErrorMessage(mensajeError);
        error.setUri(uri);

        return handleExceptionInternal(exception, error, new HttpHeaders(), status, request);
    }
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            CustomApiError error = new CustomApiError();
            error.setErrorMessage(ex.getMessage());
            error.setUri(request.getContextPath());
            body = error;
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
