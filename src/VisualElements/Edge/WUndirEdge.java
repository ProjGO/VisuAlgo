package VisualElements.Edge;

import VisualElements.Node.BasicVisuNode;

public class WUndirEdge extends WeightedEdge {

    public WUndirEdge(BasicVisuNode from, BasicVisuNode to, int weight){
        BasicUnwUndirEdge basicUnwUndirEdge=new BasicUnwUndirEdge();
        initialize(from,to,basicUnwUndirEdge,weight);
    }

}
