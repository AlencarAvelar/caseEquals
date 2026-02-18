package com.equals.caseequals.exception;

// Estendemos RuntimeException para que o Java não nos obrigue a colocar try-catch em todo lugar
public class FileProcessingException extends RuntimeException {

    // Construtor que recebe a mensagem de erro (ex: "Bandeira não suportada")
    public FileProcessingException(String message) {
        super(message);
    }

    // Construtor que recebe a mensagem e a causa original (o erro técnico do Java)
    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}