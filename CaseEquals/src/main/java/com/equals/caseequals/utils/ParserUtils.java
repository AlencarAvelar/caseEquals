package com.equals.caseequals.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ParserUtils {

    // Formatadores estáticos (para não criar um novo a cada linha lida)
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HHmmss");

    /**
     * Converte string "20180925" para LocalDate
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        return LocalDate.parse(dateStr, DATE_FMT);
    }

    /**
     * Converte string "131736" para LocalTime
     */
    public static LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.isBlank()) return null;
        return LocalTime.parse(timeStr, TIME_FMT);
    }

    /**
     * Converte string "0000000000100" para BigDecimal (1.00)
     * Divide por 100 para ajustar os centavos.
     */
    public static BigDecimal parseCurrency(String valueStr) {
        if (valueStr == null || valueStr.isBlank()) return BigDecimal.ZERO;
        return new BigDecimal(valueStr).divide(new BigDecimal(100));
    }
}