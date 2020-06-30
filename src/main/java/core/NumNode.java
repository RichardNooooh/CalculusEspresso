package core;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Represents the values in the expression tree
 */
public class NumNode extends OperandNode {
    //left and right nodes must be null
    private BigDecimal val;

    /**
     * Constructs a new node.NumNode
     */
    public NumNode(BigDecimal value) {
        val = value;
    }

    public void setNeg()
    {
        val = val.negate();
    }

    /**
     * @return the value this node contains
     */
    public BigDecimal getVal() {
        return val;
    }

    /**
     * Returns the value of this node as a String
     */
    @Override
    public String toString() {
        return val + "";
    }

    @Override
    public BigDecimal eval(Map<String, BigDecimal> env) {
        return val;
    }
}
