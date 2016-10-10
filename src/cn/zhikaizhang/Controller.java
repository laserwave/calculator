package cn.zhikaizhang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * 使用注解获取fxml文件定义的控件以及定义事件
 */
public class Controller {

    @FXML
    private Label LABEL;

    @FXML
    public void BUTTON0_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "0");
    }

    @FXML
    public void BUTTON1_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "1");
    }

    @FXML
    public void BUTTON2_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "2");
    }

    @FXML
    public void BUTTON3_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "3");
    }

    @FXML
    public void BUTTON4_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "4");
    }

    @FXML
    public void BUTTON5_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "5");
    }

    @FXML
    public void BUTTON6_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "6");
    }

    @FXML
    public void BUTTON7_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "7");
    }

    @FXML
    public void BUTTON8_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "8");
    }

    @FXML
    public void BUTTON9_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + "9");
    }

    @FXML
    public void BUTTON_LEFT_PAREN_ONCLICK(ActionEvent event){
        press();
        String s = LABEL.getText();
        if(s != ""){
            if(s.charAt(0) == '='){
                LABEL.setText("");
            }
        }
        LABEL.setText(LABEL.getText() + "(");
    }

    @FXML
    public void BUTTON_RIGHT_PAREN_ONCLICK(ActionEvent event){
        press();
        String s = LABEL.getText();
        if(s != ""){
            if(s.charAt(0) == '='){
                LABEL.setText("");
            }
        }
        LABEL.setText(LABEL.getText() + ")");
    }


    @FXML
    public void BUTTON_CLEAR_ONCLICK(ActionEvent event){
        LABEL.setText("");
    }

    @FXML
    public void BUTTON_BACK_ONCLICK(ActionEvent event){
        press();
        String text = LABEL.getText();
        if(text != ""){
            if(text.charAt(0) == '='){
                LABEL.setText("");
            }else{
                LABEL.setText(text.substring(0, text.length()-1));
            }
        }
    }

    @FXML
    public void BUTTON_ADD_ONCLICK(ActionEvent event){
        pressOperator();
        LABEL.setText(LABEL.getText() + "+");
    }

    @FXML
    public void BUTTON_MINUS_ONCLICK(ActionEvent event){
        pressOperator();
        LABEL.setText(LABEL.getText() + "-");
    }

    @FXML
    public void BUTTON_MULTIPLY_ONCLICK(ActionEvent event){
        pressOperator();
        LABEL.setText(LABEL.getText() + "×");
    }

    @FXML
    public void BUTTON_DIVIDE_ONCLICK(ActionEvent event){
        pressOperator();
        LABEL.setText(LABEL.getText() + "÷");
    }

    @FXML
    public void BUTTON_POINT_ONCLICK(ActionEvent event){
        pressNumber();
        LABEL.setText(LABEL.getText() + ".");
    }

    @FXML
    public void BUTTON_EQUAL_ONCLICK(ActionEvent event){

        press();
        String s = LABEL.getText();
        if(s == "" || s.charAt(0) == '='){
            return;
        }
        try{
            String res = Calculator.calculate(s);
            System.out.println(res);
            LABEL.setText("=" + res);
        }catch(Calculator.ExpressionIllegalException e){
            LABEL.setText(e.getMessage());
        }


    }

    /**
     * 按下任意键时，如果label上显示的是出错提示，将出错提示清空
     */
    public void press(){
        String s = LABEL.getText();
        if(s != ""){
            if(s.length() > 5 && s.substring(0, 5).equals("Error")){
                LABEL.setText("");
            }
        }
    }

    /**
     * 按下数字键时，如果label上显示的是上一次的运算结果，先将结果清空
     */
    public void pressNumber(){
        press();
        String s = LABEL.getText();
        if(s != ""){
            if(s.charAt(0) == '='){
                LABEL.setText("");
            }
        }
    }

    /**
     * 按下运算符键时，如果label上显示的是上一次的运算结果，先将等号去除
     */
    public void pressOperator(){
        press();
        String s = LABEL.getText();
        if(s != ""){
            if(s.charAt(0) == '='){
                LABEL.setText(s.substring(1, s.length()));
            }
        }
    }

}
