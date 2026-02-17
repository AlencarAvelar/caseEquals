package com.equals.caseequals.service.parser.strategy;

import com.equals.caseequals.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class VisaStrategy implements PaymentStrategy {

    @Override
    public boolean appliesTo(String rawBrandName) {
        // A string no arquivo pode vir cheia de espaços ex: "VISA                          "
        return rawBrandName != null && rawBrandName.trim().equalsIgnoreCase("VISA");
    }

    @Override
    public Transaction processLine(String line) {
        // A lógica de layout para VISA neste arquivo é a mesma do Mastercard.
        // Se fosse diferente, mudaria os índices do substring.

        Transaction transaction = new Transaction();
        try {
            transaction.setEstabelecimento(line.substring(1, 11).trim());

            transaction.setDataTransacao(LocalDate.parse(
                    line.substring(11, 19), DateTimeFormatter.ofPattern("yyyyMMdd")));

            transaction.setDataEvento(LocalDate.parse(
                    line.substring(19, 27), DateTimeFormatter.ofPattern("yyyyMMdd")));

            transaction.setHoraEvento(LocalTime.parse(
                    line.substring(27, 33), DateTimeFormatter.ofPattern("HHmmss")));

            BigDecimal valor = new BigDecimal(line.substring(97, 110));
            transaction.setValorTotal(valor.divide(new BigDecimal(100)));

            transaction.setBandeira(line.substring(261, 291).trim());

            transaction.setNsu(line.substring(329, 340).trim());

        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao processar linha Visa: " + e.getMessage());
        }

        return transaction;
    }
}