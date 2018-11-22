package cn.edu.bit.cs.VisuAlgo.VisualElements;

public class UnwUndirEdge extends Edge {
    BasicUnwUndirEdge basicUnwUndirEdge=new BasicUnwUndirEdge();

    public UnwUndirEdge(BasicNode from,BasicNode to){
        super.initialize(from,to,basicUnwUndirEdge);
    }

}
