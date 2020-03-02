/**
 * Stores the ASCII characters for all operations and important values
 */
public enum ExpressionChar
{
	//Standard Characters
	ZERO((char) 48),
	NINE((char) 57),
	DECIMAL((char) 46),

	//Binary Operators
	ADDITION('+'),
	SUBTRACTION('-'),
	MULTIPLICATION('*'),
	DIVISION('/'),
	EXPONENTIAL('^'),

	//Unary 208-212
	SQUARE_ROOT((char) 208),
	LOGARITHM((char) 209),

	//Calculus 213-218
	DERIVATIVE((char) 213),
	INTEGRAL((char) 214);


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

	/**
	 * @return the string representation of the replacement character
	 */
	@Override
	public String toString()
	{
		return replacementChar + "";
	}
}
