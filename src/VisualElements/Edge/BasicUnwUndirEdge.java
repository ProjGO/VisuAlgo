package VisualElements.Edge;

import javafx.animation.ParallelTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class BasicUnwUndirEdge extends BasicEdge {

    MoveTo MoveToStartPoint1=new MoveTo();
    LineTo endRightPoint =new LineTo(), endLeftPoint =new LineTo(), startLeftPoint =new LineTo(), startRightPoint =new LineTo();

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
                    return fromXProperty.get()+cosAngle.get()*nodeRadius;
                }
            };

            final DoubleBinding fromY=new DoubleBinding() {
                @Override
                protected double computeValue() {
                    bind(fromYProperty,cosAngle);
                    return fromYProperty.get()+sinAngle.get()*nodeRadius;
                }
            };

            final DoubleBinding toX=new DoubleBinding() {
                @Override
                protected double computeValue() {
                    bind(toXProperty,cosAngle);
                    return toXProperty.get()-cosAngle.get()*nodeRadius;
                }
            };

            final DoubleBinding toY=new DoubleBinding() {
                @Override
                protected double computeValue() {
                    bind(toYProperty,sinAngle);
                    return toYProperty.get()-sinAngle.get()*nodeRadius;
                }
            };

            MoveToStartPoint1.xProperty().bind(fromX.add(XOffset));
            MoveToStartPoint1.yProperty().bind(fromY.subtract(YOffset));
            endRightPoint.xProperty().bind(toX.add(XOffset));
            endRightPoint.yProperty().bind(toY.subtract(YOffset));
            endLeftPoint.xProperty().bind(toX.subtract(XOffset));
            endLeftPoint.yProperty().bind(toY.add(YOffset));
            startLeftPoint.xProperty().bind(fromX.subtract(XOffset));
            startLeftPoint.yProperty().bind(fromY.add(YOffset));
            startRightPoint.xProperty().bind(fromX.add(XOffset));
            startRightPoint.yProperty().bind(fromY.subtract(YOffset));

            this.getElements().addAll(MoveToStartPoint1, endRightPoint, endLeftPoint, startLeftPoint, startRightPoint);
            this.setFill(Color.GRAY);
    }

}
