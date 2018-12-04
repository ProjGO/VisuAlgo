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
        FXMLLoader loader=new FXMLLoader(Main.class.getResource("TreeScene.fxml"));
        AnchorPane root = loader.load();
        TreeController controller=loader.getController();
        controller.setAnchorPane(root);
        primaryStage.setTitle("VisuAlgo");
        primaryStage.setScene(new Scene(root, 1280, 720));

        //root.setOnMouseClicked(new ClickHandler(root));

        AnimationGenerator.setRate(3.0);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}



/*

        BasicVisuNode bNode1=new BasicVisuNode(100,0,1,true);
        BasicVisuNode bNode2=new BasicVisuNode(00,300,2,true);
        bNode2.setDragable();
        bNode1.setDragable();

        BasicVisuNode bNode3=new BasicVisuNode(500,500,3,true);
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


//VisuAVLTree visuAVLTree=new VisuAVLTree(root);

//int[] a=new int[]{6,5,4,3,2,1};

//for(int i=0;i<5;i++)
//    visuAVLTree.insert(a[i]);
//visuAVLTree.find(3);
//visuAVLTree.findMax(visuAVLTree.getRoot());
//visuAVLTree.delete(2);
//visuAVLTree.LRRotation(visuAVLTree.root,true);
//visuAVLTree.getAllAnimation().play();

        /*BasicVisuNode bNode=new BasicVisuNode(100,100,10,true);
        root.getChildren().add(bNode);
        bNode.getEmphasizeAnimation().play();*/