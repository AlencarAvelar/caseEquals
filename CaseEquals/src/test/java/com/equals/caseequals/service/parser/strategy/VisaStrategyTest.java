package com.equals.caseequals.service.parser.strategy;

import com.equals.caseequals.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VisaStrategyTest {

    private VisaStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new VisaStrategy();
    }

    @Test
    void deveAplicarApenasParaVisa() {
        // Verifica se a estratégia aceita a string correta, ignorando espaços e maiúsculas/minúsculas
        assertTrue(strategy.appliesTo("VISA"));
        assertTrue(strategy.appliesTo("visa   "));

        // Verifica se rejeita outras bandeiras e valores nulos
        assertFalse(strategy.appliesTo("MASTERCARD"));
        assertFalse(strategy.appliesTo(null));
        assertFalse(strategy.appliesTo(""));
    }

    @Test
    void deveProcessarLinhaCorretamente() {
        // Montando uma linha simulada (mock) com 391 caracteres preenchidos com espaços em branco
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 391; i++) {
            sb.append(" ");
        }

        // Injetando dados de teste nas posições exatas do layout
        // Posição 2 a 11 (Estabelecimento) - Index 1 a 11 no Java
        sb.replace(1, 11, "LOJA VISA ");

        // Posição 12 a 19 (Data Transação) - Index 11 a 19 no Java
        sb.replace(11, 19, "20180926");

        // Posição 98 a 110 (Valor Total) - Index 97 a 110 no Java
        sb.replace(97, 110, "0000000025075"); // R$ 250,75

        // Posição 262 a 291 (Bandeira) - Index 261 a 291 no Java
        sb.replace(261, 271, "VISA      "); // Ocupa 10 posições, o resto é espaço

        String linhaMock = sb.toString();

        // Ação: Processar a linha
        Transaction transaction = strategy.processLine(linhaMock);

        // Verificações (Asserções)
        assertNotNull(transaction);
        assertEquals("LOJA VISA", transaction.getEstabelecimento());
        assertEquals(LocalDate.of(2018, 9, 26), transaction.getDataTransacao());
        assertEquals(0, new BigDecimal("250.75").compareTo(transaction.getValorTotal()));
        assertEquals("VISA", transaction.getBandeira());
    }
}