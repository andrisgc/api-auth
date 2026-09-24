package com.agc.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura erros de regra de negócio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException e) {
        ErrorResponseDTO error = new ErrorResponseDTO(e.getMessage(), 400);
        return ResponseEntity.badRequest().body(error);
    }

    // Captura erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorDTO>> handleValidationErrors(MethodArgumentNotValidException e) {
        // Mapeia a lista de erros
        List<FieldErrorDTO> error = e.getFieldErrors().stream()
                .map(err -> new FieldErrorDTO(err.getField(), err.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(error);
    }

    // Captura erros de permissão de cargo
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAcessDenied(AccessDeniedException e) {
        ErrorResponseDTO error = new ErrorResponseDTO("Acesso negado: Você não possui privilégios suficientes.", 403);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

}
