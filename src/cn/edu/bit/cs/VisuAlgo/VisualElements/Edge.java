package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public class Edge extends Group {

    protected SimpleDoubleProperty toXProperty = new SimpleDoubleProperty(), toYProperty = new SimpleDoubleProperty();
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

    public void bindLayoutProperty(DoubleProperty fromX,DoubleProperty fromY,DoubleProperty toX,DoubleProperty toY){
        layoutXProperty().bindBidirectional(fromX);
        layoutYProperty().bindBidirectional(fromY);
        basicEdge.setFromXProperty(zero);
        basicEdge.setFromYProperty(zero);
        toXProperty.bind(toX.subtract(fromX));
        toYProperty.bind(toY.subtract(fromY));
    }

    public void unbindFromProperty(){
        layoutXProperty().unbindBidirectional(layoutXProperty().);
    }
}