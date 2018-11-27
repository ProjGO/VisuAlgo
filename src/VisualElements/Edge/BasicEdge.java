package VisualElements.Edge;

import Parameters.Parameters;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public abstract class BasicEdge extends Path {
    protected static double width= Parameters.edgeWidth,arrowWidth= Parameters.arrowWidth,arrowLength= Parameters.arrowLength,nodeRadius= Parameters.nodeRadius+ Parameters.nodeStrokeWidth*0.5;
    protected static Color fillColor= Parameters.edgeColor;//默认值

    SimpleDoubleProperty fromNodeXProperty =new SimpleDoubleProperty(), fromNodeYProperty =new SimpleDoubleProperty(),
            toNodeXProperty =new SimpleDoubleProperty(), toNodeYProperty =new SimpleDoubleProperty();
    DoubleBinding angle,sinAngle,cosAngle,fromX,fromY,toX,toY;
    BooleanBinding distGreaterThanRadius;

    protected void setFromNodeXProperty(DoubleProperty _fromXProperty){ fromNodeXProperty.bind(_fromXProperty); }
    protected void setFromNodeYProperty(DoubleProperty _fromYProperty){
        fromNodeYProperty.bind(_fromYProperty);
    }
    protected void setToNodeXProperty(DoubleProperty _toXProperty) {
        toNodeXProperty.bind(_toXProperty);
    }
    protected void setToNodeYProperty(DoubleProperty _toYProperty) {
        toNodeYProperty.bind(_toYProperty);
    }

    void initialize(){

        distGreaterThanRadius=new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                bind(fromNodeXProperty,fromNodeYProperty,toNodeXProperty,toNodeYProperty);
                double deltaX=fromNodeXProperty.get()-toNodeXProperty.get();
                double deltaY=fromNodeYProperty.get()-toNodeYProperty.get();
                return Math.sqrt(deltaX*deltaX+deltaY*deltaY)>2*Parameters.nodeRadius;
            }
        };

        angle=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(fromNodeXProperty, fromNodeYProperty, toNodeXProperty, toNodeYProperty);
                return Math.atan((toNodeYProperty.get()- fromNodeYProperty.get())/(toNodeXProperty.get()- fromNodeXProperty.get()));
            }

        };

        sinAngle=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(angle);
                if(toNodeXProperty.get()< fromNodeXProperty.get())
                    return -Math.sin(angle.get());
                else
                    return Math.sin(angle.get());
            }

        };

        cosAngle=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(angle);
                if(toNodeXProperty.get()< fromNodeXProperty.get())
                    return -Math.cos(angle.get());
                else
                    return Math.cos(angle.get());
            }

        };

        fromX=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(fromNodeXProperty,sinAngle,distGreaterThanRadius);
                if(distGreaterThanRadius.get())
                    return fromNodeXProperty.get()+cosAngle.get()*nodeRadius;
                else
                    return fromNodeXProperty.get();
            }
        };

        fromY=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(fromNodeYProperty,cosAngle,distGreaterThanRadius);
                if(distGreaterThanRadius.get())
                    return fromNodeYProperty.get()+sinAngle.get()*nodeRadius;
                else
                    return fromNodeYProperty.get();
            }
        };

        toX=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(toNodeXProperty,cosAngle,distGreaterThanRadius);
                if(distGreaterThanRadius.get())
                    return toNodeXProperty.get()-cosAngle.get()*nodeRadius;
                else
                    return toNodeXProperty.get();
            }
        };

        toY=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(toNodeYProperty,sinAngle,distGreaterThanRadius);
                if(distGreaterThanRadius.get())
                    return toNodeYProperty.get()-sinAngle.get()*nodeRadius;
                else
                    return toNodeYProperty.get();
            }
        };
    }

}
