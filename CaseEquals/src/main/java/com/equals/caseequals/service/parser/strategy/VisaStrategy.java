package com.equals.caseequals.service.parser.strategy;

import com.equals.caseequals.model.Transaction;
import com.equals.caseequals.utils.ParserUtils; // <--- Importante!
import org.springframework.stereotype.Component;

@Component
public class VisaStrategy implements PaymentStrategy {

    @Override
    public boolean appliesTo(String rawBrandName) {
        return rawBrandName != null && rawBrandName.trim().equalsIgnoreCase("VISA");
    }

    @Override
    public Transaction processLine(String line) {
        Transaction t = new Transaction();

        // --- Bloco 1: Início ---
        t.setEstabelecimento(line.substring(1, 11).trim());
        t.setDataTransacao(ParserUtils.parseDate(line.substring(11, 19)));
        t.setDataEvento(ParserUtils.parseDate(line.substring(19, 27)));
        t.setHoraEvento(ParserUtils.parseTime(line.substring(27, 33)));

        t.setTipoEvento(line.substring(33, 35));
        t.setTipoTransacao(line.substring(35, 37));
        t.setNumeroSerieLeitor(line.substring(37, 45).trim());
        t.setCodigoTransacao(line.substring(45, 77).trim());
        t.setCodigoPedido(line.substring(77, 97).trim());

        // --- Bloco 2: Valores Principais ---
        t.setValorTotal(ParserUtils.parseCurrency(line.substring(97, 110)));
        t.setValorParcelaOuLiquido(ParserUtils.parseCurrency(line.substring(110, 123)));

        // --- Bloco 3: Parcelamento ---
        t.setIndicadorPagamento(line.substring(123, 124));
        t.setPlano(line.substring(124, 126));
        t.setParcela(line.substring(126, 128));
        // Conversão simples de String para Integer
        try {
            t.setQtdParcelas(Integer.parseInt(line.substring(128, 130)));
        } catch (NumberFormatException e) { t.setQtdParcelas(0); }

        t.setDataPrevistaPagamento(ParserUtils.parseDate(line.substring(130, 138)));

        // --- Bloco 4: Taxas (Doidera de campos!) ---
        t.setTaxaParcelamentoComprador(ParserUtils.parseCurrency(line.substring(138, 151)));
        t.setTarifaBoletoComprador(ParserUtils.parseCurrency(line.substring(151, 164)));
        t.setValorOriginal(ParserUtils.parseCurrency(line.substring(164, 177)));
        t.setTaxaParcelamentoVendedor(ParserUtils.parseCurrency(line.substring(177, 190)));
        t.setTaxaIntermediacao(ParserUtils.parseCurrency(line.substring(190, 203)));
        t.setTarifaIntermediacao(ParserUtils.parseCurrency(line.substring(203, 216)));
        t.setTarifaBoletoVendedor(ParserUtils.parseCurrency(line.substring(216, 229)));
        t.setRepasseAplicacao(ParserUtils.parseCurrency(line.substring(229, 242)));
        t.setValorLiquidoTransacao(ParserUtils.parseCurrency(line.substring(242, 255)));

        // --- Bloco 5: Status e Meios ---
        t.setStatusPagamento(line.substring(255, 257));
        t.setMeioPagamento(line.substring(259, 261));
        t.setBandeira(line.substring(261, 291).trim()); // Onde identificamos a estratégia

        t.setCanalEntrada(line.substring(291, 293));
        t.setLeitor(line.substring(293, 295));
        t.setMeioCaptura(line.substring(295, 297));
        t.setNumeroLogico(line.substring(297, 329).trim());
        t.setNsu(line.substring(329, 340).trim());

        // --- Bloco 6: Dados do Cartão ---
        t.setCartaoBin(line.substring(343, 349));
        t.setCartaoHolder(line.substring(349, 353));
        t.setCodigoAutorizacao(line.substring(353, 359).trim());
        t.setCodigoCV(line.substring(359, 391).trim());

        return t;
    }
}