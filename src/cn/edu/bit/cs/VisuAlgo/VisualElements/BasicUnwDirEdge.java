package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;



public class BasicUnwDirEdge extends BasicEdge {

    private DoubleBinding XOffset,YOffset,arrowXOffset,arrowYOffset;
    private MoveTo start=new MoveTo();
    private LineTo arrowLeft1=new LineTo(),arrowLeft2=new LineTo(),arrowTop=new LineTo(),arrowRight2=new LineTo(),arrowRight1=new LineTo(),from1=new LineTo(),from2=new LineTo();

    public BasicUnwDirEdge(){};

    void initialize(){
        super.initialize();

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
        from1.xProperty().bind(fromX.add(XOffset));
        from1.yProperty().bind(fromY.subtract(YOffset));
        from2.xProperty().bind(fromX.subtract(XOffset));
        from2.yProperty().bind(fromY.add(YOffset));
        start.xProperty().bind(from1.xProperty());
        start.yProperty().bind(from1.yProperty());

        this.getElements().addAll(start,arrowLeft1,arrowLeft2,arrowTop,arrowRight2,arrowRight1,from2,from1);

        this.setFill(fillColor);

    }

}