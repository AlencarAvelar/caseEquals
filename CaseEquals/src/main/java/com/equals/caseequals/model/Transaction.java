package com.equals.caseequals.model;

import jakarta.persistence.*;
import lombok.Data; // O Lombok gera Getters, Setters, toString, etc. automaticamente
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data // Gera getters, setters, equals, hashcode e toString
@NoArgsConstructor // Gera construtor vazio (obrigatório para JPA)
@AllArgsConstructor // Gera construtor com todos os argumentos
@Entity // Indica que essa classe é uma tabela no banco
@Table(name = "transacoes") // Define o nome da tabela no PostgreSQL
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Layout: Posição 2, Tam 10
    @Column(nullable = false, length = 10)
    private String estabelecimento;

    // Layout: Posição 12, Tam 8 (AAAAMMDD)
    @Column(name = "data_transacao", nullable = false)
    private LocalDate dataTransacao; // Mapeado de "Data inicial da transação"

    // Layout: Posição 20, Tam 8 (AAAAMMDD)
    @Column(name = "data_evento", nullable = false)
    private LocalDate dataEvento; // Mapeado de "Data do evento"

    // Layout: Posição 28, Tam 6 (HHMMSS)
    @Column(name = "hora_evento", nullable = false)
    private LocalTime horaEvento;

    // Layout: Posição 98, Tam 13
    // Usamos BigDecimal para evitar erros de arredondamento financeiro
    @Column(name = "valor_total", nullable = false, precision = 13, scale = 2)
    private BigDecimal valorTotal;

    // Layout: Posição 262, Tam 30
    @Column(nullable = false, length = 30)
    private String bandeira;

    // Layout: Posição 330, Tam 11
    // NSU = Número Sequencial Único
    @Column(length = 11)
    private String nsu;
}