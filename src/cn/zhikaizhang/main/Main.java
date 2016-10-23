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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Map;

public class Main extends Application {

    private Stage primaryStage;
    private Parent root;
    private Scene scene;

    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        root = FXMLLoader.load(getClass().getResource("/calculator.fxml"));
        scene = new Scene(root, 390, 512, Color.TRANSPARENT);
        scene.getStylesheets().add("skin.css");
        addButtonTouchEffect();
        addLabelTouchEffect();
        addButtonImage();
        setListener();
        primaryStage.setTitle("计算器");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/logo.png")));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void addButtonImage(){

        Map<String, String> buttonImg = AppearanceManager.buttonImg;
        for(String id : buttonImg.keySet()){
            Button button = (Button)scene.lookup(id);
            if(id.equals("#BUTTON_EQUAL")){
                setButtonImageSource(button, buttonImg.get(id), 48);
            }else{
                setButtonImageSource(button, buttonImg.get(id), 27);
            }
        }
    }

    public void addButtonTouchEffect(){

        Map<String[], String[]> touchEffect = AppearanceManager.touchEffect;

        for(String[] ids : touchEffect.keySet()){
            final String[] effects = touchEffect.get(ids);
            for(int i = 0; i < ids.length; i++){
                final Button button = (Button)scene.lookup(ids[i]);
                button.setStyle("-fx-background-color: #" + effects[0]);
                button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        button.setStyle("-fx-background-color: #" + effects[1]);
                    }
                });
                button.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        button.setStyle("-fx-background-color: #" + effects[0]);
                    }
                });
            }
        }

        final Map<String, String[]> touchEffect2 = AppearanceManager.touchEffect2;
        for(final String id : touchEffect2.keySet()){
            final Button button = (Button)scene.lookup(id);
            setButtonImageSource(button, touchEffect2.get(id)[0], 10);
            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setButtonImageSource(button, touchEffect2.get(id)[1], 10);
                }
            });
            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setButtonImageSource(button, touchEffect2.get(id)[0], 10);
                }
            });
        }
    }

    public static void setButtonImageSource(Button button, String imgPath, int width){
        button.setText(null);
        ImageView imageView = new ImageView(new Image(Main.class.getResourceAsStream(imgPath)));
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        button.setGraphic(imageView);
    }

    public void addLabelTouchEffect(){

        for(int i = 1; i <= 4; i++){

            final Rectangle rectangle = (Rectangle)scene.lookup("#rect" + i);
            final Label label = (Label)scene.lookup("#LABEL" + i);
            if(i <= 2){
                rectangle.setStyle("-fx-fill: #ffffff");
                label.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangle.setStyle("-fx-fill: #aaccff");
                    }
                });
                label.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangle.setStyle("-fx-fill: #ffffff");
                    }
                });
            }else{
                rectangle.setStyle("-fx-fill: #f6f6f6");
                label.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangle.setStyle("-fx-fill: #aaccff");
                    }
                });
                label.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangle.setStyle("-fx-fill: #f6f6f6");
                    }
                });
            }
        }

    }

    public void setListener(){

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
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        root.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {

                scrollHistory(event.getDeltaY()>0?-1:1);
            }
        });

        Button iconify = (Button)root.lookup("#iconify");
        iconify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setIconified(!primaryStage.isIconified());
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

    public void scrollHistory(int increment){
        if(Data.position + increment < 0){
            return;
        }
        int index = Data.position + increment;

        Label LABEL1 = (Label)scene.lookup("#LABEL1");
        Label LABEL2 = (Label)scene.lookup("#LABEL2");
        Label LABEL3 = (Label)scene.lookup("#LABEL3");
        Label LABEL4 = (Label)scene.lookup("#LABEL4");

        if(Data.history.size() - 1 >= index + 1){
            Data.position = index;
            Map<String, String> map = Data.history.get(index);
            String expression = map.get("expression");
            String result = map.get("result");
            LABEL1.setText(expression);
            LABEL2.setText(result);
            map = Data.history.get(index + 1);
            expression = map.get("expression");
            result = map.get("result");
            LABEL3.setText(expression);
            LABEL4.setText(result);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
