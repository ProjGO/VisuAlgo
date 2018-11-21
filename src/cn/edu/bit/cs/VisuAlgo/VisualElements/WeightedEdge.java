package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WeightedEdge extends Group {

    protected SimpleDoubleProperty toXProperty,toYProperty;
    protected SimpleDoubleProperty midXProperty,midYProperty;
    protected SimpleIntegerProperty weight;
    protected Text text;

    protected void initialize(BasicNode from,BasicNode to,Edge edge,Integer _weight){
        weight=new SimpleIntegerProperty(_weight);

        DoubleProperty zero=new SimpleDoubleProperty(0);

        layoutXProperty().bindBidirectional(from.layoutXProperty());
        layoutYProperty().bindBidirectional(from.layoutYProperty());

        toXProperty=new SimpleDoubleProperty();
        toYProperty=new SimpleDoubleProperty();
        toXProperty.bind(to.layoutXProperty().subtract(from.layoutXProperty()));
        toYProperty.bind(to.layoutYProperty().subtract(from.layoutYProperty()));

        edge.setFromXProperty(zero);
        edge.setFromYProperty(zero);
        edge.setToXProperty(toXProperty);
        edge.setToYProperty(toYProperty);

        midXProperty=new SimpleDoubleProperty();
        midYProperty=new SimpleDoubleProperty();
        midXProperty.bind(toXProperty.divide(2));
        midYProperty.bind(toYProperty.divide(2));

        text=new Text(weight.getValue().toString());
        text.setFont(new Font(15));
        text.layoutXProperty().bind(midXProperty.subtract(text.getBoundsInLocal().getWidth()/2).add(3));
        text.layoutYProperty().bind(midYProperty.subtract(3));

        this.getChildren().addAll(edge,text);
    }
}
