package org.ideaman.calculus_espresso.util;

/**
 * Stores the ASCII characters for all important values
 */
public enum ExpressionChar
{
	//Numeric Characters
	ZERO((char) 48),
	NINE((char) 57),
	DECIMAL((char) 46),

	LEFT_PARENTHESIS((char) 40),
	RIGHT_PARENTHESIS((char) 41);

	private char replacementChar;
	ExpressionChar(char replacementChar)
	{
		this.replacementChar = replacementChar;
	}

	/**
	 * @return the replacement character of the enumeration
	 */
	public char getChar()
	{
		return replacementChar;
	}

	public static boolean isNumber(char c)
	{
		return c >= ZERO.getChar() && c <= NINE.getChar();
	}

	/**
	 * @return the string representation of the replacement character
	 */
	@Override
	public String toString()
	{
		return replacementChar + "";
	}
}
