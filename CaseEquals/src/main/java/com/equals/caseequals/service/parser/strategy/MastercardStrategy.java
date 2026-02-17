package com.equals.caseequals.service.parser.strategy;

import com.equals.caseequals.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class MastercardStrategy implements PaymentStrategy {

    @Override
    public boolean appliesTo(String rawBrandName) {
        // Verifica se a bandeira é MASTERCARD (ignorando maiúsculas/minúsculas e espaços)
        return rawBrandName != null && rawBrandName.trim().equalsIgnoreCase("MASTERCARD");
    }

    @Override
    public Transaction processLine(String line) {
        Transaction transaction = new Transaction();

        try {
            // --- MAPEAMENTO BASEADO NO PDF (Index Java = Index PDF - 1) ---

            // 1. Estabelecimento (Pos 2, Tam 10) -> Java: 1 a 11
            transaction.setEstabelecimento(line.substring(1, 11).trim());

            // 2. Data Inicial da Transação (Pos 12, Tam 8) -> Java: 11 a 19
            String rawDataTransacao = line.substring(11, 19);
            transaction.setDataTransacao(LocalDate.parse(rawDataTransacao, DateTimeFormatter.ofPattern("yyyyMMdd")));

            // 3. Data do Evento (Pos 20, Tam 8) -> Java: 19 a 27
            String rawDataEvento = line.substring(19, 27);
            transaction.setDataEvento(LocalDate.parse(rawDataEvento, DateTimeFormatter.ofPattern("yyyyMMdd")));

            // 4. Hora do Evento (Pos 28, Tam 6) -> Java: 27 a 33
            String rawHora = line.substring(27, 33);
            transaction.setHoraEvento(LocalTime.parse(rawHora, DateTimeFormatter.ofPattern("HHmmss")));

            // 5. Valor Total (Pos 98, Tam 13) -> Java: 97 a 110
            // O valor vem como "0000000000100" (100 centavos = R$ 1.00)
            String rawValor = line.substring(97, 110);
            transaction.setValorTotal(new BigDecimal(rawValor).divide(new BigDecimal(100)));

            // 6. Bandeira (Pos 262, Tam 30) -> Java: 261 a 291
            transaction.setBandeira(line.substring(261, 291).trim());

            // 7. NSU (Pos 330, Tam 11) -> Java: 329 a 340
            transaction.setNsu(line.substring(329, 340).trim());

        } catch (Exception e) {
            // RuntimeException
            throw new IllegalArgumentException("Erro ao processar linha Mastercard: " + e.getMessage());
        }

        return transaction;
    }
}