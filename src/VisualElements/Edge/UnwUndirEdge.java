package VisualElements.Edge;

import VisualElements.Node.BasicNode;

public class UnwUndirEdge extends Edge {
    BasicUnwUndirEdge basicUnwUndirEdge=new BasicUnwUndirEdge();

    public UnwUndirEdge(BasicNode from, BasicNode to){
        super.initialize(from,to,basicUnwUndirEdge);
    }

}
