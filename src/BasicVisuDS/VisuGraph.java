package BasicVisuDS;

import BasicAnimation.AnimationManager;
import UI.GraphController;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class VisuGraph extends VisuDS{
    private ArrayList<GraphNode> nodes=new ArrayList<>();
    private int nodeCnt=1;

    public VisuGraph(AnchorPane anchorPane){
        GraphNode.setAnchorPane(anchorPane);
        GraphNode.setAnimationManager(animationManager);
    }

    @Override
    public void setAnchorPane(AnchorPane anchorPane){
        GraphNode.setAnchorPane(anchorPane);
    }

    public void addNode(double layoutX,double layoutY){
        nodes.add(new GraphNode(layoutX,layoutY,nodeCnt++));
    }

}