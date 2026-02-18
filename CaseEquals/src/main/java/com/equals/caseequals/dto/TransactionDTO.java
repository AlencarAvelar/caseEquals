package com.equals.caseequals.dto;

import com.equals.caseequals.model.Transaction;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;


public record TransactionDTO(
        Long id,
        String loja,
        String dataHora,
        String valor,
        String bandeira,
        String nsu
) {
    // converter da entidade para DTO
    public static TransactionDTO fromEntity(Transaction t) {
        // Formatadores
        DateTimeFormatter dataFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        return new TransactionDTO(
                t.getId(),
                t.getEstabelecimento(),
                t.getDataEvento().format(dataFmt) + " às " + t.getHoraEvento().format(horaFmt),
                "R$ " + t.getValorTotal().toString().replace(".", ","), // Formatação simples de moeda
                t.getBandeira(),
                t.getNsu()
        );
    }
}