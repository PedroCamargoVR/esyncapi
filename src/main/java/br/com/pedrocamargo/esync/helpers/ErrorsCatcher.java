package br.com.pedrocamargo.esync.helpers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorsCatcher {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404(){
        return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Objeto não encontrado."),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity erroValidacao(MethodArgumentNotValidException ex){
        List<FieldError> fieldErros = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(fieldErros.stream().map(ValidationData::new).toList());
    }

    private record ValidationData(String field, String message){
        public ValidationData(FieldError er){
            this(er.getField(), er.getDefaultMessage());
        }
    }
}
