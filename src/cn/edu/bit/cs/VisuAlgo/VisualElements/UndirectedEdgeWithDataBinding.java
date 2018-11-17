package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class UndirectedEdgeWithDataBinding extends Path {
    private double width;
    //private DoubleProperty startX1,startY1,startX2,startY2,endX1,endY1,endX2,endY2;

    public UndirectedEdgeWithDataBinding(BasicNodeByGroup from, BasicNodeByGroup to, double _width){
        width=_width;

        final DoubleBinding XOffset=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(from.layoutXProperty(),from.layoutYProperty(),to.layoutXProperty(),to.layoutYProperty());
                Point2D fromCenterCoordinate=from.getCenterCoordinate();
                Point2D toCenterCoordinate=to.getCenterCoordinate();
                double angle=Math.atan((toCenterCoordinate.getY()-fromCenterCoordinate.getY())/(toCenterCoordinate.getX()-fromCenterCoordinate.getX()));
                double sinAngle=Math.sin(angle);
                return width*sinAngle*0.5;
            }
        };

        final DoubleBinding YOffset=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(from.layoutXProperty(),from.layoutYProperty(),to.layoutXProperty(),to.layoutYProperty());
                Point2D fromCenterCoordinate=from.getCenterCoordinate();
                Point2D toCenterCoordinate=to.getCenterCoordinate();
                double angle=Math.atan((toCenterCoordinate.getY()-fromCenterCoordinate.getY())/(toCenterCoordinate.getX()-fromCenterCoordinate.getX()));
                double cosAngle=Math.cos(angle);
                return width*cosAngle*0.5;
            }
        };

        /*startX1.bind(from.layoutXProperty().add(XOffset));
        startY1.bind(from.layoutYProperty().subtract(YOffset));
        startX2.bind(from.layoutXProperty().subtract(XOffset));
        startY2.bind(from.layoutYProperty().add(YOffset));
        endX1.bind(to.layoutXProperty().add(XOffset));
        endY1.bind(to.layoutYProperty().subtract(YOffset));
        endX2.bind(to.layoutXProperty().subtract(XOffset));
        endY2.bind(to.layoutYProperty().add(YOffset));*/

        /*startX1=fromCenterCoordinate.getX()+XOffset;
        startY1=fromCenterCoordinate.getY()-YOffset;
        startX2=fromCenterCoordinate.getX()-XOffset;
        startY2=fromCenterCoordinate.getY()+YOffset;
        endX1=toCenterCoordinate.getX()+XOffset;
        endY1=toCenterCoordinate.getY()-YOffset;
        endX2=toCenterCoordinate.getX()-XOffset;
        endY2=toCenterCoordinate.getY()+YOffset;*/

        MoveTo MoveToStartPoint1=new MoveTo();
        LineTo endPoint1=new LineTo(),endPoint2=new LineTo(),startPoint2=new LineTo(),startPoint1=new LineTo();
        MoveToStartPoint1.xProperty().bind(from.getCenterX().add(XOffset));
        MoveToStartPoint1.yProperty().bind(from.getCenterY().subtract(YOffset));
        endPoint1.xProperty().bind(to.getCenterX().add(XOffset));
        endPoint1.yProperty().bind(to.getCenterY().subtract(YOffset));
        endPoint2.xProperty().bind(to.getCenterX().subtract(XOffset));
        endPoint2.yProperty().bind(to.getCenterY().add(YOffset));
        startPoint2.xProperty().bind(from.getCenterX().subtract(XOffset));
        startPoint2.yProperty().bind(from.getCenterY().add(YOffset));
        startPoint1.xProperty().bind(from.getCenterX().add(XOffset));
        startPoint1.yProperty().bind(from.getCenterY().subtract(YOffset));

        this.getElements().addAll(MoveToStartPoint1,endPoint1,endPoint2,startPoint2,startPoint1);
        this.setFill(Color.GRAY);

    }
}
