package com.equals.caseequals.utils;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ParserUtilsTest {

    @Test
    void deveConverterDataComSucesso() {
        // O formato correto que o Parser espera é yyyyMMdd
        String dataString = "20180925";
        LocalDate dataConvertida = ParserUtils.parseDate(dataString);

        assertNotNull(dataConvertida);
        assertEquals(2018, dataConvertida.getYear());
        assertEquals(9, dataConvertida.getMonthValue());
        assertEquals(25, dataConvertida.getDayOfMonth());
    }

    @Test
    void deveConverterHoraComSucesso() {
        String horaString = "143000";
        LocalTime horaConvertida = ParserUtils.parseTime(horaString);

        assertNotNull(horaConvertida);
        assertEquals(14, horaConvertida.getHour());
        assertEquals(30, horaConvertida.getMinute());
        assertEquals(0, horaConvertida.getSecond());
    }

    @Test
    void deveConverterMoedaComSucesso() {
        String valorString = "0000000010000"; // R$ 100,00
        BigDecimal valorConvertido = ParserUtils.parseCurrency(valorString);

        assertNotNull(valorConvertido);
        // compareTo == 0 significa que matematicamente são iguais, ignorando a escala (100.00 vs 100)
        assertEquals(0, new BigDecimal("100.00").compareTo(valorConvertido));
    }
}