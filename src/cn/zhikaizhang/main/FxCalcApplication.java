package cn.zhikaizhang.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Map;

public class FxCalcApplication extends Application {

    private Parent root;
    private Scene scene;

    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage stage) throws Exception{

        root = FXMLLoader.load(getClass().getResource("/calculator.fxml"));
        scene = new Scene(root, 390, 512, Color.TRANSPARENT);
        scene.getStylesheets().add("skin.css");
        FxCalcLookAndFeel.attach(scene);
        stage.setScene(scene);
        setFxCalcInteractionListeners(stage);
        stage.setTitle("计算器");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(FxCalcApplication.class.getResourceAsStream("/images/logo.png")));
        stage.setResizable(false);
        stage.show();
    }



    public void setFxCalcInteractionListeners(final Stage stage){

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        root.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {

                scrollFxCalcHistory(event.getDeltaY()>0?-1:1);
            }
        });

        Button iconify = (Button)root.lookup("#iconify");
        iconify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setIconified(!stage.isIconified());
            }
        });

        Button close = (Button)root.lookup("#close");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

    }

    /**
     *  滚动计算器历史记录
     */
    private void scrollFxCalcHistory(int scroll){
        if(FxCalcMainController.position + scroll < 0){
            return;
        }
        Label[] labels = new Label[]{(Label)scene.lookup("#LABEL1"), (Label)scene.lookup("#LABEL2"), (Label)scene.lookup("#LABEL3"), (Label)scene.lookup("#LABEL4")};
        if(FxCalcMainController.history.size() - 1 >= FxCalcMainController.position + scroll + 1){
            FxCalcMainController.position += scroll;
            setHistoryLabelText(labels[0], labels[1], FxCalcMainController.position);
            setHistoryLabelText(labels[2], labels[3], FxCalcMainController.position + 1);
        }
    }

    private void setHistoryLabelText(Label l1, Label l2, int index){
        Map<String, String> map = FxCalcMainController.history.get(index);
        String expression = map.get("expression");
        String result = map.get("result");
        l1.setText(expression);
        l2.setText(result);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
