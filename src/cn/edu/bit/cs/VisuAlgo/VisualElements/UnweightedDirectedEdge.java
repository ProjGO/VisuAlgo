package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class UnweightedDirectedEdge extends Path {

    private static double width=4,arrowWidth=8,arrowLength=10,nodeRadius=20;
    private static Color fillColor=Color.GRAY;
    private SimpleDoubleProperty fromXProperty=new SimpleDoubleProperty(),
                                 fromYProperty=new SimpleDoubleProperty(),
                                 toXProperty=new SimpleDoubleProperty(),
                                 toYProperty=new SimpleDoubleProperty();
    private DoubleBinding angle,sinAngle,cosAngle;
    private DoubleBinding XOffset,YOffset,arrowXOffset,arrowYOffset;
    private MoveTo start=new MoveTo();
    private LineTo arrowLeft1=new LineTo(),arrowLeft2=new LineTo(),arrowTop=new LineTo(),arrowRight2=new LineTo(),arrowRight1=new LineTo(),from1=new LineTo(),from2=new LineTo();

    public static void setNodeRadius(double radius){
        nodeRadius=radius;
    }
    public UnweightedDirectedEdge(DoubleProperty _fromXProperty,DoubleProperty _fromYProperty,DoubleProperty _toXProperty,DoubleProperty _toYProperty){

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
        sinAngle=new DoubleBinding() {
            @Override
            protected double computeValue() {

                bind(angle);
                return Math.sin(angle.get());
            }
        };
        cosAngle=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(angle);
                return Math.cos(angle.get());
            }
        };

        XOffset=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(sinAngle);
                return sinAngle.get()*width/2;
            }
        };
        YOffset=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(cosAngle);
                return cosAngle.get()*width/2;
            }
        };
        arrowXOffset=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(sinAngle);
                return sinAngle.get()*arrowWidth/2;
            }
        };
        arrowYOffset=new DoubleBinding() {
            @Override
            protected double computeValue() {
                bind(cosAngle);
                return cosAngle.get()*arrowWidth/2;
            }
        };

        arrowTop.xProperty().bind(toXProperty.subtract(cosAngle.multiply(nodeRadius)));
        arrowTop.yProperty().bind(toYProperty.subtract(sinAngle.multiply(nodeRadius)));
        arrowLeft1.xProperty().bind(toXProperty.subtract(cosAngle.multiply(nodeRadius+arrowLength)).add(XOffset));
        arrowLeft1.yProperty().bind(toYProperty.subtract(sinAngle.multiply(nodeRadius+arrowLength)).subtract(YOffset));
        arrowLeft2.xProperty().bind(toXProperty.subtract(cosAngle.multiply(nodeRadius+arrowLength)).add(arrowXOffset));
        arrowLeft2.yProperty().bind(toYProperty.subtract(sinAngle.multiply(nodeRadius+arrowLength)).subtract(arrowYOffset));
        arrowRight1.xProperty().bind(toXProperty.subtract(cosAngle.multiply(nodeRadius+arrowLength)).subtract(XOffset));
        arrowRight1.yProperty().bind(toYProperty.subtract(sinAngle.multiply(nodeRadius+arrowLength)).add(YOffset));
        arrowRight2.xProperty().bind(toXProperty.subtract(cosAngle.multiply(nodeRadius+arrowLength)).subtract(arrowXOffset));
        arrowRight2.yProperty().bind(toYProperty.subtract(sinAngle.multiply(nodeRadius+arrowLength)).add(arrowYOffset));
        from1.xProperty().bind(fromXProperty.add(XOffset));
        from1.yProperty().bind(fromYProperty.subtract(YOffset));
        from2.xProperty().bind(fromXProperty.subtract(XOffset));
        from2.yProperty().bind(fromYProperty.add(YOffset));

        start.xProperty().bind(from1.xProperty());
        start.yProperty().bind(from1.yProperty());

        this.getElements().addAll(start,arrowLeft1,arrowLeft2,arrowTop,arrowRight2,arrowRight1,from2,from1);
        this.setFill(fillColor);
    }

    public static UnweightedDirectedEdge newUnweightedDirectedEdge(BasicNode from,BasicNode to){
        return new UnweightedDirectedEdge(from.layoutXProperty(),from.layoutYProperty(),to.layoutXProperty(),to.layoutYProperty());
    }
}
