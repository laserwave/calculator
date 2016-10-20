package cn.zhikaizhang.algorithm;

public class ExpressionIllegalException extends Exception {

    public ExpressionIllegalException(String message) {
        super(message);
    }

    public ExpressionIllegalException() {
        this("Error");
    }
}