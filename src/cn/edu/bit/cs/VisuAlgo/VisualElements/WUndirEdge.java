package cn.edu.bit.cs.VisuAlgo.VisualElements;

public class WUndirEdge extends WeightedEdge {

    public WUndirEdge(BasicNode from, BasicNode to, int weight){
        UnwDirEdge UnwDirEdge =new UnwDirEdge(from,to);
        initialize(from,to, UnwDirEdge,weight);
    }

}
