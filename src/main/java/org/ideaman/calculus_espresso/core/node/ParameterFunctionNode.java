package org.ideaman.calculus_espresso.core.node;

import org.ideaman.calculus_espresso.util.BigFunctionsPlus;

import java.math.BigDecimal;
import java.util.Map;

//log, root
public class ParameterFunctionNode extends FunctionNode
{
    protected String params;

    public ParameterFunctionNode(Operator op, String parameters)
    {
        this.operator = op;
        this.params = parameters.trim();
    }

    @Override
    public BigDecimal eval(Map<String, BigDecimal> env)
    {
        BigDecimal val = right.eval(env);

        switch(this.operator)
        {
            case LOGARITHM:
                return BigFunctionsPlus.log(val, new BigDecimal(params));
            case ROOT:
                return BigFunctionsPlus.root(val, new BigDecimal(params));
            default:
                return BigDecimal.ZERO;
        }
    }
}
