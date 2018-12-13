package Algorithm.Graph;

import BasicVisuDS.GraphNode;
import BasicVisuDS.VisuGraph;
import VisualElements.Edge.Edge;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Prim {
    private VisuGraph visuGraph;
    private Text instructionText;
    private int rootNodeIdx;

    public Prim(VisuGraph visuGraph,Text instructionText){
        this.visuGraph=visuGraph;
        this.instructionText=instructionText;
    }

    public void setRootNodeIdx(int idx){
        rootNodeIdx=idx;
    }

    private int prim() {
        visuGraph.getNode(rootNodeIdx).setDistance(0);
        int weight=0;

        while (true) {
            GraphNode curNode = null;
            int minDis = GraphNode.inf;
            for (GraphNode node : visuGraph.getNodes()) {
                if (node.getDistance() < minDis && !node.isVisited()) {
                    minDis = node.getDistance();
                    curNode = node;
                }
            }
            if (curNode == null)
                break;
            curNode.setVisited(true);
            SequentialTransition selectEdge = new SequentialTransition();
            if(curNode.getLast()!=null) {
                Edge edge = visuGraph.getEdge(curNode.getLast(), curNode);
                selectEdge.getChildren().add(curNode.getEdgeToNodeAnimation(edge));
                selectEdge.getChildren().add(edge.getSelelctedAnimation());
                weight+=edge.getWeightProperty().get();
            }
            selectEdge.getChildren().add(curNode.getSelectedAnimation());
            GraphNode finalCurNode = curNode;
            selectEdge.setOnFinished(e -> instructionText.setText("节点" + finalCurNode.getId() + "为距离目前子树最近的节点,选择它与目前子树间的边加入子树"));
            visuGraph.addNewAnimation(selectEdge);
            for (int i = 0; i < curNode.out.size(); i++) {
                if (!curNode.getOutNode(i).isVisited()) {
                    int newDis = curNode.getDistance() + curNode.getOutEdge(i).getWeightProperty().get();
                    GraphNode nextNode = curNode.getOutNode(i);
                    if (newDis < nextNode.getDistance()) {
                        nextNode.setDistance(newDis);
                        nextNode.setLast(curNode);
                        getPathTrackAnimation(nextNode);
                        ParallelTransition updateAnima = new ParallelTransition();
                        updateAnima.getChildren().add(nextNode.getDistChangeAnimation(newDis));
                        updateAnima.getChildren().add(nextNode.getLastNodeChangeAnimation(curNode.getId()));
                        updateAnima.setOnFinished(e -> instructionText.setText("节点" + nextNode.getId() + "到目前子树的最短距离由节点" + finalCurNode.getId() + "更新为" + newDis));
                        visuGraph.addNewAnimation(updateAnima);
                    }
                }

            }
        }
        return weight;
    }

    public int start(){
        return prim();
    }

    private void getPathTrackAnimation(GraphNode curNode){
        ArrayList<Animation> pathAnimation=new ArrayList<>();
        SequentialTransition pathTrackAnimation=new SequentialTransition();
        while(curNode!=visuGraph.getNode(rootNodeIdx)){
            pathAnimation.add(curNode.getEdgeToNodeAnimation(visuGraph.getEdge(curNode.getLast(),curNode)));
            curNode=curNode.getLast();
        }
        for(int i=pathAnimation.size()-1;i>=0;i--)
            visuGraph.addNewAnimation(pathAnimation.get(i));
    }
}
