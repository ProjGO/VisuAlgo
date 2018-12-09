package BasicVisuDS;

import BasicAnimation.AnimationGenerator;
import BasicAnimation.AnimationManager;
import VisualElements.Edge.Edge;
import VisualElements.Node.BasicVisuNode;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class GraphNode {
    static private AnchorPane scene;
    static private AnimationManager animationManager;

    BasicVisuNode visuNode;
    public ArrayList<EdgeAndNode> out=new ArrayList<>(),in=new ArrayList<>();//出/入边及这条边另一端的节点
    boolean visited=false;

    private SimpleDoubleProperty layoutX=new SimpleDoubleProperty(),layoutY=new SimpleDoubleProperty();

    GraphNode(double layoutX, double layoutY, int id){
        visuNode=new BasicVisuNode(layoutX,layoutY,id,true);
        scene.getChildren().add(visuNode);
        this.layoutX.bind(visuNode.layoutXProperty());
        this.layoutY.bind(visuNode.layoutYProperty());
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(visuNode));
    }

    public void addAdjacentNode(GraphNode node,Edge edge){
        out.add(new EdgeAndNode(node,edge));
        in.add(new EdgeAndNode(node,edge));
    }

    public void addAdjacentNode(GraphNode node,Edge edge,boolean isFromNode){
        if(isFromNode)
            out.add(new EdgeAndNode(node,edge));
        else
            in.add(new EdgeAndNode(node,edge));
    }

    private void removeInEdgeAndNode(GraphNode node){
        for(int i=0;i<in.size();i++)
            if(in.get(i).node==node){
                in.remove(i);
                break;
            }
    }

    private void removeOutEdgeAndNode(GraphNode node){
        for(int i=0;i<out.size();i++)
            if(out.get(i).node==node){
                out.remove(i);
                break;
            }
    }

    public void delete(){//删除节点及相连的边并产生动画并更新相邻节点的信息
        ParallelTransition deleteAnima=new ParallelTransition();

        for(int i=0;i<out.size();i++) {
            out.get(i).node.removeInEdgeAndNode(this);
            Animation edgeDisappear= AnimationGenerator.getDisappearAnimation(out.get(i).edge);
            final int fi=i;//lambda中只能用final...
            edgeDisappear.setOnFinished(e->scene.getChildren().remove(out.get(fi).edge));
            deleteAnima.getChildren().add(edgeDisappear);
        }
        for(int i=0;i<in.size();i++) {
            in.get(i).node.removeOutEdgeAndNode(this);
            Animation edgeDisappear= AnimationGenerator.getDisappearAnimation(in.get(i).edge);
            final int fi=i;
            edgeDisappear.setOnFinished(e->scene.getChildren().remove(in.get(fi).edge));
            deleteAnima.getChildren().add(edgeDisappear);
        }
        Animation nodeDisappear=AnimationGenerator.getDisappearAnimation(visuNode);
        nodeDisappear.setOnFinished(e->scene.getChildren().removeAll(visuNode));
        deleteAnima.getChildren().add(nodeDisappear);

        animationManager.addNewAnimation(deleteAnima);
    }

    static void setAnchorPane(AnchorPane anchorPane){
        scene=anchorPane;
    }

    static void setAnimationManager(AnimationManager am){
        animationManager=am;
    }

    public BasicVisuNode getVisuNode(){
        return visuNode;
    }

    public Edge getOutEdge(int idx){
        return out.get(idx).edge;
    }

    public GraphNode getOutNode(int idx){
        return out.get(idx).node;
    }

    public boolean isVisited(){
        return visited;
    }

    public void setVisited(boolean visited){
        this.visited=visited;
    }

    public Animation getSelectedAnimation(){
        return visuNode.getSelectedAnimation();
    }

    public Animation getUnselectedAnimation(){
        return visuNode.getUnselectedAnimation();
    }

    class EdgeAndNode{
        GraphNode node;
        Edge edge;
        EdgeAndNode(GraphNode node,Edge edge){
            this.node=node;
            this.edge=edge;
        }
    }
}
