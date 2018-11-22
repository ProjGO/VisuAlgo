package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;



public class BasicUnwDirEdge extends BasicEdge {

    private DoubleBinding XOffset,YOffset,arrowXOffset,arrowYOffset;
    private MoveTo start=new MoveTo();
    private LineTo arrowLeft1=new LineTo(),arrowLeft2=new LineTo(),arrowTop=new LineTo(),arrowRight2=new LineTo(),arrowRight1=new LineTo(),from1=new LineTo(),from2=new LineTo();

    public BasicUnwDirEdge(){};

    /*public BasicUnwDirEdge(DoubleProperty fromXProperty, DoubleProperty fromYProperty, DoubleProperty toXProperty, DoubleProperty toYProperty){

        setFromXProperty(fromXProperty);
        setFromYProperty(fromYProperty);
        setToXProperty(toXProperty);
        setToYProperty(toYProperty);

        initialize();

    }

    public BasicUnwDirEdge(BasicNode from, BasicNode to){
        setFromXProperty(from.layoutXProperty());
        setFromYProperty(from.layoutYProperty());
        setToXProperty(to.layoutXProperty());
        setToYProperty(to.layoutYProperty());

        initialize();
    }*/

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

}