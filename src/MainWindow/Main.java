package MainWindow;

import BasicAnimation.AnimationGenerator;
import BasicAnimation.AnimationPlayerController;
import cn.edu.bit.cs.VisuAlgo.VisualElements.BasicNode;
import cn.edu.bit.cs.VisuAlgo.VisualElements.UndirectedEdge;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.midi.SoundbankResource;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VisuAlgo");
        primaryStage.setScene(new Scene(root, 400, 400));

        BasicNode bNode=new BasicNode(20,20,20,"123");
        root.getChildren().add(bNode);

        BasicNode bNode1=new BasicNode(100,100,10,"12");
        BasicNode bNode2=new BasicNode(50,60,10,"13");
        root.getChildren().addAll(new UndirectedEdge(bNode,bNode2,5),bNode1,bNode2);

        TranslateTransition move= AnimationGenerator.getMoveAnimation(bNode,2000,100,100);

        SequentialTransition emp=bNode.getEmphasizeAnimation(1000,3);

        FadeTransition disa=AnimationGenerator.getDisappearAnimation(bNode,2000);

        SequentialTransition seq=new SequentialTransition(emp,move,disa);
        seq.play();

        System.out.println(emp.getCuePoints());
        System.out.println(move.getCuePoints());

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
