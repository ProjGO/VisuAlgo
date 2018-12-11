package Algorithm;

import BasicVisuDS.GraphNode;
import BasicVisuDS.VisuGraph;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class Dijkstra {
    private VisuGraph visuGraph;
    private Text instructionText;
    private int startNodeIdx;

    public Dijkstra(VisuGraph visuGraph, Text instructionText){
        this.visuGraph=visuGraph;
        this.instructionText=instructionText;
        //this.instructionText.setText("cyka");
    }

    public void setStartNodeIdx(int idx){
        startNodeIdx=idx;
        visuGraph.addNewAnimation(visuGraph.getNode(idx).getDistChangeAnimation(0));
    }

    private void dijkstra(){
        //Queue<GraphNode> pq=new PriorityQueue<>((o1,o2)->{ return o1.getDistance()-o2.getDistance(); });
        visuGraph.getNode(startNodeIdx).setDistance(0);

        while(true){
            GraphNode curNode=null;
            int minDis=9999;
            for(GraphNode node:visuGraph.getNodes()){
                if(node.getDistance()<minDis&&!node.isVisited()) {
                    minDis=node.getDistance();
                    curNode = node;
                }
            }
            if(curNode==null)
                break;
            curNode.setVisited(true);
            SequentialTransition selectNode=new SequentialTransition();
            selectNode.getChildren().add(curNode.getVisuNode().getEmphasizeAnimation());
            selectNode.getChildren().add(curNode.getSelectedAnimation());
            GraphNode finalCurNode = curNode;
            selectNode.setOnFinished(e->instructionText.setText("节点"+ finalCurNode.getId()+"已经找到最短距离\n下面将用它松弛其它节点"));
            visuGraph.addNewAnimation(selectNode);
            for(int i=0;i<curNode.out.size();i++){
                if(!curNode.getOutNode(i).isVisited()){
                    int newDis=curNode.getDistance()+curNode.getOutEdge(i).getWeightProperty().get();
                    GraphNode nextNode=curNode.getOutNode(i);
                    if(newDis<nextNode.getDistance()){
                        nextNode.setDistance(newDis);
                        nextNode.setLast(curNode);
                        //visuGraph.addNewAnimation(gettPathTrackAnimation(curNode));
                        getPathTrackAnimation(nextNode);
                        ParallelTransition updateAnima=new ParallelTransition();
                        updateAnima.getChildren().add(nextNode.getDistChangeAnimation(newDis));
                        updateAnima.getChildren().add(nextNode.getLastNodeChangeAnimation(curNode.getId()));
                        updateAnima.setOnFinished(e->instructionText.setText("节点"+nextNode.getId()+"的最短距离经过节点"+finalCurNode.getId()+"更新为"+newDis));
                        visuGraph.addNewAnimation(updateAnima);
                    }
                }
            }
        }
    }

    public void start(){
        dijkstra();
    }

    private void getPathTrackAnimation(GraphNode curNode){
        ArrayList<Animation> pathAnimation=new ArrayList<>();
        SequentialTransition pathTrackAnimation=new SequentialTransition();
        while(curNode!=visuGraph.getNode(startNodeIdx)){
            pathAnimation.add(curNode.getEdgeToNodeAnimation(visuGraph.getEdge(curNode.getLast(),curNode)));
            curNode=curNode.getLast();
        }
        for(int i=pathAnimation.size()-1;i>=0;i--)
            visuGraph.addNewAnimation(pathAnimation.get(i));
            //pathTrackAnimation.getChildren().add(pathAnimation.get(i));
        //return pathTrackAnimation;
    }

    private Animation gettPathTrackAnimation(GraphNode curNode){
        ArrayList<Animation> pathAnimation=new ArrayList<>();
        SequentialTransition pathTrackAnimation=new SequentialTransition();
        while(curNode!=visuGraph.getNode(startNodeIdx)){
            pathAnimation.add(curNode.getEdgeToNodeAnimation(visuGraph.getEdge(curNode.getLast(),curNode)));
            curNode=curNode.getLast();
        }
        for(int i=pathAnimation.size()-1;i>=0;i--)
            pathTrackAnimation.getChildren().add(pathAnimation.get(i));
        return pathTrackAnimation;
    }

}
