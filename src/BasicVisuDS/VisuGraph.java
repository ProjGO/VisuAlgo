package BasicVisuDS;

import BasicAnimation.AnimationGenerator;
import Parameters.Parameters;
import VisualElements.Edge.Edge;
import javafx.animation.ParallelTransition;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;

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
        return getEdge(fromNodeIdx,toNodeIdx)!=null;
    }

    private GraphNode.EdgeAndNode getEdge(int fromNodeIdx, int toNodeIdx){
        for(int i=0;i<nodes.get(fromNodeIdx).out.size();i++) {
            if (nodes.get(fromNodeIdx).out.get(i).node == nodes.get(toNodeIdx))
                return nodes.get(fromNodeIdx).out.get(i);
        }
        return null;
    }

    public void addDirEdge(int fromNodeIdx, int toNodeIdx, Edge edge) throws VisuGraphException {
        if(haveEdge(fromNodeIdx, toNodeIdx))
            throw(new VisuGraphException("这两点之间已存在边"));
        if(haveEdge(toNodeIdx,fromNodeIdx))
            throw(new VisuGraphException("这两点之间已存在反向边，请添加无向边"));
        nodes.get(fromNodeIdx).addAdjacentNode(nodes.get(toNodeIdx),edge,true);
        nodes.get(toNodeIdx).addAdjacentNode(nodes.get(fromNodeIdx),edge,false);
    }

    public void addUDirEdge(int fromNodeIdx,int toNodeIdx,Edge edge) throws VisuGraphException{
        if(haveEdge(fromNodeIdx,toNodeIdx)&&haveEdge(toNodeIdx,fromNodeIdx))
            throw(new VisuGraphException("这两点间已存在无向边"));
        if(haveEdge(fromNodeIdx, toNodeIdx)||haveEdge(toNodeIdx,fromNodeIdx)) {
            GraphNode.EdgeAndNode existEdge=null;
            if (haveEdge(fromNodeIdx, toNodeIdx))
                existEdge = getEdge(fromNodeIdx, toNodeIdx);
            else
                existEdge=getEdge(toNodeIdx,fromNodeIdx);
            animationManager.addNewAnimation(AnimationGenerator.getDisappearAnimation(existEdge.edge));
            anchorPane.getChildren().remove(existEdge.edge);
            nodes.get(fromNodeIdx).out.remove(existEdge);
            nodes.get(toNodeIdx).in.remove(existEdge);
        }
        nodes.get(fromNodeIdx).addAdjacentNode(nodes.get(toNodeIdx),edge);
        nodes.get(toNodeIdx).addAdjacentNode(nodes.get(fromNodeIdx),edge);
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

    public void reset(){//将所有节点的visited设为false,并生成取消所有节点的选中的动画加入到animationManager中
        ParallelTransition unselAllNodes=new ParallelTransition();
        for(int i=0;i<nodes.size();i++) {
            nodes.get(i).setVisited(false);
            unselAllNodes.getChildren().add(nodes.get(i).getUnselectedAnimation());
        }
        animationManager.addNewAnimation(unselAllNodes);
    }

    public void showAllDist(){
        ParallelTransition distAppear=new ParallelTransition();
        for(int i=0;i<nodes.size();i++)
            distAppear.getChildren().add(nodes.get(i).getVisuNode().setDistVisibleAnima());
        addNewAnimation(distAppear);
    }

    public GraphNode getNode(int idx){
        return nodes.get(idx);
    }

    public ArrayList<GraphNode> getNodes(){
        return nodes;
    }

}