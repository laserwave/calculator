package cn.zhikaizhang;

import java.text.DecimalFormat;
import java.util.*;
import cn.zhikaizhang.Unit.Type;

public class Calculator {

    public static char compareOperator(Unit u1, Unit u2) throws ExpressionIllegalException{

        if(!(u1.isOperator() && u2.isOperator())){
            throw new ExpressionIllegalException();
        }
        if(u1.isNormalOperator() && u2.isNormalOperator()){
            return u1.getPriority()>=u2.getPriority()?'>':'<';
        }else if(u2.getType() == Type.LEFT_BRACKET){
            return '<';
        }else if(u1.getType() == Type.LEFT_BRACKET){
            if(u2.isNormalOperator()){
                return '<';
            }else if(u2.getType() == Type.RIGHT_BRACKET){
                return '=';
            }
        }else if(u1.getType() == Type.START_STOP_SIGN){
            if(u2.isNormalOperator()){
                return '<';
            }else if(u2.getType() == Type.START_STOP_SIGN){
                return '=';
            }
        }else if(u1.isNormalOperator()){
            return '>';
        }
        return 'e';
    }


    /**
     * 将运算的字符串转化为运算符和操作数的列表，Unit表示一个运算符或一个操作数
     */
    private static List<Unit> parseUnits(String s) throws ExpressionIllegalException{

        List<Unit> units = new ArrayList<Unit>();
        /**
         * 若一个Unit是乘号或除号或左括号或井号，flag置为true，否则置为false
         * 若一个Unit是+，且flag为true，说明该+号表示正号
         * 若一个Unit是-，且flag为true，说明该-号表示负号
         */
        boolean flag = true;

        for(int i = 0; i < s.length(); i++){

            char c = s.charAt(i);
            if(c == '∞' || c == 'N' || c >= '0' && c <= '9'){   //操作数
                flag = false;
                if(s.charAt(i) == '∞'){   //操作数，∞无穷,用于处理上次运算结果为无穷然后继续运算的情形

                    units.add(new Unit(Double.POSITIVE_INFINITY));
                }
                else if(s.charAt(i) == 'N'){   //操作数，NaN，表示未定义的运算结果，如0/0

                    units.add(new Unit(Double.NaN));
                    i += 2;
                }
                else if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){   //操作数

                    double val = s.charAt(i) - '0';
                    boolean hasPoint = false;   //是否出现小数点
                    double level = 0.1;        //小数部分的单位
                    int k;
                    for(k = i+1; k < s.length(); k++){
                        if(s.charAt(k) >= '0' && s.charAt(k) <= '9'){
                            if(hasPoint){
                                val = val + (s.charAt(k) - '0') * level;
                                level *= 0.1;
                            }else{
                                val = val * 10 + (s.charAt(k) - '0');
                            }
                        }else if(s.charAt(k) == '.'){
                            if(hasPoint){
                                throw new ExpressionIllegalException();
                            }
                            hasPoint = true;
                        }else{
                            break;
                        }
                    }
                    i = k-1;
                    units.add(new Unit(val));
                }
            }
            else{      //运算符
                if(c == '-' && flag){
                    flag = false;
                    units.add(new Unit(Type.NEGATIVE));
                }else if(c == '+' && flag){
                    flag = false;
                    units.add(new Unit(Type.POSITIVE));
                }else{
                    switch(c){
                        case '+':
                            units.add(new Unit(Type.ADD));
                            break;
                        case '-':
                            units.add(new Unit(Type.SUBSTRACT));
                            break;
                        case '*':
                            units.add(new Unit(Type.MULTIPLY));
                            break;
                        case '/':
                            units.add(new Unit(Type.DIVIDE));
                            break;
                        case '^':
                            units.add(new Unit(Type.POWER));
                            break;
                        case '!':
                            units.add(new Unit(Type.FACTORIAL));
                            break;
                        case '(':
                            units.add(new Unit(Type.LEFT_BRACKET));
                            break;
                        case ')':
                            units.add(new Unit(Type.RIGHT_BRACKET));
                            break;
                        case '#':
                            units.add(new Unit(Type.START_STOP_SIGN));
                            break;
                        case '.':
                            throw new ExpressionIllegalException();
                    }
                    flag = (c == '*' || c == '/' || c == '(' || c == '#');
                }
            }
        }

        return units;
    }

    public static String calculate(String s) throws ExpressionIllegalException{

        List<Unit> units = parseUnits("#" + s + "#");
        debug("units.size()=" + units.size());
        Stack<Unit> operators = new Stack<Unit>();
        Stack<Unit> operands = new Stack<Unit>();

        int index = 0;
        while(index < units.size() || operators.size() != 0){

            Unit unit = units.get(index);
            if(unit.getType() == Type.OPERAND){
                operands.push(unit);
                debug(unit + "入栈");
                index += 1;
            }else{
                if(operators.empty() || compareOperator(operators.peek(), unit) == '<'){
                    operators.push(unit);
                    debug(unit + "入栈");
                    index += 1;
                }else if(compareOperator(operators.peek(), unit) == '='){
                    Unit top = operators.pop();
                    debug(top + "出栈");
                    index += 1;
                }else if(compareOperator(operators.peek(), unit) == '>'){
                    Unit top = operators.pop();
                    debug(top + "出栈");
                    if(top.isUnary() && operands.size() >= 1){
                        Unit operand1 = operands.pop();
                        debug(operand1 + "出栈");
                        Unit res = top.operate(operand1);
                        operands.push(res);
                        debug(res + "入栈");
                    }else if(top.isBinary() && operands.size() >= 2){
                        Unit operand2 = operands.pop();
                        Unit operand1 = operands.pop();
                        debug(operand2 + "出栈");
                        debug(operand1 + "出栈");
                        Unit res = top.operate(operand1, operand2);
                        operands.push(res);
                        debug(res + "入栈");
                    }else{
                        throw new ExpressionIllegalException();
                    }
                }else{
                    throw new ExpressionIllegalException();
                }
            }
        }
        double result = operands.peek().getVal();
        return Double.isNaN(result)?"NaN":new DecimalFormat("#.##############").format(result);
    }

    private static boolean DEBUG = true;

    private static void debug(String msg){
        if(DEBUG){
            System.out.println(msg);
        }
    }

    public static class ExpressionIllegalException extends Exception{
        public ExpressionIllegalException() {
            super("Error");
        }
    }

    public static void main(String[] args) throws ExpressionIllegalException{
        String s = "1+3";
        try{
            System.out.println(s + "=" + Calculator.calculate(s));
        }catch(ExpressionIllegalException e){
            System.out.println(e.getMessage());
        }

    }

}
