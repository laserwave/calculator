package cn.zhikaizhang.algorithm;

import cn.zhikaizhang.algorithm.ExpressionIllegalException;


/**
 * 运算单元（操作数或广义的运算符）
 * 将可运算的运算符、左右括号、起止符并称为广义运算符
 */
public class Unit {

    enum Type{ADD, SUBSTRACT, SIN, COS, TAN, LN, LOG, SQRT, MULTIPLY, DIVIDE, EE, POSITIVE, NEGATIVE, POWER, FACTORIAL, LEFT_BRACKET, RIGHT_BRACKET, START_STOP_SIGN, OPERAND};

    private Type type;

    private int priority;

    private double val;

    /**
     *  操作数
     */
    public Unit(double val) {
        this.type = Type.OPERAND;
        this.val = val;
    }

    /**
     *  广义运算符
     */
    public Unit(Type type) {

        this.type = type;
        switch(type){
            case ADD:
            case SUBSTRACT:
                this.priority = 1;
                break;
            case MULTIPLY:
            case DIVIDE:
            case EE:
                this.priority = 2;
                break;
            case POSITIVE:
            case NEGATIVE:
                this.priority = 3;
                break;
            case POWER:
                this.priority = 4;
                break;
            case FACTORIAL:
                this.priority = 5;
                break;
            case SIN:
            case COS:
            case TAN:
            case LN:
            case LOG:
            case SQRT:
                this.priority = 6;
                break;
        }
    }

    public Type getType() {
        return type;
    }

    public double getVal() {
        return val;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * 运算单元进行运算
     */
    public Unit operate(Unit ...operands) throws ExpressionIllegalException{

        if(isUnary() && operands.length == 1 && operands[0].getType() == Type.OPERAND){
            switch(type){
                case POSITIVE:
                    return new Unit(Operation.positive(operands[0].getVal()));
                case NEGATIVE:
                    return new Unit(Operation.negative(operands[0].getVal()));
                case FACTORIAL:
                    return new Unit(Operation.factorial(operands[0].getVal()));
                case SIN:
                    return new Unit(Operation.sin(operands[0].getVal()));
                case COS:
                    return new Unit(Operation.cos(operands[0].getVal()));
                case TAN:
                    return new Unit(Operation.tan(operands[0].getVal()));
                case LN:
                    return new Unit(Operation.ln(operands[0].getVal()));
                case LOG:
                    return new Unit(Operation.log(operands[0].getVal()));
                case SQRT:
                    return new Unit(Operation.sqrt(operands[0].getVal()));
            }
        }else if(isBinary() && operands.length == 2 && operands[0].getType() == Type.OPERAND &&operands[1].getType() == Type.OPERAND){
            switch(type){
                case ADD:
                    return new Unit(Operation.add(operands[0].getVal(), operands[1].getVal()));
                case SUBSTRACT:
                    return new Unit(Operation.substract(operands[0].getVal(), operands[1].getVal()));
                case MULTIPLY:
                    return new Unit(Operation.multiply(operands[0].getVal(), operands[1].getVal()));
                case DIVIDE:
                    return new Unit(Operation.divide(operands[0].getVal(), operands[1].getVal()));
                case EE:
                    return new Unit(Operation.ee(operands[0].getVal(), operands[1].getVal()));
                case POWER:
                    return new Unit(Operation.power(operands[0].getVal(), operands[1].getVal()));
            }
        }
        throw new ExpressionIllegalException();
    }


    /**
     * 一元运算符
     */
    public boolean isUnary(){
        return type == Type.POSITIVE || type == Type.NEGATIVE || type == Type.FACTORIAL || type == Type.SIN || type == Type.COS || type == Type.TAN || type == Type.LN || type == Type.LOG || type == Type.SQRT;
    }

    /**
     * 二元运算符
     */
    public boolean isBinary(){
        return type == Type.ADD || type == Type.SUBSTRACT || type == Type.MULTIPLY || type == Type.DIVIDE  || type == Type.EE || type == Type.POWER;
    }

    /**
     * 可运算的运算符
     */
    public boolean isNormalOperator(){
        return isUnary() || isBinary();
    }

    /**
     * 广义运算符
     */
    public boolean isOperator(){
        return type != Type.OPERAND;
    }

    @Override
    public String toString() {

        if(type == Type.OPERAND){
            return val + " ";
        }else{
            return type.toString() + " ";
        }

    }
}
