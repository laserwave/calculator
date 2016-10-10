package cn.zhikaizhang;

import java.math.BigDecimal;

/**
 * 表达式的运算单元，表示一个操作数或者一个运算符
 */
public class Unit {

    private UnitType unitType;

    private char op;

    private double val;

    public Unit() {
    }

    public Unit(char op) {
        this.unitType = UnitType.OPERATOR;
        this.op = op;
    }

    public Unit(double val) {
        this.unitType = UnitType.OPERAND;
        this.val = val;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public char getOp() {
        return op;
    }

    public void setOp(char op) {
        this.op = op;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }

    enum UnitType{OPERATOR, OPERAND};

    public Unit operate(Unit operand1, Unit operand2) {
        if(unitType != UnitType.OPERATOR || operand1.unitType != UnitType.OPERAND || operand2.unitType != UnitType.OPERAND){
            return null;
        }
        double n1 = operand1.val;
        double n2 = operand2.val;

        if(op == '+'){
            if(isNanOrInfinite(operand1.val + operand2.val)){
                return new Unit(operand1.val + operand2.val);
            }
            return new Unit(new BigDecimal(String.valueOf(operand1.val)).add(new BigDecimal(String.valueOf(operand2.val))).doubleValue());
        }else if(op == '-'){
            if(isNanOrInfinite(operand1.val - operand2.val)){
                return new Unit(operand1.val - operand2.val);
            }
            return new Unit(new BigDecimal(String.valueOf(operand1.val)).subtract(new BigDecimal(String.valueOf(operand2.val))).doubleValue());
        }else if(op == '×' || op == '*') {
            if(isNanOrInfinite(operand1.val * operand2.val)){
                return new Unit(operand1.val * operand2.val);
            }
            return new Unit(new BigDecimal(String.valueOf(operand1.val)).multiply(new BigDecimal(String.valueOf(operand2.val))).doubleValue());
        }else {
            if(isNanOrInfinite(operand1.val / operand2.val)){
                return new Unit(operand1.val / operand2.val);
            }
            return new Unit(new BigDecimal(String.valueOf(operand1.val)).divide(new BigDecimal(String.valueOf(operand2.val)), 14, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }

    private boolean isNanOrInfinite(double n){
        return Double.isInfinite(n) || Double.isNaN(n);
    }

    @Override
    public String toString() {

        if(unitType == UnitType.OPERATOR){
            return "运算符 " + op + " ";
        }else{
            return "操作数 " + val + " ";
        }
    }
}
