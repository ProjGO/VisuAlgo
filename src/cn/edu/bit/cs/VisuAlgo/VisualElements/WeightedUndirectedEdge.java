package cn.edu.bit.cs.VisuAlgo.VisualElements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WeightedUndirectedEdge extends Group {

    private SimpleDoubleProperty toXProperty,toYProperty;
    private SimpleDoubleProperty midXProperty,midYProperty;
    private SimpleIntegerProperty weight;
    private UnweightedUndirectedEdge unweightedUndirectedEdge;
    private Text text;

    public WeightedUndirectedEdge(BasicNode from,BasicNode to,double width,int _weight){

        weight=new SimpleIntegerProperty(_weight);

        DoubleProperty zero=new SimpleDoubleProperty(0);

        layoutXProperty().bindBidirectional(from.layoutXProperty());
        layoutYProperty().bindBidirectional(from.layoutYProperty());

        toXProperty=new SimpleDoubleProperty();
        toYProperty=new SimpleDoubleProperty();
        toXProperty.bind(to.layoutXProperty().subtract(from.layoutXProperty()));
        toYProperty.bind(to.layoutYProperty().subtract(from.layoutYProperty()));

        unweightedUndirectedEdge =new UnweightedUndirectedEdge(zero,zero,toXProperty,toYProperty,width);

        midXProperty=new SimpleDoubleProperty();
        midYProperty=new SimpleDoubleProperty();
        midXProperty.bind(toXProperty.divide(2));
        midYProperty.bind(toYProperty.divide(2));

        text=new Text(weight.getValue().toString());
        text.setFont(new Font(15));
        text.layoutXProperty().bind(midXProperty.subtract(text.getBoundsInLocal().getWidth()/2).add(3));
        text.layoutYProperty().bind(midYProperty.subtract(3));
        text.rotateProperty().bind(unweightedUndirectedEdge.getAngleProperty());

        /*final ChangeListener changeListener=(ObservableValue observableValue,Object oldValue,Object newValue)->{
            text.setText(newValue.toString());
        };*/

        this.getChildren().addAll(unweightedUndirectedEdge,text);

    }

    public void setTextFont(Font font){
        text.setFont(font);
    }


}
