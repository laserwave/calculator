package cn.zhikaizhang.controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {

    private String ids0[] = new String[]{"#BUTTON0", "#BUTTON1", "#BUTTON2", "#BUTTON3", "#BUTTON4", "#BUTTON5", "#BUTTON6", "#BUTTON7", "#BUTTON8", "#BUTTON9", "#BUTTON_POINT"};
    private String ids[][] = {ids0};

    private String colors0[] = new String[]{"#ffe680", "#fff5c6"};
    private String colors[][] = {colors0};

    private double xOffset;
    private double yOffset;

    @Override
    public void start(final Stage primaryStage) throws Exception{








        Parent root = FXMLLoader.load(getClass().getResource("/calculator.fxml"));



//        root.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                xOffset = event.getSceneX();
//                yOffset = event.getSceneY();
//            }
//        });
//        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                primaryStage.setX(event.getScreenX() - xOffset);
//                primaryStage.setY(event.getScreenY() - yOffset);
//            }
//        });




        Scene scene = new Scene(root, 320, 470);
        scene.getStylesheets().add("skin.css");

        for(int i = 0; i < ids.length; i++){
            final int index = i;
            for(int j = 0; j < ids[i].length; j++){
                final Button button = (Button)scene.lookup(ids[i][j]);
                button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        button.setStyle("-fx-background-color: " + colors[index][1]);
                    }
                });
                button.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        button.setStyle("-fx-background-color: " + colors[index][0]);
                    }
                });
            }
        }



//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("计算器");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/logo.png")));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
