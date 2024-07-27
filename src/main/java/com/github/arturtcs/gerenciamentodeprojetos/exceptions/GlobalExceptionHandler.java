package com.github.arturtcs.gerenciamentodeprojetos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CpfInvalidoException.class)
    public ResponseEntity<String> handleCpfInvalidoException(CpfInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailInvalidoException.class)
    public ResponseEntity<String> handleEmailInvalidoException(EmailInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NomeObrigatorioException.class)
    public ResponseEntity<String> handleNomeObrigatorioException(NomeObrigatorioException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteComProjetosException.class)
    public ResponseEntity<String> handleClienteComProjetosException(ClienteComProjetosException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflitoAtributoException.class)
    public ResponseEntity<String> handleConflitoAtributoException(ConflitoAtributoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<String> handleRegraDeNegocioException(RegraDeNegocioException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StatusDoProjetoException.class)
    public ResponseEntity<String> handleStatusDoProjetoException(StatusDoProjetoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StatusDaAtividadeException.class)
    public ResponseEntity<String> handleStatusDaAtividadeException(StatusDaAtividadeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>("Informe um status válido.", HttpStatus.BAD_REQUEST);
    }
}