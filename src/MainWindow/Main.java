package MainWindow;

import BasicAnimation.AnimationGenerator;
import cn.edu.bit.cs.VisuAlgo.VisualElements.BasicNodeByGroup;
import cn.edu.bit.cs.VisuAlgo.VisualElements.UndirectedEdgeWithDataBinding;
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VisuAlgo");
        primaryStage.setScene(new Scene(root, 1280, 720));

        BasicNodeByGroup bNode1=new BasicNodeByGroup(0,0,20,"1");
        BasicNodeByGroup bNode2=new BasicNodeByGroup(200,300,20,"2");
        bNode2.setDragable();
        bNode1.setDragable();

        BasicNodeByGroup bNode3=new BasicNodeByGroup(500,500,20,"3");
        bNode3.setPosition(600,600);

        //root.getChildren().addAll(/*new UndirectedEdge(bNode1,bNode2,5),*/bNode1,bNode2);
        root.getChildren().addAll(new UndirectedEdgeWithDataBinding(bNode1,bNode2,3),bNode1,bNode2,bNode3);

        Timeline move1=AnimationGenerator.getMoveAnimation(bNode1,2000,100,300);
        Timeline move2=AnimationGenerator.getMoveAnimation(bNode2,2000,300,300);


        SequentialTransition emp=bNode1.getEmphasizeAnimation(1000,3);

        FadeTransition disa=AnimationGenerator.getDisappearAnimation(bNode1,2000);

        SequentialTransition seq=new SequentialTransition(emp,move1,move2);

        seq.play();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
