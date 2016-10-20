package cn.zhikaizhang.util;

public class Debug {

    private static boolean DEBUG = true;

    public static void debug(String msg){
        if(DEBUG){
            System.out.println(msg);
        }
    }
}
