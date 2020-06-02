package node;

import java.util.Map;

/**
 * Represents the values in the expression tree
 */
public class NumNode extends OperandNode {
    //left and right nodes must be null
    private double val;

    /**
     * Constructs a new node.NumNode
     */
    public NumNode(double value) {
        val = value;
    }

    public void setNeg()
    {
        val = -val;
    }

    /**
     * @return the value this node contains
     */
    public double getVal() {
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
    public double eval(Map<String, Double> env) {
        return val;
    }
}
