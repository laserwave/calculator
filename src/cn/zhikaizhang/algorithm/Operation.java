package cn.zhikaizhang.algorithm;

import org.apache.commons.math3.special.Gamma;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * 运算
 */
public class Operation {

    /**
     * 加
     */
    public static double add(double n1, double n2){

        if(Double.isInfinite(n1 + n2) || Double.isNaN(n1 + n2)){
            return n1 + n2;
        }else{
            return new BigDecimal(String.valueOf(n1)).add(new BigDecimal(String.valueOf(n2))).doubleValue();
        }
    }

    /**
     * 减
     */
    public static double substract(double n1, double n2){

        if(Double.isInfinite(n1 - n2) || Double.isNaN(n1 - n2)){
            return n1 - n2;
        }else{
            return new BigDecimal(String.valueOf(n1)).subtract(new BigDecimal(String.valueOf(n2))).doubleValue();
        }
    }

    /**
     * 乘
     */
    public static double multiply(double n1, double n2){

        if(Double.isInfinite(n1 * n2) || Double.isNaN(n1 * n2)){
            return n1 * n2;
        }else{
            return new BigDecimal(String.valueOf(n1)).multiply(new BigDecimal(String.valueOf(n2))).doubleValue();
        }
    }

    /**
     * 除
     */
    public static double divide(double n1, double n2){

        if(Double.isInfinite(n1 / n2) || Double.isNaN(n1 / n2)){
            return n1 / n2;
        }else{
            return new BigDecimal(String.valueOf(n1)).divide(new BigDecimal(String.valueOf(n2)), 14, RoundingMode.HALF_DOWN).doubleValue();
        }
    }

    /**
     * EE
     */
    public static double ee(double n1, double n2){

        return multiply(n1, power(10, n2));
    }

    /**
     * 正
     */
    public static double positive(double n1){

        return n1;
    }

    /**
     * 负
     */
    public static double negative(double n1){

        return -n1;
    }

    /**
     * 乘方
     */
    public static double power(double n1, double n2){

        if(Double.isInfinite(Math.pow(n1, n2)) || Double.isNaN(Math.pow(n1, n2)) || n2 != (int)n2 || n2 < 0 || n2 > 999999999){
            return Math.pow(n1, n2);
        }else{
            return new BigDecimal(String.valueOf(n1)).pow((int)n2).doubleValue();
        }
    }

    /**
     * 阶乘
     */
    public static double factorial(double n1){

        return Gamma.gamma(n1 + 1);
    }

    /**
     * sin
     */
    public static double sin(double n1){
        return Math.sin(n1);
    }

    /**
     * cos
     */
    public static double cos(double n1){
        return Math.cos(n1);
    }

    /**
     * tan
     */
    public static double tan(double n1){
        return Math.tan(n1);
    }

    /**
     * ln
     */
    public static double ln(double n1){
        return Math.log(n1);
    }

    /**
     * log
     */
    public static double log(double n1){
        return Math.log10(n1);
    }
}
