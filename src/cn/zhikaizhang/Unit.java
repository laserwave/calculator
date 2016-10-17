package cn.zhikaizhang;

import cn.zhikaizhang.Calculator.ExpressionIllegalException;

/**
 * 表达式的运算单元，表示一个操作数或者一个运算符
 */
public class Unit {

    enum Type{ADD, SUBSTRACT, MULTIPLY, DIVIDE, POSITIVE, NEGATIVE, POWER, FACTORIAL, LEFT_BRACKET, RIGHT_BRACKET, START_STOP_SIGN, OPERAND};

    private Type type;

    private int priority;

    private double val;

    public Unit(double val) {
        this.type = Type.OPERAND;
        this.val = val;
    }

    public Unit(Type type) {

        this.type = type;
        switch(type){
            case ADD:
            case SUBSTRACT:
                this.priority = 1;
                break;
            case MULTIPLY:
            case DIVIDE:
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

    public Unit operate(Unit ...operands) throws ExpressionIllegalException{

        if(isUnary() && operands.length == 1 && operands[0].getType() == Type.OPERAND){
            switch(type){
                case POSITIVE:
                    return new Unit(Operation.positive(operands[0].getVal()));
                case NEGATIVE:
                    return new Unit(Operation.negative(operands[0].getVal()));
                case FACTORIAL:
                    return new Unit(Operation.factorial(operands[0].getVal()));
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
                case POWER:
                    return new Unit(Operation.power(operands[0].getVal(), operands[1].getVal()));
            }
        }
        throw new ExpressionIllegalException();
    }


    public boolean isUnary(){
        return type == Type.POSITIVE || type == Type.NEGATIVE || type == Type.FACTORIAL;
    }

    public boolean isBinary(){
        return type == Type.ADD || type == Type.SUBSTRACT || type == Type.MULTIPLY || type == Type.DIVIDE || type == Type.POWER;
    }

    public boolean isNormalOperator(){
        return isUnary() || isBinary();
    }

    public boolean isOperator(){
        return type != Type.OPERAND;
    }

    @Override
    public String toString() {

        if(type == Type.OPERAND){
            return type.toString() + " " + val + " ";
        }else{
            return type.toString() + " ";
        }

    }
}
