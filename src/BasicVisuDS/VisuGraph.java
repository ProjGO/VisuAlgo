package BasicVisuDS;

import Parameters.Parameters;
import VisualElements.Edge.Edge;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class VisuGraph extends VisuDS{
    protected ArrayList<GraphNode> nodes=new ArrayList<>();
    private AnchorPane anchorPane;
    private int nodeCnt=1;

    public VisuGraph(AnchorPane anchorPane){
        this.anchorPane=anchorPane;
        GraphNode.setAnchorPane(anchorPane);
        GraphNode.setAnimationManager(animationManager);
    }

    @Override
    public void setAnchorPane(AnchorPane anchorPane){
        this.anchorPane=anchorPane;
        GraphNode.setAnchorPane(anchorPane);
    }

    public void addNode(double layoutX,double layoutY){
        nodes.add(new GraphNode(layoutX,layoutY,nodeCnt++));
        animationManager.addNewAnimation(nodes.get(nodes.size()-1).visuNode.getEmphasizeAnimation());
    }

    private boolean haveEdge(int fromNodeIdx,int toNodeIdx){
        for(int i=0;i<nodes.get(fromNodeIdx).out.size();i++) {
            if (nodes.get(fromNodeIdx).out.get(i).node == nodes.get(toNodeIdx))
                return true;
        }
        return false;
    }

    public void addDirEdge(int fromNodeIdx, int toNodeIdx, Edge edge) throws VisuGraphException {
        if(haveEdge(fromNodeIdx, toNodeIdx))
            throw(new VisuGraphException("这两点之间已存在边"));
        nodes.get(fromNodeIdx).addAdjacentNode(nodes.get(toNodeIdx),edge,true);
        nodes.get(toNodeIdx).addAdjacentNode(nodes.get(fromNodeIdx),edge,false);
        anchorPane.getChildren().add(edge);
        animationManager.clear();
        animationManager.addNewAnimation(edge.getAppearAnimation(true));
        animationManager.addNewAnimation(edge.getEmphasizeAnimation());
    }

    public int getSelectedNodeIdx(double X,double Y) {//鼠标没点在节点上时返回-1
        for(int i=0;i<nodes.size();i++) {
            if (Math.pow(X - nodes.get(i).visuNode.getLayoutX(), 2) + Math.pow(Y - nodes.get(i).visuNode.getLayoutY(), 2) <= Parameters.nodeRadius*Parameters.nodeRadius)
                return i;
        }
        return -1;
    }

    public void setNodeSelected(int idx){
        animationManager.addNewAnimation(nodes.get(idx).visuNode.getSelectedAnimation());
    }

    public void setNodeUnselected(int idx){
        animationManager.addNewAnimation(nodes.get(idx).visuNode.getUnselectedAnimation());
    }

    public GraphNode getNode(int idx){
        return nodes.get(idx);
    }

}