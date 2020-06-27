package util;

import util.tareknaj.BigFunctions;

import java.math.BigDecimal;

public class BigFunctionsPlus
{
    final static int SCALE = 10;

    /**
     * Compute the value x^y
     * @param x the value of x
     * @param y the value of y
     * @return the value of x^y
     */
    public static BigDecimal pow(BigDecimal x, BigDecimal y)
    {
        return BigFunctions.exp(BigFunctions.ln(x, SCALE).multiply(y), SCALE);
    }
}

