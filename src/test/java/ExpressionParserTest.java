import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("When running ExpressionParser")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExpressionParserTest
{
	ExpressionParser parser;

	@BeforeAll
	public void setUp()
	{
		parser = new ExpressionParser("");
	}

	@Nested
	@DisplayName("The substituteMultiCharOp method should a string with")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class substituteMultiCharOpTest
	{
		Method method;

		@BeforeEach
		public void setUp() throws NoSuchMethodException
		{
			method = ExpressionParser.class.getDeclaredMethod("substituteMultiCharOp", String.class);
			method.setAccessible(true);
		}

		/**
		 * Since the invoke method is very long for lambdas, invokeExpr simply shortens the method call.
		 * @return String returned from invoked method
		 */
		private String invokeExpr(String expression) throws InvocationTargetException, IllegalAccessException
		{
			return (String) method.invoke(parser, expression);
		}

		@Test
		@DisplayName((char) 208 + " instead of sqrt")
		public void sqrtTest()
		{
			assertAll("should return substituted char in string with preserved spacing",
					() -> assertEquals((char) 208 + "2", invokeExpr("sqrt2")),
					() -> assertEquals((char) 208 + "(2)", invokeExpr("sqrt(2)")),
					() -> assertEquals((char) 208 + " 2", invokeExpr("sqrt 2")),
					() -> assertEquals((char) 208 + " (2)", invokeExpr("sqrt (2)")),
					() -> assertEquals("x" + (char) 208 + " 24x", invokeExpr("xsqrt 24x")),
					() -> assertEquals("x" + (char) 208 + "24x", invokeExpr("xsqrt24x")),
					() -> assertEquals("x " + (char) 208 + "24x", invokeExpr("x sqrt24x"))
			);
		}

	}
}