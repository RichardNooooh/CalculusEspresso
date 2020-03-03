/**
 * Stores the ASCII characters for all important values
 */
public enum ExpressionChar
{
	//Standard Characters
	ZERO((char) 48),
	NINE((char) 57),
	DECIMAL((char) 46);

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
