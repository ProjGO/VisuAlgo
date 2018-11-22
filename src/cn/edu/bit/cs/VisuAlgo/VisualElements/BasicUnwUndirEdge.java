package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class BasicUnwUndirEdge extends BasicEdge {

    private MoveTo MoveToStartPoint1=new MoveTo();
    private LineTo endPoint1=new LineTo(),endPoint2=new LineTo(),startPoint2=new LineTo(),startPoint1=new LineTo();

    public BasicUnwUndirEdge(){}

    void initialize(){
        super.initialize();

            final DoubleBinding XOffset=new DoubleBinding() {
                @Override
                protected double computeValue() {
                    bind(sinAngle);
                    return width*sinAngle.get()*0.5;
                }
            };

            final DoubleBinding YOffset=new DoubleBinding() {
                @Override
                protected double computeValue() {
                    bind(cosAngle);
                    return width*cosAngle.get()*0.5;
                }
            };

            final DoubleBinding fromX=new DoubleBinding() {
                @Override
                protected double computeValue() {
                    bind(fromXProperty,sinAngle);
                    return fromXProperty.get()+cosAngle.get()*nodeRadius*0.99;
                }
            };

            final DoubleBinding fromY=new DoubleBinding() {
                @Override
                protected double computeValue() {
                    bind(fromYProperty,cosAngle);
                    return fromYProperty.get()+sinAngle.get()*nodeRadius*0.99;
                }
            };

            final DoubleBinding toX=new DoubleBinding() {
                @Override
                protected double computeValue() {
                    bind(toXProperty,cosAngle);
                    return toXProperty.get()-cosAngle.get()*nodeRadius*0.99;
                }
            };

            final DoubleBinding toY=new DoubleBinding() {
                @Override
                protected double computeValue() {
                    bind(toYProperty,sinAngle);
                    return toYProperty.get()-sinAngle.get()*nodeRadius*0.99;
                }
            };

            MoveToStartPoint1.xProperty().bind(fromX.add(XOffset));
            MoveToStartPoint1.yProperty().bind(fromY.subtract(YOffset));
            endPoint1.xProperty().bind(toX.add(XOffset));
            endPoint1.yProperty().bind(toY.subtract(YOffset));
            endPoint2.xProperty().bind(toX.subtract(XOffset));
            endPoint2.yProperty().bind(toY.add(YOffset));
            startPoint2.xProperty().bind(fromX.subtract(XOffset));
            startPoint2.yProperty().bind(fromY.add(YOffset));
            startPoint1.xProperty().bind(fromX.add(XOffset));
            startPoint1.yProperty().bind(fromY.subtract(YOffset));

            this.getElements().addAll(MoveToStartPoint1,endPoint1,endPoint2,startPoint2,startPoint1);
            this.setFill(Color.GRAY);
    }
}
