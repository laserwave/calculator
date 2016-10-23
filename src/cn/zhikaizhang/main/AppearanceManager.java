package cn.zhikaizhang.main;

import java.util.HashMap;
import java.util.Map;

public class AppearanceManager {

    public static String[] name = new String[]{"POINT", "EE", "ADD", "MINUS", "MULTIPLY", "DIVIDE", "SIN", "COS", "TAN", "LOG", "LN", "LEFT_PAREN", "RIGHT_PAREN",
            "NEGATIVE", "CLEAR", "CLEAR_ALL", "BACK", "EQUAL", "PI", "E", "POWER", "FACTORIAL", "SQUARE", "SQRT", "RECIPROCAL", "EXPONENT"};

    public static Map<String[], String[]> touchEffect = new HashMap<>();

    public static Map<String, String[]> touchEffect2 = new HashMap<>();

    public static Map<String, String> buttonImg = new HashMap<>();

    static{
        touchEffect.put(new String[]{"#BUTTON0", "#BUTTON1", "#BUTTON2", "#BUTTON3", "#BUTTON4", "#BUTTON5", "#BUTTON6", "#BUTTON7", "#BUTTON8", "#BUTTON9"},
                new String[]{"ffe680", "fff5c6"});  //黄
        touchEffect.put(new String[]{"#BUTTON_SIN", "#BUTTON_COS", "#BUTTON_TAN", "#BUTTON_POWER", "#BUTTON_FACTORIAL", "#BUTTON_LOG", "#BUTTON_LN", "#BUTTON_PI", "#BUTTON_E", "#BUTTON_SQUARE", "#BUTTON_SQRT", "#BUTTON_EXPONENT", "#BUTTON_RECIPROCAL"},
                new String[]{"aaccff", "cde2ff"});  //蓝
        touchEffect.put(new String[]{"#BUTTON_LEFT_PAREN", "#BUTTON_RIGHT_PAREN", "#BUTTON_POINT", "#BUTTON_EE", "#BUTTON_NEGATIVE"}, new String[]{"ffffff", "f6f6f6"});  //白
        touchEffect.put(new String[]{"#BUTTON_ADD", "#BUTTON_MINUS", "#BUTTON_MULTIPLY", "#BUTTON_DIVIDE"}, new String[]{"b3b3b3", "e0e0e0"});  //灰
        touchEffect.put(new String[]{"#BUTTON_CLEAR_ALL", "#BUTTON_CLEAR", "#BUTTON_BACK"}, new String[]{"f09797", "e9aeae"});  //红
        touchEffect.put(new String[]{"#BUTTON_EQUAL"}, new String[]{"c6e9af", "def3d0"});  //绿

        touchEffect2.put("#iconify", new String[]{"/images/iconify1.png", "/images/iconify2.png"});
        touchEffect2.put("#close", new String[]{"/images/close1.png", "/images/close2.png"});

        for(int i = 0; i <= 9; i++){
            buttonImg.put("#BUTTON" + i, "/images/" + i + ".png");
        }
        for(int i = 0; i < name.length; i++){
            buttonImg.put("#BUTTON_" + name[i], "/images/" + name[i] + ".png");
        }


    }
}
