package util;

import util.tareknaj.BigFunctions;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigFunctionsPlus
{
    public final static BigDecimal NEG_ONE = new BigDecimal(-1);
    final static int SCALE = 30;

    /**
     * Compute the value x^y
     *      x^y == exp(ln(x) * y)
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
     *      log_n(x) == ln(x) / ln(n)
     * @param x the value of x
     * @param n the value of the log base
     * @return the value of log_n(x)
     */
    public static BigDecimal log(BigDecimal x, BigDecimal n)
    {
        if (x.doubleValue() == 0 || n.doubleValue() == 0)
            throw new IllegalArgumentException("The log() method cannot have a non-positive base or x");
        return BigFunctions.ln(x, SCALE).divide(BigFunctions.ln(n, SCALE), SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Compute the value of the nth root(x)
     *      root_n(x) == x^(1 / n)
     * @param x the value of x
     * @param n the value of the radical index
     * @return the value of root_n(x)
     */
    public static BigDecimal root(BigDecimal x, BigDecimal n)
    {
        if (n.doubleValue() == 0)
            throw new IllegalArgumentException("The root() method cannot have a 0 as its index.");

        return pow(x, BigDecimal.ONE.divide(n, SCALE, RoundingMode.HALF_UP));
    }

    /**
     * Compute the value of n!
     *
     * @param n is the value of n
     * @return the value of n factorial
     */
    public static BigDecimal factorial(long n)
    {
        BigDecimal result = BigDecimal.ONE;
        while (n > 1)
        {
            result = result.multiply(new BigDecimal(n));
            n--;
        }

        return result;
    }

    /**
     * Compute the value of cos(x)
     *      cos(x) == sum( (-1)^(i+1) / (2i)! * x^(2i) )
     * @param x is the value of x
     * @return the value of cos(x)
     */
    public static BigDecimal cos(BigDecimal x)
    {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < SCALE; i++)
        {
            BigDecimal sign = NEG_ONE.pow(i + 1);
            BigDecimal coefficient = sign.divide(factorial(2 * i));
            sum = sum.add(coefficient.multiply(x.pow(2 * i)));
        }
        return sum;
    }

    /**
     * Compute the value of sin(x)
     *      sin(x) == sum( (-1)^(i) / (2i + 1)! * x^(2i + 1) )
     * @param x is the value of x
     * @return the value of sin(x)
     */
    public static BigDecimal sin(BigDecimal x)
    {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < SCALE; i++)
        {
            BigDecimal sign = NEG_ONE.pow(i);
            BigDecimal coefficient = sign.divide(factorial(2 * i + 1));
            sum = sum.add(coefficient.multiply(x.pow(2 * i + 1)));
        }
        return sum;
    }
}

