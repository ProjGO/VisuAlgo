package cn.edu.bit.cs.VisuAlgo.VisualElements;

public class WDirEdge extends WeightedEdge {
    public WDirEdge(BasicNode from, BasicNode to, int weight){
        UnwDirEdge unwDirEdge =new UnwDirEdge(from,to);
        initialize(from,to, unwDirEdge,weight);
    }
}
