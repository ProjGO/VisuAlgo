package MainWindow;

import BasicAnimation.AnimationGenerator;
import cn.edu.bit.cs.VisuAlgo.VisualElements.BasicNode;
import cn.edu.bit.cs.VisuAlgo.VisualElements.UndirectedEdge;
import cn.edu.bit.cs.VisuAlgo.VisualElements.*;
import javafx.animation.*;
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

        BasicNode bNode1=new BasicNode(0,0,20,"1");
        BasicNode bNode2=new BasicNode(200,300,20,"2");
        bNode2.setDragable();
        bNode1.setDragable();

        BasicNode bNode3=new BasicNode(500,500,20,"3");
        bNode3.setPosition(600,600);

        //root.getChildren().addAll(new UndirectedEdge(bNode1.layoutXProperty(),bNode1.layoutYProperty(),bNode2.layoutXProperty(),bNode2.layoutYProperty(),3),bNode1,bNode2,bNode3);
        root.getChildren().addAll(new WeightedUndirectedEdge(bNode1,bNode2,3,5),bNode1,bNode2,bNode3);

        Timeline move1=AnimationGenerator.getMoveAnimation(bNode1,2000,100,300);
        Timeline move2=AnimationGenerator.getMoveAnimation(bNode2,2000,300,300);


        SequentialTransition emp=bNode1.getEmphasizeAnimation(1000,3);

        FadeTransition disa=AnimationGenerator.getDisappearAnimation(bNode1,2000);

        SequentialTransition seq=new SequentialTransition(emp,move1,move2);

        AnimationGenerator.setRate(2.0f);

        seq.play();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
