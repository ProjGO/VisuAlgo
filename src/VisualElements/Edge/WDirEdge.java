package VisualElements.Edge;

import VisualElements.Node.BasicVisuNode;

public class WDirEdge extends WeightedEdge {
    public WDirEdge(BasicVisuNode from, BasicVisuNode to, int weight){
        BasicUnwDirEdge basicUnwDirEdge =new BasicUnwDirEdge();
        initialize(from,to, basicUnwDirEdge,weight);
    }
}
