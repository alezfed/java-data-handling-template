package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.repository.SimpleFileRepository;

public class SimpleRegExpService implements RegExpService {

    private final SimpleFileRepository fileRepository;

    public SimpleRegExpService() {
        fileRepository = new SimpleFileRepository();
    }

    public SimpleRegExpService(SimpleFileRepository simpleFileRepository) {
        this.fileRepository = simpleFileRepository;
    }

    @Override
    public String maskSensitiveData() {
        String startString = fileRepository.readFileFromResources("sensitive_data.txt");
        if (startString == null || startString.equals("")) {
            return "";
        }
        return startString.replaceAll("\\n|\\r\\n", "").
                replaceAll("\\b(\\d{4})(\\s*\\d{4}\\s*\\d{4}\\s*)(\\d{4})\\b", "$1 **** **** $3");
    }

    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String startString = fileRepository.readFileFromResources("sensitive_data.txt");
        if (startString == null || startString.equals("")) {
            return "";
        }
        return startString.replaceAll("\\n|\\r\\n", "").
                replaceAll("\\$\\{payment_amount\\}", String.format("%.0f", paymentAmount)).
                replaceAll("\\$\\{balance\\}", String.format("%.0f", balance));
    }
}
