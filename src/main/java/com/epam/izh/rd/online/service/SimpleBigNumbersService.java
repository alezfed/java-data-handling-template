package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        BigDecimal aBigDecimal = BigDecimal.valueOf(a);
        BigDecimal bBigDecimal = BigDecimal.valueOf(b);
        return aBigDecimal.divide(bBigDecimal, range, RoundingMode.HALF_UP);
    }

    @Override
    public BigInteger getPrimaryNumber(int range) {
        if (range <= 0) {
            System.out.println("При вычислении простых чисел задан номер простого числа <=0.");
            return null;
        }
        BigInteger valuePrime = BigInteger.valueOf(2);
        for (int i = 1; i <= range; i++) {
            valuePrime = valuePrime.nextProbablePrime();
        }
        return valuePrime;
    }
}
