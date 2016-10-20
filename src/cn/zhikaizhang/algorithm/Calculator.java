package cn.zhikaizhang.algorithm;

import cn.zhikaizhang.util.Debug;

import java.text.DecimalFormat;
import java.util.*;

import static cn.zhikaizhang.algorithm.OperatorComparator.compareOperator;

public class Calculator {
    
    private static String DOUBLE_PATTERN = "#.##############";


    public static String calculate(String expression) throws ExpressionIllegalException{

        List<Unit> units = ExpressionParser.parse(expression);
        Debug.debug("units.size() equals " + units.size());
        Stack<Unit> operators = new Stack<Unit>();
        Stack<Unit> operands = new Stack<Unit>();

        int index = 0;
        while(index < units.size() || operators.size() != 0){

            Unit unit = units.get(index);
            if(unit.getType() == Unit.Type.OPERAND){
                operands.push(unit);
                Debug.debug(unit + "push");
                index += 1;
            }else{
                if(operators.empty() || compareOperator(operators.peek(), unit) == '<'){
                    operators.push(unit);
                    Debug.debug(unit + "push");
                    index += 1;
                }else if(compareOperator(operators.peek(), unit) == '='){
                    Unit top = operators.pop();
                    Debug.debug(top + "pop");
                    index += 1;
                }else if(compareOperator(operators.peek(), unit) == '>'){
                    Unit top = operators.pop();
                    Debug.debug(top + "pop");
                    if(top.isUnary() && operands.size() >= 1){
                        Unit operand1 = operands.pop();
                        Debug.debug(operand1 + "pop");
                        Unit res = top.operate(operand1);
                        operands.push(res);
                        Debug.debug(res + "push");
                    }else if(top.isBinary() && operands.size() >= 2){
                        Unit operand2 = operands.pop();
                        Unit operand1 = operands.pop();
                        Debug.debug(operand2 + "pop");
                        Debug.debug(operand1 + "pop");
                        Unit res = top.operate(operand1, operand2);
                        operands.push(res);
                        Debug.debug(res + "push");
                    }else{
                        throw new ExpressionIllegalException();
                    }
                }else{
                    throw new ExpressionIllegalException();
                }
            }
        }
        if(operands.size() != 1){
            throw new ExpressionIllegalException();
        }
        Unit finalResult = operands.pop();
        Debug.debug(finalResult + "pop");
        return Double.isNaN(finalResult.getVal())?"NaN":new DecimalFormat(DOUBLE_PATTERN).format(finalResult.getVal());
    }


    public static void main(String[] args){
        String s = "-2+3!-2*2+(-1+3)^3+cos(pi)-sin(-pi/2)+log10-ln(e)+2E-1+2pi-3e";
        try{
            System.out.println(Calculator.calculate(s));
        }catch(ExpressionIllegalException e){
            System.out.println(e.getMessage());
        }
    }

}
