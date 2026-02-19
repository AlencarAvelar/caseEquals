package com.equals.caseequals.service;

import com.equals.caseequals.exception.FileProcessingException;
import com.equals.caseequals.model.Transaction;
import com.equals.caseequals.repository.TransactionRepository;
import com.equals.caseequals.service.parser.strategy.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //  retorna um 'int' com a quantidade de transações salvas
    public int processFile(InputStream fileStream) {
        int registrosSalvos = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Pula linhas em branco

                // Se processDetailLine retornar true, incrementa o contador
                if (processDetailLine(line)) {
                    registrosSalvos++;
                }
            }
        } catch (Exception e) {
            throw new FileProcessingException("Falha ao ler o arquivo: " + e.getMessage(), e);
        }


        if (registrosSalvos == 0) {
            throw new FileProcessingException("O arquivo foi lido, mas nenhum registro válido de transação foi encontrado. Verifique o layout.");
        }

        return registrosSalvos;
    }

    // Retorna boolean para sabermos se a linha virou transação ou não
    private boolean processDetailLine(String line) {
        try {
            // Regra CNAB: Se a linha for muito curta, pode ser Cabeçalho ou Rodapé. Ignoramos pacificamente.
            if (line.length() < 291) {
                return false;
            }

            // Extrai a bandeira (posições 262 a 291 no layout humano)
            String rawBrand = line.substring(261, 291).trim();

            PaymentStrategy strategy = strategies.stream()
                    .filter(s -> s.appliesTo(rawBrand))
                    .findFirst()
                    .orElse(null);

            // Se não encontrou a estratégia (ex: bandeira 'Xpto'), ignora a linha ou lança erro.
            //    ignorar a linha e não salvar.
            if (strategy == null) {
                return false;
            }

            Transaction transaction = strategy.processLine(line);
            repository.save(transaction);

            return true; // Sucesso!

        } catch (Exception e) {
            // Se a linha tem o tamanho certo, mas os dados estão ruins (ex: letras onde devia ter números), ele quebra e avisa.
            throw new FileProcessingException("Erro ao converter dados da linha. O layout não corresponde ao formato esperado.", e);
        }
    }
}