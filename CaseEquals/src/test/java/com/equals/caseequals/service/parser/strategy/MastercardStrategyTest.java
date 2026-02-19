package com.equals.caseequals.service.parser.strategy;

import com.equals.caseequals.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MastercardStrategyTest {

    private MastercardStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new MastercardStrategy();
    }

    @Test
    void deveAplicarApenasParaMastercard() {
        assertTrue(strategy.appliesTo("MASTERCARD"));
        assertTrue(strategy.appliesTo("mastercard ")); // Deve ignorar espaços e maiúsculas
        assertFalse(strategy.appliesTo("VISA"));
        assertFalse(strategy.appliesTo(null));
    }

    @Test
    void deveProcessarLinhaCorretamente() {
        // Montando uma linha fake (mock) com 391 caracteres preenchidos com espaços
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 391; i++) {
            sb.append(" ");
        }

        // Injetando dados nas posições exatas (Lembrando que substring(0, 10) pega do index 0 ao 9)
        // Posição 2 a 11 (Estabelecimento) - Index 1 a 11 no Java
        sb.replace(1, 11, "LOJA TESTE");

        // Posição 12 a 19 (Data Transação) - Index 11 a 19 no Java
        sb.replace(11, 19, "20180925");

        // Posição 98 a 110 (Valor Total) - Index 97 a 110 no Java
        sb.replace(97, 110, "0000000015050"); // R$ 150,50

        // Posição 262 a 291 (Bandeira) - Index 261 a 291 no Java
        sb.replace(261, 271, "MASTERCARD"); // Ocupa 10 posições, o resto é espaço

        String linhaMock = sb.toString();

        // Ação: Processar a linha
        Transaction transaction = strategy.processLine(linhaMock);

        // Verificações (Asserts)
        assertNotNull(transaction);
        assertEquals("LOJA TESTE", transaction.getEstabelecimento());
        assertEquals(LocalDate.of(2018, 9, 25), transaction.getDataTransacao());
        assertEquals(0, new BigDecimal("150.50").compareTo(transaction.getValorTotal()));
        assertEquals("MASTERCARD", transaction.getBandeira());
    }
}