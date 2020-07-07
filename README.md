# CalculusEspresso

A non-CAS, Java calculus library for computing mathematical expressions. 

##Features
- Multi-variable derivatives and integrals
- Trigonometric functions
    - sine, cosine, tangent
    - secant, cosecant, cotangent
    - arcsine, arccosine
- nth-root and nth-logarithm operators
- Implicit multiplication

##How To Use
Import the CalculusEspresso.java class into your code. {TODO describe how to do this}

###Quick Start (General Use)
The CalculusEspresso class accepts a String which represents your expression (in
standard infix notation). Upon the creation of CalculusEspresso, the *evaluate()* 
method can be called.
```$xslt
    String expression = "1 + 2.5 * 6";
    CalculusEspresso c = new CalculusEspresso(expression);
    System.out.println(c.evaluate()); // 16.0
```
####Variables
You can also use variables within your expression, using any non-reserved characters
in ASCII (possible UNICODE, haven't tested). The reserved characters can be found in
Operator.java within the core.node package. **The alphabetical characters are currently
completely free to use, however the variable 'e' and string 'pi' will become reserved
for the transcendental constants in the near future.**
```$xslt
    String expression = "x + y * z ~~ x=1 y=4 z=5";
    CalculusEspresso c = new CalculusEspresso(expression);
    System.out.println(c.evaluate()); // 21.0
```
####Implicit Multiplication
Implicit multiplication works in the following situations:
```$xslt
    //number, variable
    String num_var = "2x ~~ x=3";
    System.out.println((new CalculusEspresso(num_var)).evaluate()); // 6.0
    
    //variable, number
    String var_num = "x2 ~~ x=3";
    System.out.println((new CalculusEspresso(var_num)).evaluate()); // 6.0
    
    //variable, variable
    String var_var = "xyz ~~ x=1 y=3 z=5";
    System.out.println((new CalculusEspresso(var_var)).evaluate()); // 15.0

    //number, parenthesis
    String num_paren = "2(3 + 4)";
    System.out.println((new CalculusEspresso(num_paren)).evaluate()); // 14.0
    
    //variable, parenthesis
    String var_paren = "x(12 + 4) ~~ x=3";
    System.out.println((new CalculusEspresso(var_paren)).evaluate()); // 48.0

    //number, function
    String num_func = "3sqrt(9)";
    System.out.println((new CalculusEspresso(num_func)).evaluate()); // 9.0

    //variable, function
    String var_func = "xln(5) ~~ x=3";
    System.out.println((new CalculusEspresso(var_func)).evaluate()); // 4.8283137372
```
####Simple Functions
As you can see in the examples above, you can also use various functions (full list in the
features list above).
Most of the functions can simply be used by typing them out with the parentheses:
```$xslt
    String sqrt_func = "sqrt(11)";
    System.out.println((new CalculusEspresso(sqrt_func)).evaluate()); // 3.31662479

    String sin_func = "sin(3)";
    System.out.println((new CalculusEspresso(sin_func)).evaluate()); // 0.14112000785871504
```
####"Parameter" Functions
A few functions, such as the root and logarithm functions, require additional information
to compute their values.
```$xslt
    String log_func = "log[4](16)";
    System.out.println((new CalculusEspresso(log_func)).evaluate()); // 2.0

    String root_func = "root[3](18)";
    System.out.println((new CalculusEspresso(root_func)).evaluate()); // 2.6207413942088964
```
These functions simply require that additional input to be placed within '[ ]'.

####Calculus Functions
The two calculus operators have similar input:
```$xslt
    String derivative_func = "der[x = 12](x^3)";
    System.out.println((new CalculusEspresso(derivative_func)).evaluate()); // 432.00000000036

    String integral_func = "int[0, 1, x](x^2)";
    System.out.println((new CalculusEspresso(integral_func)).evaluate()); // 0.3333333325
```
In the configuration file (to be added soon), the 'h' variable determines the precision of
the derivative, while the 'del_x' variable determines the precision of the integral (names
of these configuration variables will be replaced with more intuitive ones).

Based on the computation method of these operators, multiple integrals and/or derivatives
can be nested within each other. Keep in mind this has not been tested extensively, so
compute multi-variable computations at your own risk. **Please be sure to post your errors in
issues if you find any!**

###Repetitive Computations


###Advanced Use

##Other Notes
I built this library on Java 13, however, it should work with lower versions of Java, 
down to Java 1.8. Perhaps in the near future, I'll downgrade this library down to 1.8. 

##Future Features
- Transcendental constants in expression
    - PI
    - E
- Configuration file
    - Levels of precision
    - Implicit Multiplication
- Vector Calculus
    - Internal vectors/matrices
    - Divergence/Curl
- Future versions may or may not be compatible with this current one