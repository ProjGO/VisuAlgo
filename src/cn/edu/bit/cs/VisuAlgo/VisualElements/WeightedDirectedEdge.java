package cn.edu.bit.cs.VisuAlgo.VisualElements;

public class WeightedDirectedEdge extends WeightedEdge {
    public WeightedDirectedEdge(BasicNode from,BasicNode to,int weight){
        UnweightedDirectedEdge unweightedDirectedEdge=new UnweightedDirectedEdge(from,to);
        initialize(from,to,unweightedDirectedEdge,weight);
    }
}
