package cn.edu.bit.cs.VisuAlgo.VisualElements;

public class WDirEdge extends WeightedEdge {
    public WDirEdge(BasicNode from, BasicNode to, int weight){
        BasicUnwDirEdge basicUnwDirEdge =new BasicUnwDirEdge();
        initialize(from,to, basicUnwDirEdge,weight);
    }
}
