package com.equals.caseequals.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice // avisa o spring que aqui ocorre o gerenciamento de erros
public class GlobalExceptionHandler {

    // 1. Captura nossa exceção personalizada
    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<String> handleFileError(FileProcessingException ex) {
        // Retorna HTTP 400 (Bad Request) com a  mensagem explicativa
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Erro de Processamento: " + ex.getMessage());
    }

    // 2. Captura erro de arquivo muito grande
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeError(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("O arquivo é muito grande! O limite máximo é 10MB.");
    }

    // 3. Captura qualquer outro erro genérico (NullPointer, Banco fora, etc)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericError(Exception ex) {
        // Retorna HTTP 500 (Internal Server Error)
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro interno inesperado: " + ex.getMessage());
    }
}