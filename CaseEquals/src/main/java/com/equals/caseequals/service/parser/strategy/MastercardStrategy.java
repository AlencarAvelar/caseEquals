package com.equals.caseequals.service.parser.strategy;

import com.equals.caseequals.model.Transaction;
import com.equals.caseequals.utils.ParserUtils;
import org.springframework.stereotype.Component;

@Component
public class MastercardStrategy implements PaymentStrategy {

    @Override
    public boolean appliesTo(String rawBrandName) {
        return rawBrandName != null && rawBrandName.trim().equalsIgnoreCase("MASTERCARD");
    }

    @Override
    public Transaction processLine(String line) {
        Transaction t = new Transaction();

        // Código muito mais limpo usando o ParserUtils:

        t.setEstabelecimento(line.substring(1, 11).trim());

        // Converte Data (Pos 12-19)
        t.setDataTransacao(ParserUtils.parseDate(line.substring(11, 19)));

        // Converte Data Evento (Pos 20-27)
        t.setDataEvento(ParserUtils.parseDate(line.substring(19, 27)));

        // Converte Hora (Pos 28-33)
        t.setHoraEvento(ParserUtils.parseTime(line.substring(27, 33)));

        // Converte Valor (Pos 98-110)
        t.setValorTotal(ParserUtils.parseCurrency(line.substring(97, 110)));

        t.setBandeira(line.substring(261, 291).trim());
        t.setNsu(line.substring(329, 340).trim());

        return t;
    }
}