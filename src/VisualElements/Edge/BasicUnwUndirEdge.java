package VisualElements.Edge;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

class BasicUnwUndirEdge extends BasicEdge {

    private MoveTo MoveToStartPoint1=new MoveTo();
    private LineTo endRightPoint =new LineTo(), endLeftPoint =new LineTo(), startLeftPoint =new LineTo(), startRightPoint =new LineTo();

    BasicUnwUndirEdge(){}

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