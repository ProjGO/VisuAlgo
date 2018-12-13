package UI;

import BasicAnimation.AnimationGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("GraphScene.fxml"));
        AnchorPane root = loader.load();
        GraphController controller=loader.getController();
        primaryStage.setTitle("VisuAlgo");
        primaryStage.setScene(new Scene(root, 1280, 720));

        AnimationGenerator.setRate(3.0);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}