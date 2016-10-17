package cn.zhikaizhang;

import org.apache.commons.math3.special.Gamma;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Operation {

    public static double add(double n1, double n2){

        if(Double.isInfinite(n1 + n2) || Double.isNaN(n1 + n2)){
            return n1 + n2;
        }else{
            return new BigDecimal(String.valueOf(n1)).add(new BigDecimal(String.valueOf(n2))).doubleValue();
        }
    }

    public static double substract(double n1, double n2){

        if(Double.isInfinite(n1 - n2) || Double.isNaN(n1 - n2)){
            return n1 - n2;
        }else{
            return new BigDecimal(String.valueOf(n1)).subtract(new BigDecimal(String.valueOf(n2))).doubleValue();
        }
    }

    public static double multiply(double n1, double n2){

        if(Double.isInfinite(n1 * n2) || Double.isNaN(n1 * n2)){
            return n1 * n2;
        }else{
            return new BigDecimal(String.valueOf(n1)).multiply(new BigDecimal(String.valueOf(n2))).doubleValue();
        }
    }

    public static double divide(double n1, double n2){

        if(Double.isInfinite(n1 / n2) || Double.isNaN(n1 / n2)){
            return n1 / n2;
        }else{
            return new BigDecimal(String.valueOf(n1)).divide(new BigDecimal(String.valueOf(n2)), 14, RoundingMode.HALF_DOWN).doubleValue();
        }
    }

    public static double positive(double n1){

        return n1;
    }

    public static double negative(double n1){

        return -n1;
    }

    public static double power(double n1, double n2){

        if(Double.isInfinite(Math.pow(n1, n2)) || Double.isNaN(Math.pow(n1, n2)) || n2 != (int)n2 || n2 < 0 || n2 > 999999999){
            return Math.pow(n1, n2);
        }else{
            return new BigDecimal(String.valueOf(n1)).pow((int)n2).doubleValue();
        }
    }

    public static double factorial(double n1){

        return Gamma.gamma(n1 + 1);
    }

}
