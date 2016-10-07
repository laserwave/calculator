package sample;

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
        if(op == '+'){
            return new Unit(operand1.val + operand2.val);
        }else if(op == '-'){
            return new Unit(operand1.val - operand2.val);
        }else if(op == '×' || op == '*') {
            return new Unit(operand1.val * operand2.val);
        }else {
            return new Unit(operand1.val / operand2.val);
        }
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
