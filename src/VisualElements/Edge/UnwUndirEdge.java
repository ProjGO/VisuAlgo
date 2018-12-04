package VisualElements.Edge;

import VisualElements.Node.BasicVisuNode;

public class UnwUndirEdge extends Edge {
    BasicUnwUndirEdge basicUnwUndirEdge=new BasicUnwUndirEdge();

    public UnwUndirEdge(BasicVisuNode from, BasicVisuNode to){
        super.initialize(from,to,basicUnwUndirEdge);
    }

}
