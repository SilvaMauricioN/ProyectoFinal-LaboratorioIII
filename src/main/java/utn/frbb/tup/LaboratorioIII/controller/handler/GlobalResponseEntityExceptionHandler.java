package utn.frbb.tup.LaboratorioIII.controller.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String mensajeError = "El cuerpo de la Solicitud es Inválido.";

        CustomApiError error = new CustomApiError();
        String uri = request.getContextPath();
        error.setErrorCode(status.value());
        error.setErrorMessage(mensajeError);
        error.setUri(uri);

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
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
