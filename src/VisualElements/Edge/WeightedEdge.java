package VisualElements.Edge;

import BasicAnimation.AnimationGenerator;
import Parameters.Parameters;
import VisualElements.Node.BasicVisuNode;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WeightedEdge extends Edge {

    private SimpleDoubleProperty midXProperty,midYProperty;
    private Text text;

    protected void initialize(BasicVisuNode from, BasicVisuNode to, BasicEdge basicEdge, Integer _weight){
        super.initialize(from,to,basicEdge);

        weight=new SimpleIntegerProperty(_weight);
        weight.addListener((prop,oldValue,newValue)->text.setText(newValue.toString()));

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

    @Override
    public Animation getEmphasizeAnimation(){
        SequentialTransition edgeEmpha=new SequentialTransition(),textEmpha=new SequentialTransition();
        for(int i = 0; i< Parameters.emphaAnimaTimes; i++){
            edgeEmpha.getChildren().add(AnimationGenerator.getFillTransition(basicEdge,Parameters.edgeColor,Parameters.emphaAnimaColor));
            edgeEmpha.getChildren().add(AnimationGenerator.getFillTransition(basicEdge,Parameters.emphaAnimaColor,Parameters.edgeColor));
            textEmpha.getChildren().add(AnimationGenerator.getFillTransition(text,Color.BLACK,Parameters.emphaAnimaColor));
            textEmpha.getChildren().add(AnimationGenerator.getFillTransition(text,Parameters.emphaAnimaColor,Color.BLACK));
        }
        ParallelTransition emphaAnima=new ParallelTransition(edgeEmpha,textEmpha);
        return emphaAnima;
    }

    @Override
    public Animation getAppearAnimation(){
        Animation appearAnimation=super.getAppearAnimation();
        ((SequentialTransition) appearAnimation).getChildren().add(AnimationGenerator.getAppearAnimation(text));
        return appearAnimation;
    }

    @Override
    public Animation getAppearAnimation(boolean fromTo){
        Animation appearAnimation=super.getAppearAnimation(fromTo);
        ((SequentialTransition) appearAnimation).getChildren().add(AnimationGenerator.getAppearAnimation(text));
        return appearAnimation;
    }

}
