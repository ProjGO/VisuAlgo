package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class UnweightedUndirectedEdge extends Path {
    static private double width=ElementParameters.edgeWidth;
    private DoubleBinding angle;
    //private DoubleProperty startX1,startY1,startX2,startY2,endX1,endY1,endX2,endY2;

    public static UnweightedUndirectedEdge newUnweightedUndirectedEdge(BasicNode from, BasicNode to) {
        return new UnweightedUndirectedEdge(from.layoutXProperty(), from.layoutYProperty(), to.layoutXProperty(), to.layoutYProperty());
    }

    public UnweightedUndirectedEdge(DoubleProperty fromXProperty,DoubleProperty fromYProperty,DoubleProperty toXProperty,DoubleProperty toYProperty){

        angle=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(fromXProperty,fromYProperty,toXProperty,toYProperty);
                double angle=Math.atan((toYProperty.get()-fromYProperty.get())/(toXProperty.get()-fromXProperty.get()));
                return angle;
            }
        };

        final DoubleBinding XOffset=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(angle);
                double sinAngle=Math.sin(angle.get());
                return width*sinAngle*0.5;
            }
        };

        final DoubleBinding YOffset=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(angle);
                double cosAngle=Math.cos(angle.get());
                return width*cosAngle*0.5;
            }
        };

        MoveTo MoveToStartPoint1=new MoveTo();
        LineTo endPoint1=new LineTo(),endPoint2=new LineTo(),startPoint2=new LineTo(),startPoint1=new LineTo();
        MoveToStartPoint1.xProperty().bind(fromXProperty.add(XOffset));
        MoveToStartPoint1.yProperty().bind(fromYProperty.subtract(YOffset));
        endPoint1.xProperty().bind(toXProperty.add(XOffset));
        endPoint1.yProperty().bind(toYProperty.subtract(YOffset));
        endPoint2.xProperty().bind(toXProperty.subtract(XOffset));
        endPoint2.yProperty().bind(toYProperty.add(YOffset));
        startPoint2.xProperty().bind(fromXProperty.subtract(XOffset));
        startPoint2.yProperty().bind(fromYProperty.add(YOffset));
        startPoint1.xProperty().bind(fromXProperty.add(XOffset));
        startPoint1.yProperty().bind(fromYProperty.subtract(YOffset));

        this.getElements().addAll(MoveToStartPoint1,endPoint1,endPoint2,startPoint2,startPoint1);
        this.setFill(Color.GRAY);
    }

    public DoubleBinding getAngleProperty(){
        return angle;
    }
}