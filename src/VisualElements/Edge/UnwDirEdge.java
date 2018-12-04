package VisualElements.Edge;

import VisualElements.Node.BasicVisuNode;

public class UnwDirEdge extends Edge {
    BasicUnwDirEdge basicUnwDirEdge=new BasicUnwDirEdge();

    public UnwDirEdge(BasicVisuNode from, BasicVisuNode to){
        super.initialize(from,to,basicUnwDirEdge);
    }
}
