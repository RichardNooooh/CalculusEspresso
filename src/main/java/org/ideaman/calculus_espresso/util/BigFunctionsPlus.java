package org.ideaman.calculus_espresso.util;

import org.ideaman.calculus_espresso.util.tareknaj.BigFunctions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class BigFunctionsPlus
{
    //Common numbers
    public final static BigDecimal NEG_ONE = new BigDecimal(-1);
    public final static BigDecimal TWO = new BigDecimal(2);

    //Transcendental numbers (and their scaled variants)
    public final static BigDecimal PI = new BigDecimal("3.14159265358979323846264338327950288419716939937511"); //50
    public final static BigDecimal PI_HALVES = PI.divide(TWO);
    public final static BigDecimal THREE_PI_HALVES = new BigDecimal(3).multiply(PI_HALVES);
    public final static BigDecimal TAU = new BigDecimal("6.28318530717958647692528676655900576839433879875021"); //50
    public final static BigDecimal E = new BigDecimal("2.71828182845904523536028747135266249775724709369996"); //50

    //Precision parameters
    final static int SCALE = 30;
    final static int N = 10; //TODO change this into "accurate digits" using error approximation
    final static BigDecimal ASYMPTOTE_LIMIT = new BigDecimal(0.0000000000001);

    /**
     * Compute the value x^y
     *      x^y == exp(ln(x) * y)
     * @param x the value of x
     * @param y the value of y
     * @return the value of x^y
     */
    public static BigDecimal pow(BigDecimal x, BigDecimal y)
    {
        if (x.abs().compareTo(ASYMPTOTE_LIMIT) < 0)
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
        if (x.compareTo(ASYMPTOTE_LIMIT) < 0 || n.compareTo(ASYMPTOTE_LIMIT) < 0)
            throw new IllegalArgumentException("The log() method cannot have a non-positive base or x");
        return divide(BigFunctions.ln(x, SCALE), BigFunctions.ln(n, SCALE));
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
        if (x.compareTo(ASYMPTOTE_LIMIT) < 0 || n.compareTo(ASYMPTOTE_LIMIT) < 0)
            throw new IllegalArgumentException("The root() method cannot have a 0 as its index, nor can it have" +
                    " a negative radicand.");

        return pow(x, divide(BigDecimal.ONE, n));
    }

    /**
     * Compute the value of n!
     *
     * @param n is the value of n
     * @return the value of n factorial
     * @throws IllegalArgumentException if n < 0
     */
    public static BigInteger factorial(long n)
    {
        if (n < 0)
            throw new IllegalArgumentException("factorial(long n) can not receive negative operators. The user " +
                    "should implement their own Gamma function for \"negative factorial\" values");
        BigInteger result = BigInteger.ONE;
        while (n > 1)
        {
            result = result.multiply(new BigInteger(String.valueOf(n)));
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
        //Limits the range of x to provide more accurate trigonometric values
        x = x.remainder(TAU);
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < N; i++)
        {
            BigDecimal sign = NEG_ONE.pow(i);
            BigDecimal coefficient = divide(sign, new BigDecimal(factorial(2 * i)));
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
        //Limits the range of x to provide more accurate trigonometric values
        x = x.remainder(TAU);
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < N; i++)
        {
            BigDecimal sign = NEG_ONE.pow(i);
            BigDecimal coefficient = divide(sign, new BigDecimal(factorial(2 * i + 1)));
            sum = sum.add(coefficient.multiply(x.pow(2 * i + 1)));
        }
        return sum;
    }

    /**
     * Compute the value of tan(x)
     *      tan(x) = sin(x) / cos(x)
     *
     * Due to the nature of using Taylor series approximation with tan(x), involving Bernoulli numbers and the like,
     * the implementation of a more efficient tan(x) calculation is left up to the end user.
     * Perhaps in a future version of this library, such a thing may be implemented.
     * @param x is the value of x
     * @return the value of tan(x)
     * @throws IllegalArgumentException if the value of x is too close to an asymptote of tangent(x)
     */
    public static BigDecimal tan(BigDecimal x)
    {
        x = x.remainder(PI);
        if (x.subtract(PI_HALVES).abs().compareTo(ASYMPTOTE_LIMIT) < 0)
            throw new IllegalArgumentException("The value of x is too close to an asymptote in tan().");
        return divide(sin(x), cos(x));
    }

    /**
     * Compute the value of cot(x)
     *      cot(x) = cos(x) / sin(x)
     *
     * Due to the nature of using Taylor series approximation with cot(x), involving Bernoulli numbers and the like,
     * the implementation of a more efficient cot(x) calculation is left up to the end user.
     * Perhaps in a future version of this library, such a thing may be implemented.
     * @param x is the value of x
     * @return the value of cot(x)
     * @throws IllegalArgumentException if the value of x is too close to an asymptote of cotangent(x)
     */
    public static BigDecimal cot(BigDecimal x)
    {
        x = x.remainder(PI);
        if (x.subtract(BigDecimal.ZERO).abs().compareTo(ASYMPTOTE_LIMIT) < 0)
            throw new IllegalArgumentException("The value of x is too close to an asymptote in cot().");
        return divide(cos(x), sin(x));
    }

    /**
     * Compute the value of sec(x)
     *      sec(x) = 1 / cos(x)
     *
     * Due to the nature of using Taylor series approximation with sec(x), involving Euler numbers and the like,
     * the implementation of a more efficient sec(x) calculation is left up to the end user.
     * Perhaps in a future version of this library, such a thing may be implemented.
     * @param x is the value of x
     * @return the value of sec(x)
     * @throws IllegalArgumentException if the value of x is too close to an asymptote of secant(x): a multiple of
     *                                  PI / 2 or 3PI / 2
     */
    public static BigDecimal sec(BigDecimal x)
    {
        x = x.remainder(TAU);
        if (x.subtract(PI_HALVES).abs().compareTo(ASYMPTOTE_LIMIT) < 0 ||
            x.subtract(THREE_PI_HALVES).abs().compareTo(ASYMPTOTE_LIMIT) < 0)
            throw new IllegalArgumentException("The value of x is too close to an asymptote in sec().");
        return divide(BigDecimal.ONE, cos(x));
    }

    /**
     * Compute the value of csc(x)
     *      csc(x) = 1 / sin(x)
     *
     * Due to the nature of using Taylor series approximation with csc(x), involving Euler numbers and the like,
     * the implementation of a more efficient csc(x) calculation is left up to the end user.
     * Perhaps in a future version of this library, such a thing may be implemented.
     * @param x is the value of x
     * @return the value of csc(x)
     * @throws IllegalArgumentException if the value of x is too close to an asymptote of cosecant(x): 0 or a multiple
     *                                  of PI
     */
    public static BigDecimal csc(BigDecimal x)
    {
        x = x.remainder(TAU);
        if (x.subtract(BigDecimal.ZERO).abs().compareTo(ASYMPTOTE_LIMIT) < 0 ||
            x.subtract(PI).abs().compareTo(ASYMPTOTE_LIMIT) < 0)
            throw new IllegalArgumentException("The value of x is too close to an asymptote in csc().");
        return divide(BigDecimal.ONE, sin(x));
    }

    /**
     * Compute the value of arcsin(x)
     *      arcsin(x) = sum( (2i)! / (a * b * c) * x^c )
     *              a = 4^i
     *              b = (i!)^2
     *              c = 2i + 1
     *
     * @param x is the value of x
     * @return the value of arcsin(x)
     * @throws IllegalArgumentException if the abs(x) > 1
     */
    public static BigDecimal arcsin(BigDecimal x)
    {
        if (x.abs().compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("The input of arcsin(x) must be within -1 and 1, inclusive.");

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < N; i++)
        {
            BigDecimal numerator = new BigDecimal(factorial(2 * i));
            BigDecimal a = new BigDecimal(Math.pow(4, i));
            BigDecimal b = new BigDecimal(factorial(i).pow(2));
            BigDecimal c = new BigDecimal(2 * i + 1);
            BigDecimal coefficient = divide(numerator, a.multiply(b).multiply(c));
            BigDecimal result = coefficient.multiply(pow(x, c));

            sum = sum.add(result);
        }

        return sum;
    }

    /**
     * Compute the value of arccos(x)
     *      arccos(x) = PI/2 - arcsin(x)
     *
     * @param x is the value of x
     * @return the value of arccos(x)
     * @throws IllegalArgumentException if the abs(x) > 1
     */
    public static BigDecimal arccos(BigDecimal x)
    {
        if (x.abs().compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("The input of arccos(x) must be within -1 and 1, inclusive.");

        return PI_HALVES.subtract(arcsin(x));
    }

    /**
     * Divides two BigDecimal values with the same SCALE and RoundingMode
     * @param numerator value of the numerator
     * @param denominator value of the denominator
     * @return the value of numerator / denominator
     * @throws ArithmeticException
     */
    public static BigDecimal divide(BigDecimal numerator, BigDecimal denominator)
    {
        if (denominator.compareTo(BigDecimal.ZERO) == 0)
            throw new ArithmeticException("You can not divide by 0.");
        return numerator.divide(denominator, SCALE, RoundingMode.HALF_UP);
    }
}

