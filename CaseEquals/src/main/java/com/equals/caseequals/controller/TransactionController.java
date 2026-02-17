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
        try {
            fileProcessorService.processFile(file.getInputStream());
            return ResponseEntity.ok("Arquivo processado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar arquivo: " + e.getMessage());
        }
    }

    // Endpoint de Listagem com Filtro
    // ATENÇÃO: Veja onde o @RequestParam está posicionado (dentro dos parênteses)
    @GetMapping
    public List<TransactionDTO> listAll(
            @RequestParam(required = false) LocalDate inicio,
            @RequestParam(required = false) LocalDate fim) {

        List<Transaction> transacoesDoBanco;

        // 1. Busca no Banco (Vem Entidade)
        if (inicio != null && fim != null) {
            transacoesDoBanco = repository.findByDataEventoBetween(inicio, fim);
        } else {
            transacoesDoBanco = repository.findAll();
        }

        // 2. Converte para DTO (Vem JSON formatado)
        return transacoesDoBanco.stream()
                .map(TransactionDTO::fromEntity) // Chama o conversor que criamos
                .collect(Collectors.toList());
    }
}