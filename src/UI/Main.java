package UI;

import Algorithm.VisuAVLTree;
import Algorithm.VisuBSTree;
import BasicAnimation.AnimationGenerator;
import VisualElements.Node.BasicNode;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("VisuAlgo");
        primaryStage.setScene(new Scene(root, 1280, 720));

        //root.setOnMouseClicked(new ClickHandler(root));

        AnimationGenerator.setRate(3.0);

        VisuAVLTree visuAVLTree=new VisuAVLTree(root);

        int[] a=new int[]{5,4,3,2,1};

        for(int i=0;i<5;i++)
            visuAVLTree.insert(a[i]);
        //visuAVLTree.find(3);
        //visuAVLTree.findMax(visuAVLTree.getRoot());
        //visuAVLTree.delete(2);
        visuAVLTree.LLRotation(visuAVLTree.root.leftChild.leftChild,true);
        visuAVLTree.getAllAnimation().play();

        /*BasicNode bNode=new BasicNode(100,100,10,true);
        root.getChildren().add(bNode);
        bNode.getEmphasizeAnimation().play();*/


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}

class ClickHandler implements EventHandler<MouseEvent>{

    AnchorPane root;

    public ClickHandler(AnchorPane root){
        this.root=root;
    }

    @Override
    public void handle(MouseEvent e){
        if(e.getEventType()==MouseEvent.MOUSE_CLICKED&&e.getButton()== MouseButton.SECONDARY) {
            BasicNode basicNode=new BasicNode(e.getSceneX(), e.getSceneY(), 5, true);
            root.getChildren().add(basicNode);
            AnimationGenerator.getAppearAnimation(basicNode).play();
        }
    }
}



/*

        BasicNode bNode1=new BasicNode(100,0,1,true);
        BasicNode bNode2=new BasicNode(00,300,2,true);
        bNode2.setDragable();
        bNode1.setDragable();

        BasicNode bNode3=new BasicNode(500,500,3,true);
        bNode3.setDragable();

        //root.getChildren().addAll(new UndirectedEdge(bNode1.layoutXProperty(),bNode1.layoutYProperty(),bNode2.layoutXProperty(),bNode2.layoutYProperty(),3),bNode1,bNode2,bNode3);
        UnwDirEdge e13=new UnwDirEdge(bNode1,bNode3);
        root.getChildren().add(e13);
        root.getChildren().addAll(new WDirEdge(bNode2,bNode3,13),bNode1,bNode2,bNode3);

        Timeline move1=AnimationGenerator.getMoveAnimation(bNode1,2000,100,300);
        Timeline move2=AnimationGenerator.getMoveAnimation(bNode2,2000,300,300);


        SequentialTransition emp=bNode1.getEmphasizeAnimation(1000,3);


        Timeline changeToNode=AnimationGenerator.changeEdgeFromNode(root,e13,300,300,bNode2,2000);
        SequentialTransition seq=new SequentialTransition(emp,move2,changeToNode);

 */