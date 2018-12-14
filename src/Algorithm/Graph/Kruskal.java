package Algorithm.Graph;

import BasicVisuDS.GraphEdge;
import BasicVisuDS.GraphNode;
import BasicVisuDS.VisuGraph;
import VisualElements.Edge.Edge;
import javafx.animation.ParallelTransition;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Kruskal {
    private VisuGraph visuGraph;
    private Text instructionText;

    public Kruskal(VisuGraph visuGraph,Text instructionText){
        this.visuGraph=visuGraph;
        this.instructionText=instructionText;
    }

    private int kruskal(){
        Queue<GraphEdge> edges=new PriorityQueue<>((e1, e2) -> e1.getVisuEdge().getWeightProperty().get()-e2.getVisuEdge().getWeightProperty().get());
        for(GraphEdge edge:visuGraph.getEdges())
            edges.offer(edge);
        int weight=0;
        while(!edges.isEmpty()){
            GraphEdge curEdge=edges.poll();
            if(visuGraph.getLastAnimation()!=null)
                visuGraph.getLastAnimation().setOnFinished(e->instructionText.setText("这是目前未加入的权值最小的边"));
            visuGraph.addNewAnimation(curEdge.getVisuEdge().getEmphasizeAnimation());
            if(getFather(curEdge.getFromNode())!=getFather(curEdge.getToNode())){
                weight+=curEdge.getVisuEdge().getWeightProperty().get();
                visuGraph.getLastAnimation().setOnFinished(e->instructionText.setText("加入这条边不会形成圈,可以加入至生成树中"));
                ParallelTransition addEdgeAnima=new ParallelTransition();
                addEdgeAnima.getChildren().add(curEdge.getVisuEdge().getSelelctedAnimation());
                if(!curEdge.getFromNode().isSelected())
                    addEdgeAnima.getChildren().add(curEdge.getFromNode().getSelectedAnimation());
                if(!curEdge.getToNode().isSelected())
                    addEdgeAnima.getChildren().add(curEdge.getToNode().getSelectedAnimation());
                visuGraph.addNewAnimation(addEdgeAnima);
                getFather(curEdge.getFromNode()).setLast(getFather(curEdge.getToNode()));
            }else{
                ArrayList<Edge> loopEdges=new ArrayList<>();
                loopEdges.addAll(getToFatherPath(curEdge.getFromNode()));
                loopEdges.addAll(getToFatherPath(curEdge.getToNode()));
                loopEdges.add(curEdge.getVisuEdge());
                visuGraph.getLastAnimation().setOnFinished(e->instructionText.setText("加入这条边会形成圈,故不加入这条边"));
                ParallelTransition rejectAnima=new ParallelTransition();
                for(Edge edge:loopEdges)
                    if(edge!=null)
                        rejectAnima.getChildren().add(edge.getRejectedAnimation());
                visuGraph.addNewAnimation(rejectAnima);
            }
        }
        return weight;
    }

    public int start(){
        return kruskal();
    }

    private ArrayList<Edge> getToFatherPath(GraphNode node){
        ArrayList<Edge> path=new ArrayList<>();
        while(node.getLast()!=null){
            path.add(visuGraph.getEdge(node,node.getLast()));
            node=node.getLast();
        }
        return path;
    }

    private GraphNode getFather(GraphNode node){
        while(node.getLast()!=null)
            node=node.getLast();
        return node;
    }

}
