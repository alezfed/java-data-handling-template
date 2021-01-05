package com.epam.izh.rd.online.service;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class SimpleDateService implements DateService {

    @Override
    public String parseDate(LocalDate localDate) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return localDate.format(dateTimeFormatter);
        } catch (DateTimeException dateTimeException) {
            System.out.println("Ошибка преобразования даты в методе parseDate(LocalDate localDate) класса SimpleDateService.");
            dateTimeException.printStackTrace();
        }
        return null;
    }

    @Override
    public LocalDateTime parseString(String string) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(string, dateTimeFormatter);

        } catch (DateTimeException dateTimeException) {
            System.out.println("Ошибка преобразования даты в методе parseString(String string)) класса SimpleDateService.");
            dateTimeException.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) {
        try {
            return localDate.format(formatter);
        } catch (DateTimeException dateTimeException) {
            System.out.println("Ошибка преобразования даты в методе convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) класса SimpleDateService.");
            dateTimeException.printStackTrace();
        }
        return null;
    }

    @Override
    public long getNextLeapYear() {
        int intYear = Year.now(ZoneId.systemDefault()).getValue();
        while (true) {
            if (Year.of(++intYear).isLeap()) break;
        }
        return intYear;
    }

    @Override
    public long getSecondsInYear(int year) {
        LocalDateTime startLocalDateTime = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime endLocalDateTime = startLocalDateTime.plusYears(1);
        Instant startInstant = startLocalDateTime.toInstant(ZoneOffset.UTC);
        Instant endInstant = endLocalDateTime.toInstant(ZoneOffset.UTC);
        return Duration.between(startInstant, endInstant).getSeconds();
    }
}
