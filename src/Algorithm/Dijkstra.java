package Algorithm;

import BasicVisuDS.VisuGraph;
import javafx.animation.ParallelTransition;

public class Dijkstra {
    private VisuGraph visuGraph;
    private int startNodeIdx;

    public Dijkstra(VisuGraph visuGraph){
        this.visuGraph=visuGraph;
    }

    public void setStartNodeIdx(int idx){
        startNodeIdx=idx;
    }

}
