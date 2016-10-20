package cn.zhikaizhang.algorithm;

import java.util.ArrayList;
import java.util.List;
import cn.zhikaizhang.algorithm.ExpressionIllegalException;

/**
 * 表达式分析器
 */
public class ExpressionParser {


    /**
     * 分析表达式，转化为广义运算符和操作数的数组
     */
    public static List<Unit> parse(String expression) throws ExpressionIllegalException {

        String s = "#" + expression + "#";

        List<Unit> units = new ArrayList<Unit>();
        /**
         * 若一个Unit是乘号或除号或左括号或井号，flag置为true，否则置为false
         * 若一个Unit是+，且flag为true，说明该+号表示正号
         * 若一个Unit是-，且flag为true，说明该-号表示负号
         */
        boolean flag = true;

        for(int i = 0; i < s.length(); i++){

            char c = s.charAt(i);
            if(c == '∞' || c == 'N' || c == 'p' ||c == 'e' || c >= '0' && c <= '9'){   //操作数
                flag = false;
                if(s.charAt(i) == '∞'){   //操作数，∞无穷,用于处理上次运算结果为无穷然后继续运算的情形

                    units.add(new Unit(Double.POSITIVE_INFINITY));
                }
                else if(s.charAt(i) == 'N'){   //操作数，NaN，表示未定义的运算结果，如0/0

                    units.add(new Unit(Double.NaN));
                    i += 2;
                }
                else if(s.charAt(i) == 'p'){

                    units.add(new Unit(Math.PI));
                    i += 1;
                }
                else if(s.charAt(i) == 'e'){

                    units.add(new Unit(Math.E));
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

                    //数字与pi、e直接相连,补上乘号
                    if(s.charAt(i+1) == 'p'){
                        units.add(new Unit(Unit.Type.MULTIPLY));
                        units.add(new Unit(Math.PI));
                        i += 2;
                    }else if(s.charAt(i+1) == 'e'){
                        units.add(new Unit(Unit.Type.MULTIPLY));
                        units.add(new Unit(Math.E));
                        i += 1;
                    }
                }
            }
            else{      //运算符
                if(c == '-' && flag){
                    flag = false;
                    units.add(new Unit(Unit.Type.NEGATIVE));
                }else if(c == '+' && flag){
                    flag = false;
                    units.add(new Unit(Unit.Type.POSITIVE));
                }else{
                    switch(c){
                        case '+':
                            units.add(new Unit(Unit.Type.ADD));
                            break;
                        case '-':
                            units.add(new Unit(Unit.Type.SUBSTRACT));
                            break;
                        case 's':
                            units.add(new Unit(Unit.Type.SIN));
                            i += 2;
                            break;
                        case 'c':
                            units.add(new Unit(Unit.Type.COS));
                            i += 2;
                            break;
                        case 't':
                            units.add(new Unit(Unit.Type.TAN));
                            i += 2;
                            break;
                        case 'l':
                            if(s.charAt(i+1) == 'n'){
                                units.add(new Unit(Unit.Type.LN));
                                i += 1;
                            }else if(s.charAt(i+1) == 'o'){
                                units.add(new Unit(Unit.Type.LOG));
                                i += 2;
                            }
                            break;
                        case '*':
                            units.add(new Unit(Unit.Type.MULTIPLY));
                            break;
                        case '/':
                            units.add(new Unit(Unit.Type.DIVIDE));
                            break;
                        case 'E':
                            units.add(new Unit(Unit.Type.EE));
                            break;
                        case '^':
                            units.add(new Unit(Unit.Type.POWER));
                            break;
                        case '!':
                            units.add(new Unit(Unit.Type.FACTORIAL));
                            break;
                        case '(':
                            units.add(new Unit(Unit.Type.LEFT_BRACKET));
                            break;
                        case ')':
                            units.add(new Unit(Unit.Type.RIGHT_BRACKET));
                            break;
                        case '#':
                            units.add(new Unit(Unit.Type.START_STOP_SIGN));
                            break;
                        case '.':
                            throw new ExpressionIllegalException();
                    }
                    flag = (c == '*' || c == '/' || c == '^' || c == '(' || c == '#' || c == 's' || c == 'c' || c == 't' || c == 'l' || c == 'E');
                }
            }
        }

        return units;
    }
}
