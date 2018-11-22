package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public class Edge extends Group {

    protected SimpleDoubleProperty toXProperty=new SimpleDoubleProperty(),toYProperty=new SimpleDoubleProperty();
    protected Circle fromPoint,toPoint;

    private SimpleDoubleProperty zero=new SimpleDoubleProperty(0);

    protected void initialize(BasicNode from,BasicNode to,BasicEdge basicEdge){
        fromPoint=new Circle(0);
        toPoint=new Circle(0);
        bindCoordinate(fromPoint,from);
        bindCoordinate(toPoint,to);
        bindCoordinate(this,fromPoint);
        this.getChildren().addAll(fromPoint,toPoint);
        basicEdge.setFromXProperty(zero);
        basicEdge.setFromYProperty(zero);
        toXProperty.bind(to.layoutXProperty().subtract(from.layoutXProperty()));
        toYProperty.bind(to.layoutYProperty().subtract(from.layoutYProperty()));
        basicEdge.setToXProperty(toXProperty);
        basicEdge.setToYProperty(toYProperty);
        basicEdge.initialize();
        this.getChildren().add(basicEdge);
    }

    private void bindCoordinate(Shape dest,Group src){
        dest.layoutXProperty().bind(src.layoutXProperty());
        dest.layoutYProperty().bind(src.layoutYProperty());
    }
    private void bindCoordinate(Group dest,Shape src){
        dest.layoutXProperty().bindBidirectional(src.layoutXProperty());
        dest.layoutYProperty().bindBidirectional(src.layoutYProperty());
    }
}
