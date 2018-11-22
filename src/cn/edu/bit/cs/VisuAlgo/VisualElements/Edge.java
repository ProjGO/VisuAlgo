package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;

public class Edge extends Group {

    protected SimpleDoubleProperty toXProperty = new SimpleDoubleProperty(), toYProperty = new SimpleDoubleProperty();
    private DoubleProperty fromX,fromY,toX,toY;
    private BasicEdge basicEdge;

    private SimpleDoubleProperty zero = new SimpleDoubleProperty(0);

    protected void initialize(BasicNode from, BasicNode to, BasicEdge _basicEdge) {
        basicEdge=_basicEdge;
        bindLayoutProperty(from.layoutXProperty(),from.layoutYProperty(),to.layoutXProperty(),to.layoutYProperty());
        basicEdge.setToXProperty(toXProperty);
        basicEdge.setToYProperty(toYProperty);
        basicEdge.initialize();
        this.getChildren().add(basicEdge);
    }

    public void bindLayoutProperty(DoubleProperty _fromX,DoubleProperty _fromY,DoubleProperty _toX,DoubleProperty _toY){
        bindFromLayoutProperty(_fromX,_fromY);
        bindToLayoutProperty(_toX,_toY);
        basicEdge.setFromXProperty(zero);
        basicEdge.setFromYProperty(zero);
    }

    public void bindFromLayoutProperty(DoubleProperty _fromX,DoubleProperty _fromY){
        fromX=_fromX;
        fromY=_fromY;
        layoutXProperty().bindBidirectional(fromX);
        layoutYProperty().bindBidirectional(fromY);
    }

    public void bindToLayoutProperty(DoubleProperty _toX,DoubleProperty _toY){
        toX=_toX;
        toY=_toY;
        toXProperty.bind(toX.subtract(layoutXProperty()));
        toYProperty.bind(toY.subtract(layoutYProperty()));
    }

    public void unbindFromLayoutProperty(){
        layoutXProperty().unbindBidirectional(fromX);
        layoutYProperty().unbindBidirectional(fromY);
    }

    public void unbindToLayoutProperty(){
        toXProperty.unbind();
        toYProperty.unbind();
    }

    public double getToX(){ return toX.get(); }
    public double getToY(){ return toY.get(); }
    public double getFromX() {return fromX.get();}
    public double getFromY() {return fromY.get();}
}