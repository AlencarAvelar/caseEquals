package com.equals.caseequals.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "transacoes", indexes = {
        @Index(name = "idx_data_prevista", columnList = "dataPrevistaPagamento"),
        @Index(name = "idx_bandeira", columnList = "bandeira")
})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Identificação ---
    private String estabelecimento; // Pos 2-11
    private String nsu;             // Pos 330-340

    // --- Datas e Horas ---
    private LocalDate dataTransacao;      // Pos 12-19
    private LocalDate dataEvento;         // Pos 20-27
    private LocalTime horaEvento;         // Pos 28-33
    private LocalDate dataPrevistaPagamento; // Pos 131-138

    // --- Valores (Dinheiro) ---
    private BigDecimal valorTotal;              // Pos 98-110
    private BigDecimal valorParcelaOuLiquido;   // Pos 111-123
    private BigDecimal valorOriginal;           // Pos 165-177
    private BigDecimal valorLiquidoTransacao;   // Pos 243-255

    // --- Taxas e Tarifas ---
    private BigDecimal taxaParcelamentoComprador; // Pos 139-151
    private BigDecimal tarifaBoletoComprador;     // Pos 152-164
    private BigDecimal taxaParcelamentoVendedor;  // Pos 178-190
    private BigDecimal taxaIntermediacao;         // Pos 191-203
    private BigDecimal tarifaIntermediacao;       // Pos 204-216
    private BigDecimal tarifaBoletoVendedor;      // Pos 217-229
    private BigDecimal repasseAplicacao;          // Pos 230-242

    // --- Detalhes da Venda ---
    private String tipoEvento;      // Pos 34-35
    private String tipoTransacao;   // Pos 36-37
    private String numeroSerieLeitor; // Pos 38-45
    private String codigoTransacao;   // Pos 46-77
    private String codigoPedido;      // Pos 78-97

    // --- Parcelamento ---
    private String indicadorPagamento; // Pos 124
    private String plano;              // Pos 125-126
    private String parcela;            // Pos 127-128
    private Integer qtdParcelas;       // Pos 129-130

    // --- Status e Captura ---
    private String statusPagamento; // Pos 256-257
    private String meioPagamento;   // Pos 260-261
    private String bandeira;        // Pos 262-291
    private String canalEntrada;    // Pos 292-293
    private String leitor;          // Pos 294-295
    private String meioCaptura;     // Pos 296-297
    private String numeroLogico;    // Pos 298-329

    // --- Dados do Cartão ---
    private String cartaoBin;       // Pos 344-349
    private String cartaoHolder;    // Pos 350-353
    private String codigoAutorizacao; // Pos 354-359
    private String codigoCV;        // Pos 360-391
}