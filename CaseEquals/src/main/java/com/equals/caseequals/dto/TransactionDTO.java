package com.equals.caseequals.dto;

import com.equals.caseequals.model.Transaction;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public record TransactionDTO(
        // Identificação
        String id,
        String estabelecimento,
        String nsu,

        // Datas e Horas
        String dataTransacao,
        String dataEvento,
        String horaEvento,
        String dataPrevistaPagamento,

        // Valores (Dinheiro)
        String valorTotal,
        String valorLiquido,
        String valorOriginal,
        String valorLiquidoTransacao,

        // Taxas e Tarifas
        String taxaParcelamentoComprador,
        String tarifaBoletoComprador,
        String taxaParcelamentoVendedor,
        String taxaIntermediacao,
        String tarifaIntermediacao,
        String tarifaBoletoVendedor,
        String repasseAplicacao,

        // Detalhes da Venda
        String tipoEvento,
        String tipoTransacao,
        String numeroSerieLeitor,
        String codigoTransacao,
        String codigoPedido,

        // Parcelamento
        String indicadorPagamento,
        String plano,
        String parcela,
        String qtdParcelas,

        // Status e Captura
        String statusPagamento,
        String meioPagamento,
        String bandeira,
        String canalEntrada,
        String leitor,
        String meioCaptura,
        String numeroLogico,

        // Dados do Cartão
        String cartaoBin,
        String cartaoHolder,
        String codigoAutorizacao,
        String codigoCV
) {
    public static TransactionDTO fromEntity(Transaction t) {
        DateTimeFormatter dataFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        return new TransactionDTO(
                String.valueOf(t.getId()),
                t.getEstabelecimento(),
                t.getNsu(),

                // Datas (com check de null)
                t.getDataTransacao() != null ? t.getDataTransacao().format(dataFmt) : "",
                t.getDataEvento() != null ? t.getDataEvento().format(dataFmt) : "",
                t.getHoraEvento() != null ? t.getHoraEvento().format(horaFmt) : "",
                t.getDataPrevistaPagamento() != null ? t.getDataPrevistaPagamento().format(dataFmt) : "",

                // Valores
                formatMoney(t.getValorTotal()),
                formatMoney(t.getValorParcelaOuLiquido()),
                formatMoney(t.getValorOriginal()),
                formatMoney(t.getValorLiquidoTransacao()),

                // Taxas
                formatMoney(t.getTaxaParcelamentoComprador()),
                formatMoney(t.getTarifaBoletoComprador()),
                formatMoney(t.getTaxaParcelamentoVendedor()),
                formatMoney(t.getTaxaIntermediacao()),
                formatMoney(t.getTarifaIntermediacao()),
                formatMoney(t.getTarifaBoletoVendedor()),
                formatMoney(t.getRepasseAplicacao()),

                // Detalhes
                t.getTipoEvento(),
                t.getTipoTransacao(),
                t.getNumeroSerieLeitor(),
                t.getCodigoTransacao(),
                t.getCodigoPedido(),

                // Parcelamento
                t.getIndicadorPagamento(),
                t.getPlano(),
                t.getParcela(),
                String.valueOf(t.getQtdParcelas()),

                // Status
                t.getStatusPagamento(),
                t.getMeioPagamento(),
                t.getBandeira(),
                t.getCanalEntrada(),
                t.getLeitor(),
                t.getMeioCaptura(),
                t.getNumeroLogico(),

                // Cartão
                t.getCartaoBin(),
                t.getCartaoHolder(),
                t.getCodigoAutorizacao(),
                t.getCodigoCV()
        );
    }

    private static String formatMoney(BigDecimal value) {
        if (value == null) return "R$ 0,00";
        return "R$ " + value.toString().replace(".", ",");
    }
}