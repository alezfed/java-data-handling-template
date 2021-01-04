package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.repository.SimpleFileRepository;

public class SimpleRegExpService implements RegExpService {
    private final SimpleFileRepository simpleFileRepository;

    public SimpleRegExpService() {
        simpleFileRepository = new SimpleFileRepository();
    }

    public SimpleRegExpService(SimpleFileRepository simpleFileRepository) {
        this.simpleFileRepository = simpleFileRepository;
    }

    @Override
    public String maskSensitiveData() {
        String startString = simpleFileRepository.readFileFromResources("sensitive_data.txt");
        if (startString == null || startString.equals("")) {
            return "";
        }
        return startString.replaceAll("\\b(\\d{4})(\\s*\\d{4}\\s*\\d{4}\\s*)(\\d{4})\\b", "$1 **** **** $3");
    }

    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String startString = simpleFileRepository.readFileFromResources("sensitive_data.txt");
        if (startString == null || startString.equals("")) {
            return "";
        }
        return startString.replaceAll("\\$\\{payment_amount\\}", String.format("%.0f", paymentAmount)).
                replaceAll("\\$\\{balance\\}", String.format("%.0f", balance));
    }
}
