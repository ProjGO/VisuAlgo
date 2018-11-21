package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WeightedUndirectedEdge extends WeightedEdge {

    public WeightedUndirectedEdge(BasicNode from,BasicNode to,int weight){
        UnweightedUndirectedEdge unweightedUndirectedEdge=new UnweightedUndirectedEdge(from,to);
        initialize(from,to,unweightedUndirectedEdge,weight);
    }

}
