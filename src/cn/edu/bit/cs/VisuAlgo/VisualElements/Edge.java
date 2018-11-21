package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public class Edge extends Path {

    protected SimpleDoubleProperty fromXProperty=new SimpleDoubleProperty(),fromYProperty=new SimpleDoubleProperty(),
                         toXProperty=new SimpleDoubleProperty(),toYProperty=new SimpleDoubleProperty();
    static protected double width=ElementParameters.edgeWidth;
    static protected double arrowWidth=ElementParameters.arrowWidth,arrowLength=ElementParameters.arrowLength;

    protected void setFromXProperty(DoubleProperty _fromXProperty){
        fromXProperty.bind(_fromXProperty);
    }
    protected void setFromYProperty(DoubleProperty _fromYProperty){
        fromYProperty.bind(_fromYProperty);
    }

    protected void setToXProperty(DoubleProperty _toXProperty) {
        toXProperty.bind(_toXProperty);
    }

    protected void setToYProperty(DoubleProperty _toYProperty) {
        toYProperty.bind(_toYProperty);
    }

    protected void setFromPoint(Shape fromPoint) {
        setFromXProperty(fromPoint.layoutXProperty());
        setFromYProperty(fromPoint.layoutYProperty());
    }

    protected void setToPoint(Shape toPoint){
        setToXProperty(toPoint.layoutXProperty());
        setToYProperty(toPoint.layoutYProperty());
    }

}
