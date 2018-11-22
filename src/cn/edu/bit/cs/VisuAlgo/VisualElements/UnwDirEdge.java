package cn.edu.bit.cs.VisuAlgo.VisualElements;

public class UnwDirEdge extends Edge {
    BasicUnwDirEdge basicUnwDirEdge=new BasicUnwDirEdge();

    public UnwDirEdge(BasicNode from,BasicNode to){
        super.initialize(from,to,basicUnwDirEdge);
    }
}
