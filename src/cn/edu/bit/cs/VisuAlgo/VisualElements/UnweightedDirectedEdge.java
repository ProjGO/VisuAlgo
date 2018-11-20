package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.shape.Path;

public class UnweightedDirectedEdge extends Path {

    private double width,arrowWidth,arrowLength;
    private SimpleDoubleProperty fromXProperty=new SimpleDoubleProperty(),
                                 fromYProperty=new SimpleDoubleProperty(),
                                 toXProperty=new SimpleDoubleProperty(),
                                 toYProperty=new SimpleDoubleProperty();
    private DoubleBinding angle;

    public UnweightedDirectedEdge(DoubleProperty _fromXProperty,DoubleProperty _fromYProperty,DoubleProperty _toXProperty,DoubleProperty _toYProperty,double nodeRadius){
        fromXProperty.bind(_fromXProperty);
        fromYProperty.bind(_fromYProperty);
        toXProperty.bind(_toXProperty);
        toYProperty.bind(_toYProperty);

        angle=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(fromXProperty,fromYProperty,toXProperty,toYProperty);
                double angle=Math.atan((toYProperty.get()-fromYProperty.get())/(toXProperty.get()-fromXProperty.get()));
                return angle;
            }
        };
    }
}
