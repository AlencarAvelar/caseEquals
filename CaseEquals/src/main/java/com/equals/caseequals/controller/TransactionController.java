package com.equals.caseequals.controller;

import com.equals.caseequals.model.Transaction;
import com.equals.caseequals.repository.TransactionRepository;
import com.equals.caseequals.service.FileProcessorService;
import com.equals.caseequals.dto.TransactionDTO;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*") // Permite acesso do Front-end (HTML)
public class TransactionController {

    @Autowired
    private FileProcessorService fileProcessorService;

    @Autowired
    private TransactionRepository repository;

    // Endpoint de Upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // Se der erro, o GlobalExceptionHandler pega automaticamente.
        try {
            fileProcessorService.processFile(file.getInputStream());
            return ResponseEntity.ok("Arquivo processado com sucesso!");
        } catch (java.io.IOException e) {
            // converte erro de IO para nossa exceção
            throw new com.equals.caseequals.exception.FileProcessingException("Erro de leitura do arquivo upload", e);
        }
    }

    // Endpoint de Listagem com Filtro

    @GetMapping
    public List<TransactionDTO> listAll(
            @RequestParam(required = false) LocalDate inicio,
            @RequestParam(required = false) LocalDate fim,
            @RequestParam(required = false) String bandeira) { // <--- Novo parâmetro

        // Chama o método inteligente do repositório
        // Se a bandeira vier vazia, o SQL vai ignorar ela
        List<Transaction> transactions = repository.findByFilters(inicio, fim, bandeira);

        // Converte para DTO e retorna
        return transactions.stream()
                .map(TransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }
}