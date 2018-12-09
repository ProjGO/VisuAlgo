package Algorithm;

import BasicVisuDS.GraphNode;
import BasicVisuDS.VisuGraph;
import javafx.animation.ParallelTransition;

import java.util.LinkedList;
import java.util.Queue;

public class VisuBFS {
    private VisuGraph visuGraph;
    private int startNodeIdx;
    private Queue<GraphNode> q=new LinkedList<>();

    public VisuBFS(VisuGraph visuGraph){
        this.visuGraph=visuGraph;
    }

    public void setStartNodeIdx(int idx){
        startNodeIdx=idx;
    }

    private void DFS(){
        q.offer(visuGraph.getNode(startNodeIdx));
        visuGraph.addNewAnimation(q.peek().getSelectedAnimation());
        int curLayerNodeCnt=1,nextLayerNodeCnt=0;
        ParallelTransition edgeEmphaAnima=new ParallelTransition(),nodeEmphaAnima=new ParallelTransition();
        while(!q.isEmpty()){
            GraphNode cur=q.poll();
            cur.setVisited(true);
            for(int i=0;i<cur.out.size();i++){
                nextLayerNodeCnt++;
                if(!cur.getOutNode(i).isVisited()) {
                    if (cur.getVisuNode() == cur.getOutEdge(i).getFromVisuNode())
                        edgeEmphaAnima.getChildren().add(cur.getOutEdge(i).getFromToEmphaAnimation());
                    else
                        edgeEmphaAnima.getChildren().add(cur.getOutEdge(i).getToFromEmphaAnimation());
                    nodeEmphaAnima.getChildren().add(cur.getOutNode(i).getSelectedAnimation());
                    q.offer(cur.getOutNode(i));
                }
            }
            if(--curLayerNodeCnt==0){
                visuGraph.addNewAnimation(edgeEmphaAnima);
                visuGraph.addNewAnimation(nodeEmphaAnima);
                edgeEmphaAnima=new ParallelTransition();
                nodeEmphaAnima=new ParallelTransition();
                curLayerNodeCnt=nextLayerNodeCnt;
                nextLayerNodeCnt=0;
            }
        }
    }

    public void start(){
        DFS();
    }
}
