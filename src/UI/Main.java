package UI;

import BasicAnimation.AnimationGenerator;
import VisualElements.Node.BasicNode;
import VisualElements.Column.Column;
import VisualElements.Edge.UnwDirEdge;
import VisualElements.Edge.WDirEdge;
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VisuAlgo");
        primaryStage.setScene(new Scene(root, 1280, 720));

        BasicNode bNode1=new BasicNode(100,0,"1");
        BasicNode bNode2=new BasicNode(00,300,"2");
        bNode2.setDragable();
        bNode1.setDragable();

        BasicNode bNode3=new BasicNode(500,500,"3");
        bNode3.setDragable();

        //root.getChildren().addAll(new UndirectedEdge(bNode1.layoutXProperty(),bNode1.layoutYProperty(),bNode2.layoutXProperty(),bNode2.layoutYProperty(),3),bNode1,bNode2,bNode3);
        UnwDirEdge e13=new UnwDirEdge(bNode1,bNode3);
        root.getChildren().add(e13);
        root.getChildren().addAll(new WDirEdge(bNode2,bNode3,13),bNode1,bNode2,bNode3);

        Column column=new Column(500,500,50, Color.BEIGE);
        root.getChildren().add(column);

        Timeline move1=AnimationGenerator.getMoveAnimation(bNode1,2000,100,300);
        Timeline move2=AnimationGenerator.getMoveAnimation(bNode2,2000,300,300);


        SequentialTransition emp=bNode1.getEmphasizeAnimation(1000,3);

        //changeToNode.play();

        Timeline changeToNode=AnimationGenerator.changeEdgeFromNode(root,e13,300,300,bNode2,2000);
        SequentialTransition seq=new SequentialTransition(emp,move2,changeToNode);

        AnimationGenerator.setRate(3.0);

        seq.play();


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
