package com.equals.caseequals.service;

import com.equals.caseequals.model.Transaction;
import com.equals.caseequals.repository.TransactionRepository;
import com.equals.caseequals.service.parser.strategy.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.equals.caseequals.exception.FileProcessingException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class FileProcessorService {

    @Autowired
    private List<PaymentStrategy> strategies;

    @Autowired
    private TransactionRepository repository;

    // O método principal que lê o arquivo
    public void processFile(InputStream fileStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream))) {
            String line;
            System.out.println("--- INICIANDO PROCESSAMENTO DO ARQUIVO ---");

            while ((line = reader.readLine()) != null) {
                // Pular linhas vazias
                if (line.trim().isEmpty()) continue;

                // Log para ver se está lendo
                // System.out.println("Lendo linha: " + line);

                // Tipo de Registro (Posição 1)
                char recordType = line.charAt(0);

                // Se for '1', é uma venda (Detalhe)
                if (recordType == '1') {
                    System.out.println("Processando venda (Tipo 1)...");
                    processDetailLine(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileProcessingException("Erro ao processar arquivo: " + e.getMessage(), e);
        }
    }


    private void processDetailLine(String line) {
        try {
            // 1. Identifica a bandeira (Posição 262 a 291 no arquivo -> 261 a 291 no Java)
            // Se o arquivo for menor que isso, vai dar erro, por isso o try/catch
            if (line.length() < 291) {
                throw new FileProcessingException("Linha inválida ou corrompida (tamanho incorreto).");
            }

            String rawBrand = line.substring(261, 291);
            System.out.println("Bandeira identificada na linha: [" + rawBrand.trim() + "]");

            // 2. Busca a estratégia correta (Visa ou Master)
            PaymentStrategy strategy = strategies.stream()
                    .filter(s -> s.appliesTo(rawBrand))
                    .findFirst()
                    .orElseThrow(() -> new FileProcessingException("Bandeira não suportada: " + rawBrand));

            // 3. Processa e Salva
            Transaction transaction = strategy.processLine(line);
            repository.save(transaction);

            System.out.println("Venda salva com sucesso! ID: " + transaction.getId());

        } catch (Exception e) {
            throw new FileProcessingException("Erro ao salvar linha: " + e.getMessage(), e);
        }
        }
    }
