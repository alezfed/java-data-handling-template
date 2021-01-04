package com.epam.izh.rd.online.service;

public class SimpleTextService implements TextService {

    @Override
    public String removeString(String base, String remove) {
        if (base == null || remove == null) {
            System.out.println("При замене строк в методе removeString в классе SimpleTextService введены строки null.");
            return null;
        }
        return base.replace(remove, "");
    }

    @Override
    public boolean isQuestionString(String text) {
        if (text == null) {
            System.out.println("При вводе параметра text в метод isQuestionString в классе SimpleTextService передано значение null.");
            return false;
        }
        return text.endsWith("?");
    }

    @Override
    public String concatenate(String... elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String curString : elements) {
            if (curString == null) {
                System.out.println("При передаче строк в метод concatenate в классе SimpleTextService передано значение null.");
                return null;
            }
            stringBuilder.append(curString);
        }
        return stringBuilder.toString();
    }

    @Override
    public String toJumpCase(String text) {
        if (text == null) {
            System.out.println("При вводе параметра text в метод toJumpCase в классе SimpleTextService передано значение null.");
            return null;
        }
        char[] textAsChars = text.toCharArray();
        for (int i = 0; i < textAsChars.length; i++) {
            if (i % 2 == 0) {
                textAsChars[i] = Character.toLowerCase(textAsChars[i]);
            } else {
                textAsChars[i] = Character.toUpperCase(textAsChars[i]);
            }
        }
        return String.valueOf(textAsChars);
    }

    @Override
    public boolean isPalindrome(String string) {
        if (string == null) {
            System.out.println("При вводе параметра string в метод isPalindrome в классе SimpleTextService передано значение null.");
            return false;
        }
        if (string.equals("")) {
            System.out.println("При вводе параметра string в метод isPalindrome в классе SimpleTextService передано пустая строка.");
            return false;
        }
        String startString = string.replaceAll("\\s+", "").toLowerCase();
        String reversedString = new StringBuilder(startString).reverse().toString();
        return startString.equals(reversedString);
    }
}
