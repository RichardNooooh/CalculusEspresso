package util;

import util.tareknaj.BigFunctions;

import java.math.BigDecimal;

public class BigFunctionsPlus
{
    final static int SCALE = 30;

    /**
     * Compute the value x^y
     * @param x the value of x
     * @param y the value of y
     * @return the value of x^y
     */
    public static BigDecimal pow(BigDecimal x, BigDecimal y)
    {
        if (x.doubleValue() == 0)
            return BigDecimal.ZERO;
        return BigFunctions.exp(BigFunctions.ln(x, SCALE).multiply(y), SCALE);
    }

    /**
     * Compute the value log_n(x)
     * @param x the value of x
     * @param n the value of the log base
     * @return the value of log_n(x)
     */
    public static BigDecimal log(BigDecimal x, BigDecimal n)
    {
        if (x.doubleValue() == 0 || n.doubleValue() == 0)
            throw new IllegalArgumentException("The log() method cannot have a non-positive base or x");
        return BigFunctions.ln(x, SCALE).divide(BigFunctions.ln(n, SCALE));
    }

    /**
     * Compute the value of the nth root(x)
     * @param x the value of x
     * @param n the value of the radical index
     * @return the value of root_n(x)
     */
    public static BigDecimal root(BigDecimal x, BigDecimal n)
    {
        if (n.doubleValue() == 0)
            throw new IllegalArgumentException("The root() method cannot have a 0 as its index.");
        return pow(x, BigDecimal.ONE.divide(n));
    }
}

