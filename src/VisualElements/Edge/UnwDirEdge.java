package VisualElements.Edge;

import VisualElements.Node.BasicNode;

public class UnwDirEdge extends Edge {
    BasicUnwDirEdge basicUnwDirEdge=new BasicUnwDirEdge();

    public UnwDirEdge(BasicNode from, BasicNode to){
        super.initialize(from,to,basicUnwDirEdge);
    }
}
