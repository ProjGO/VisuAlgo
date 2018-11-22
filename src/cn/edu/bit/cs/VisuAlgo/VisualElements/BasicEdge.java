package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public abstract class BasicEdge extends Path {
    protected static double width=ElementParameters.edgeWidth,arrowWidth=ElementParameters.arrowWidth,arrowLength=ElementParameters.arrowLength,nodeRadius=ElementParameters.nodeRadius;
    protected static Color fillColor=ElementParameters.edgeColor;

    protected SimpleDoubleProperty fromXProperty=new SimpleDoubleProperty(),fromYProperty=new SimpleDoubleProperty(),
            toXProperty=new SimpleDoubleProperty(),toYProperty=new SimpleDoubleProperty();
    protected DoubleBinding angle,sinAngle,cosAngle;

    protected void setFromXProperty(DoubleProperty _fromXProperty){ fromXProperty.bind(_fromXProperty); }
    protected void setFromYProperty(DoubleProperty _fromYProperty){
        fromYProperty.bind(_fromYProperty);
    }
    protected void setToXProperty(DoubleProperty _toXProperty) {
        toXProperty.bind(_toXProperty);
    }
    protected void setToYProperty(DoubleProperty _toYProperty) {
        toYProperty.bind(_toYProperty);
    }

    void initialize(){
        angle=new DoubleBinding() {

            @Override

            protected double computeValue() {
                bind(fromXProperty,fromYProperty,toXProperty,toYProperty);
                double angle=Math.atan((toYProperty.get()-fromYProperty.get())/(toXProperty.get()-fromXProperty.get()));
                return angle;
            }

        };

        sinAngle=new DoubleBinding() {

            @Override

            protected double computeValue() {
                bind(angle);
                if(toXProperty.get()<fromXProperty.get())
                    return -Math.sin(angle.get());
                else
                    return Math.sin(angle.get());
            }

        };

        cosAngle=new DoubleBinding() {

            @Override

            protected double computeValue() {
                bind(angle);
                if(toXProperty.get()<fromXProperty.get())
                    return -Math.cos(angle.get());
                else
                    return Math.cos(angle.get());
            }

        };
    }
}
