package VisualElements.Edge;

import VisualElements.Node.BasicNode;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WeightedEdge extends Edge {

    protected SimpleDoubleProperty midXProperty,midYProperty;
    protected SimpleIntegerProperty weight;
    protected Text text;

    protected void initialize(BasicNode from, BasicNode to, BasicEdge basicEdge, Integer _weight){
        super.initialize(from,to,basicEdge);

        weight=new SimpleIntegerProperty(_weight);

        midXProperty=new SimpleDoubleProperty();
        midYProperty=new SimpleDoubleProperty();
        midXProperty.bind(toXProperty.divide(2));
        midYProperty.bind(toYProperty.divide(2));

        text=new Text(weight.getValue().toString());
        text.setFont(new Font(20));
        text.layoutXProperty().bind(midXProperty.subtract(text.getBoundsInLocal().getWidth()/2).add(3));
        text.layoutYProperty().bind(midYProperty.subtract(3));

        this.getChildren().add(text);
    }
}
