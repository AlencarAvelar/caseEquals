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

        t.setEstabelecimento(line.substring(1, 11).trim());
        t.setDataTransacao(ParserUtils.parseDate(line.substring(11, 19)));
        t.setDataEvento(ParserUtils.parseDate(line.substring(19, 27)));
        t.setHoraEvento(ParserUtils.parseTime(line.substring(27, 33)));
        t.setValorTotal(ParserUtils.parseCurrency(line.substring(97, 110)));
        t.setBandeira(line.substring(261, 291).trim());
        t.setNsu(line.substring(329, 340).trim());

        return t;
    }
}