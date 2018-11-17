package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class UndirectedEdge extends Path {
    private double width;
    private double startX1,startY1,startX2,startY2,endX1,endY1,endX2,endY2;
    private double angle;

    public UndirectedEdge(BasicNode from,BasicNode to,double _width){
        width=_width;
        Point2D fromCenterCoordinate=from.getCenterCoordinate();
        Point2D toCenterCoordinate=to.getCenterCoordinate();

        angle=Math.atan((toCenterCoordinate.getY()-fromCenterCoordinate.getY())/(toCenterCoordinate.getX()-fromCenterCoordinate.getX()));
        double sinAngle=Math.sin(angle),cosAngle=Math.cos(angle);

        double halfWidth=0.5*width;
        double XOffset=halfWidth*sinAngle,YOffset=halfWidth*cosAngle;
        startX1=fromCenterCoordinate.getX()+XOffset;
        startY1=fromCenterCoordinate.getY()-YOffset;
        startX2=fromCenterCoordinate.getX()-XOffset;
        startY2=fromCenterCoordinate.getY()+YOffset;
        endX1=toCenterCoordinate.getX()+XOffset;
        endY1=toCenterCoordinate.getY()-YOffset;
        endX2=toCenterCoordinate.getX()-XOffset;
        endY2=toCenterCoordinate.getY()+YOffset;

        this.getElements().addAll(new MoveTo(startX1,startY1),
                new LineTo(endX1,endY1),new LineTo(endX2,endY2),
                new LineTo(startX2,startY2),new LineTo(startX1,startY1));
        this.setFill(Color.BLACK);


        /*startX=fromCenterCoordinate.getX()+from.getRadius()*sinAngle;
        startY=fromCenterCoordinate.getY()+from.getRadius()*cosAngle;
        endX=toCenterCoordinate.getX()-to.getRadius()*cosAngle;
        endY=toCenterCoordinate.getY()-to.getRadius()*sinAngle;*/
    }
}
