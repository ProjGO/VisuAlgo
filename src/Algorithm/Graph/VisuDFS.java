package Algorithm.Graph;

import BasicVisuDS.GraphNode;
import BasicVisuDS.VisuGraph;
import VisualElements.Edge.Edge;

public class VisuDFS {

    private VisuGraph visuGraph;
    private int startNodeIdx;

    public VisuDFS(VisuGraph visuGraph){
        this.visuGraph=visuGraph;
    }

    public void setStartNodeIdx(int idx){
        startNodeIdx=idx;
    }

    private void dfs(GraphNode cur, Edge inEdge){
        cur.setVisited(true);
        visuGraph.addNewAnimation(cur.getSelectedAnimation());
        for(int i=0;i<cur.out.size();i++)
        {
            if(!cur.getOutNode(i).isVisited()) {
                if(cur.getVisuNode()==cur.getOutEdge(i).getFromVisuNode())
                    visuGraph.addNewAnimation(cur.getOutEdge(i).getFromToEmphaAnimation());
                else
                    visuGraph.addNewAnimation(cur.getOutEdge(i).getToFromEmphaAnimation());
                dfs(cur.getOutNode(i),cur.getOutEdge(i));
            }
        }
        if(inEdge!=null) {
            if (inEdge.getToVisuNode() == cur.getVisuNode())
                visuGraph.addNewAnimation(inEdge.getToFromEmphaAnimation());
            else
                visuGraph.addNewAnimation(inEdge.getFromToEmphaAnimation());
        }
    }

    public void start(){
        dfs(visuGraph.getNode(startNodeIdx),null);
    }

}
