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

                    if(extract(i, 3, s).equals("NaN")){
                        units.add(new Unit(Double.NaN));
                        i += 2;
                    }else{
                        throw new ExpressionIllegalException();
                    }

                }
                else if(s.charAt(i) == 'p'){

                    if(extract(i, 2, s).equals("pi")){
                        units.add(new Unit(Math.PI));
                        i += 1;
                    }else{
                        throw new ExpressionIllegalException();
                    }
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

                    //数字与pi、e、函数直接相连,补上乘号
                    if("pesctl".contains(String.valueOf(s.charAt(i+1)))){
                        units.add(new Unit(Unit.Type.MULTIPLY));
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
                            if(extract(i, 4, s).equals("sin(")){
                                units.add(new Unit(Unit.Type.SIN));
                                i += 2;
                            }else if(extract(i, 5, s).equals("sqrt(")){
                                units.add(new Unit(Unit.Type.SQRT));
                                i += 3;
                            }else{
                                throw new ExpressionIllegalException();
                            }
                            break;
                        case 'c':
                            if(extract(i, 4, s).equals("cos(")){
                                units.add(new Unit(Unit.Type.COS));
                                i += 2;
                            }else{
                                throw new ExpressionIllegalException();
                            }
                            break;
                        case 't':
                            if(extract(i, 4, s).equals("tan(")){
                                units.add(new Unit(Unit.Type.TAN));
                                i += 2;
                            }else{
                                throw new ExpressionIllegalException();
                            }
                            break;
                        case 'l':
                            if(extract(i, 3, s).equals("ln(")){
                                units.add(new Unit(Unit.Type.LN));
                                i += 1;
                            }else if(extract(i, 4, s).equals("log(")){
                                units.add(new Unit(Unit.Type.LOG));
                                i += 2;
                            }else{
                                throw new ExpressionIllegalException();
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
                        default:
                            throw new ExpressionIllegalException();
                    }
                    flag = (c == '*' || c == '/' || c == '^' || c == '(' || c == '#' || c == 'E');
                }
            }
        }

        return units;
    }

    public static String extract(int i, int num, String s) throws ExpressionIllegalException{
        if(i+num-1 > s.length()-1){
            throw new ExpressionIllegalException();
        }
        return s.substring(i, i+num);
    }

}
