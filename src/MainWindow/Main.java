package MainWindow;

import BasicAnimation.AnimationGenerator;
import cn.edu.bit.cs.VisuAlgo.VisualElements.BasicNodeByGroup;
import cn.edu.bit.cs.VisuAlgo.VisualElements.UndirectedEdgeWithDataBinding;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VisuAlgo");
        primaryStage.setScene(new Scene(root, 1280, 720));

        BasicNodeByGroup bNode1=new BasicNodeByGroup(150,200,20,"1");
        BasicNodeByGroup bNode2=new BasicNodeByGroup(1000,500,20,"2");

        //root.getChildren().addAll(/*new UndirectedEdge(bNode1,bNode2,5),*/bNode1,bNode2);
        root.getChildren().addAll(new UndirectedEdgeWithDataBinding(bNode1,bNode2,3),bNode1,bNode2);

        TranslateTransition move1= AnimationGenerator.getMoveAnimation(bNode1,2000,500,300);
        TranslateTransition move2=AnimationGenerator.getMoveAnimation(bNode1,2000,300,300);
        System.out.println(move1.getFromX());
        System.out.println(move1.getFromY());

        SequentialTransition emp=bNode1.getEmphasizeAnimation(1000,3);

        FadeTransition disa=AnimationGenerator.getDisappearAnimation(bNode1,2000);

        SequentialTransition seq=new SequentialTransition(emp,move1,move2);
        System.out.println(bNode1.getCenterX());
        System.out.println(bNode1.getCenterY());
        seq.play();
        System.out.println(bNode1.getCenterX());
        System.out.println(bNode1.getCenterY());

        //System.out.println(bnode);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
