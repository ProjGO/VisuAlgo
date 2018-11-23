package VisualElements.Edge;

import VisualElements.Node.BasicNode;

public class WDirEdge extends WeightedEdge {
    public WDirEdge(BasicNode from, BasicNode to, int weight){
        BasicUnwDirEdge basicUnwDirEdge =new BasicUnwDirEdge();
        initialize(from,to, basicUnwDirEdge,weight);
    }
}
