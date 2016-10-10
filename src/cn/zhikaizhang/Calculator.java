package cn.zhikaizhang;

import java.text.DecimalFormat;
import java.util.*;


public class Calculator {

    /**
     * 根据优先级矩阵返回两个运算符优先级比较的结果
     * !表示不可能出现比较这两个运算符的情形，可认为输入有误
     * 乘法接受*或×两种符号
     * 除法接受/或÷两种符号
     */
    public static char compareOperator(char op1, char op2){

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('+', 0);
        map.put('-', 1);
        map.put('×', 2);
        map.put('*', 2);
        map.put('÷', 3);
        map.put('/', 3);
        map.put('(', 4);
        map.put(')', 5);
        map.put('#', 6);
        String[] compare = new String[]{">><<<>>", ">><<<>>", ">>>><>>", ">>>><>>", "<<<<<=!", "!!!!!!!", "<<<<<!="};
        return compare[map.get(op1)].charAt(map.get(op2));
    }


    /**
     * 将运算的字符串转化为运算符和操作数的列表，Unit表示一个运算符或一个操作数
     */
    private static List<Unit> parseUnits(String s) throws ExpressionIllegalException{

        List<Unit> units = new ArrayList<Unit>();

        /**
         * 当前操作数前面是否有负号
         * 若一个Unit是操作数，且hasSign为true，该操作数的值乘-1
         */
        boolean hasSign = false;

        /**
         * 若一个Unit是乘号或除号或左括号，flag置为true，否则置为false
         * 若一个Unit是加号，且flag为true，说明该加号表示正号
         * 若一个Unit是减号，且flag为true，说明该减号表示负号，hasSign置为true
         * flag初始值为true，表示字符串开头的加减号一定为正负号
         */
        boolean flag = true;

        for(int i = 0; i < s.length(); i++){

            if(s.charAt(i) == '.'){
                throw new ExpressionIllegalException();
            }
            /**
             * 操作数，∞无穷,用于处理上次运算结果为无穷然后继续运算的情形
             */
            else if(s.charAt(i) == '∞'){
                units.add(new Unit(hasSign?Double.NEGATIVE_INFINITY:Double.POSITIVE_INFINITY));
                hasSign = false;
                flag = false;
            }
            /**
             * 操作数，NaN，表示未定义的运算结果，如0/0
             */
            else if(s.charAt(i) == 'N'){
                units.add(new Unit(Double.NaN));
                hasSign = false;
                flag = false;
                i += 2;
            }
            /**
             * 添加表示操作数的Unit
             */
            else if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){

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
                units.add(new Unit(hasSign?-val:val));
                hasSign = false;
                flag = false;
            }
            /**
             * 正负号或者加减乘除号或者括号
             * 若是正号，置flag为false
             * 若是负号，置hasSign为true，置flag为false
             * 不是正负号，就添加一个表示运算符的Unit
             */
            else{
                if(s.charAt(i) == '-' && flag){
                    hasSign = true;
                    flag = false;
                }else if(s.charAt(i) == '+' && flag){
                    flag = false;
                }else{
                    char c = s.charAt(i);
                    units.add(new Unit(c));
                    flag = (c == '*' || c == '/' || c == '×' || c == '÷' || c == '(');
                }
            }
        }

        return units;
    }

    private static double _calculate(String s) throws ExpressionIllegalException{

        List<Unit> units = new ArrayList<Unit>();
        units.add(new Unit('#'));
        units.addAll(parseUnits(s));
        units.add(new Unit('#'));
        debug("units.size()=" + units.size());

        Stack<Unit> operators = new Stack<Unit>();
        Stack<Unit> operands = new Stack<Unit>();

        int index = 0;
        while(index < units.size() || operators.size() != 0){
            Unit unit = units.get(index);
            if(unit.getUnitType() == Unit.UnitType.OPERAND){
                operands.push(unit);
                debug(unit + "入栈");
                index += 1;
            }else if(unit.getUnitType() == Unit.UnitType.OPERATOR){
                if(operators.empty() || compareOperator(operators.peek().getOp(), unit.getOp()) == '<'){
                    operators.push(unit);
                    debug(unit + "入栈");
                    index += 1;
                }else if(compareOperator(operators.peek().getOp(), unit.getOp()) == '='){
                    Unit tmp = operators.pop();
                    debug(tmp + "出栈");
                    index += 1;
                }else if(compareOperator(operators.peek().getOp(), unit.getOp()) == '>'){
                    if(operands.size() < 2){
                        throw new ExpressionIllegalException();
                    }
                    Unit operand2 = operands.pop();
                    Unit operand1 = operands.pop();
                    Unit operator = operators.pop();
                    debug(operand2 + "出栈");
                    debug(operand1 + "出栈");
                    debug(operator + "出栈");
                    Unit res = operator.operate(operand1, operand2);
                    operands.push(res);
                    debug(res + "入栈");
                }else{
                    throw new ExpressionIllegalException();
                }
            }
        }

        double res = operands.peek().getVal();
        return res;

    }

    public static String calculate(String s) throws ExpressionIllegalException{

        double res = _calculate(s);
        return Double.isNaN(res)?"NaN":new DecimalFormat("#.##############").format(res);
    }



    private static boolean DEBUG = true;

    private static void debug(String msg){
        if(DEBUG){
            System.out.println(msg);
        }
    }

    public static class ExpressionIllegalException extends Exception{
        public ExpressionIllegalException() {
            super("Error:表达式不合法");
        }
    }


    public static void main(String[] args) {

        String s = "-0.2+1.5*4*(4-2/4)";
        try{
            System.out.println(s + "=" + Calculator.calculate(s));
        }catch(ExpressionIllegalException e){
            System.out.println(e.getMessage());
        }

    }

}
