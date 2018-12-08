package Algorithm;

import BasicVisuDS.GraphNode;
import BasicVisuDS.VisuGraph;
import VisualElements.Edge.Edge;

public class VisuDFS {

    VisuGraph visuGraph;
    private int startNodeIdx;

    public VisuDFS(VisuGraph visuGraph){
        this.visuGraph=visuGraph;
    }

    public void setStartNode(int idx){
        startNodeIdx=idx;
        visuGraph.setNodeSelected(idx);
    }

    public void dfs(GraphNode cur, Edge inEdge){
        cur.setVisited();
        for(int i=0;i<cur.out.size();i++)
        {
            if(!cur.getOutNode(i).isVisited()) {
                visuGraph.addNewAnimation(cur.getOutEdge(i).getFromToEmphaAnimation());
                dfs(cur.getOutNode(i),cur.getOutEdge(i));
            }
        }
        if(inEdge!=null)
            visuGraph.addNewAnimation(inEdge.getToFromEmphaAnimation());
    }

    public void start(){
        dfs(visuGraph.getNode(startNodeIdx),null);
    }

}
