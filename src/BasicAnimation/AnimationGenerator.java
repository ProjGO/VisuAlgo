package BasicAnimation;

import Parameters.Parameters;
import VisualElements.Node.BasicVisuNode;
import VisualElements.Edge.Edge;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class AnimationGenerator {

    private static DoubleProperty rate=new SimpleDoubleProperty(1);

    public static void setRate(double targetRate){
        rate.setValue(targetRate);
    }

    public static FadeTransition getAppearAnimation(Node node){
        FadeTransition appearAnimation=new FadeTransition(Duration.millis(Parameters.appearAnimaDuration));
        appearAnimation.setFromValue(0.0f);
        appearAnimation.setToValue(1.0f);
        appearAnimation.setNode(node);
        appearAnimation.rateProperty().bind(rate);
        return appearAnimation;
    }

    public static FadeTransition getAppearAnimation(Group group){
        FadeTransition appearAnimation=new FadeTransition(Duration.millis(Parameters.disappearAnimaDuration));
        appearAnimation.setFromValue(0.0f);
        appearAnimation.setToValue(1.0f);
        appearAnimation.setNode(group);
        appearAnimation.rateProperty().bind(rate);
        return appearAnimation;
    }

    public static FadeTransition getDisappearAnimation(Node node){
        FadeTransition disappearAnimation=new FadeTransition(Duration.millis(Parameters.disappearAnimaDuration));
        disappearAnimation.setFromValue(1.0f);
        disappearAnimation.setToValue(0.0f);
        disappearAnimation.setNode(node);
        disappearAnimation.rateProperty().bind(rate);
        return disappearAnimation;
    }

    public static Timeline getMoveAnimation(Node node,double toX,double toY){
        Timeline timeline=new Timeline();
        KeyValue kvX=new KeyValue(node.layoutXProperty(),toX);
        KeyValue kvY=new KeyValue(node.layoutYProperty(),toY);
        KeyFrame kf=new KeyFrame(Duration.millis(Parameters.moveAnimaDuration),kvX,kvY);
        timeline.getKeyFrames().add(kf);
        timeline.rateProperty().bind(rate);
        timeline.setOnFinished(e->{
            System.out.println(node.getLayoutX());
            System.out.println(node.getLayoutY());
        });
        return timeline;
    }

    public static FillTransition getFillTransition(Shape shape, Color fromColor,Color toColor){
        return new FillTransition(Duration.millis(Parameters.emphaAnimaDuration),shape,fromColor,toColor);
    }

    public static SequentialTransition getNodeEmphAnimation(BasicVisuNode basicVisuNode){
        return basicVisuNode.getEmphasizeAnimation();
    }

    public static SequentialTransition getEdgeEmphaAnimation(Edge edge){
        return edge.getEmphasizeAnimation();
    }


    public static Timeline changeEdgeToNode(AnchorPane pane, Edge edge, double newToNodeX, double newToNodeY, BasicVisuNode newToNode){
        Timeline timeline=new Timeline();
        Circle toPoint=new Circle(0);
        toPoint.setLayoutX(edge.getToX());
        toPoint.setLayoutY(edge.getToY());
        pane.getChildren().add(toPoint);
        edge.bindToLayoutProperty(toPoint.layoutXProperty(),toPoint.layoutYProperty());
        KeyValue kvX=new KeyValue(toPoint.layoutXProperty(),newToNodeX);
        KeyValue kvY=new KeyValue(toPoint.layoutYProperty(),newToNodeY);
        KeyFrame kf=new KeyFrame(Duration.millis(Parameters.moveAnimaDuration),kvX,kvY);
        timeline.getKeyFrames().add(kf);
        timeline.rateProperty().bind(rate);
        timeline.setOnFinished(e->{
            edge.bindToLayoutProperty(newToNode.layoutXProperty(),newToNode.layoutYProperty());
            pane.getChildren().remove(toPoint);
        });
        return timeline;
    }

    public static Timeline changeEdgeFromNode(AnchorPane pane, Edge edge, double newFromNodeX, double newFromNodeY, BasicVisuNode newFromNode){
        Timeline timeline=new Timeline();
        Circle fromPoint=new Circle(0);
        edge.unbindFromLayoutProperty();
        fromPoint.setLayoutX(edge.getFromX());
        fromPoint.setLayoutY(edge.getFromY());
        pane.getChildren().add(fromPoint);
        edge.bindFromLayoutProperty(fromPoint.layoutXProperty(),fromPoint.layoutYProperty());
        KeyValue kvX=new KeyValue(fromPoint.layoutXProperty(),newFromNodeX);
        KeyValue kvY=new KeyValue(fromPoint.layoutYProperty(),newFromNodeY);
        KeyFrame kf=new KeyFrame(Duration.millis(Parameters.moveAnimaDuration),kvX,kvY);
        timeline.getKeyFrames().add(kf);
        timeline.rateProperty().bind(rate);
        timeline.setOnFinished(e->{
            edge.bindFromLayoutProperty(newFromNode.layoutXProperty(),newFromNode.layoutYProperty());
            pane.getChildren().remove(fromPoint);
        });
        return timeline;
    }
}
