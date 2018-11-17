package BasicAnimation;

import cn.edu.bit.cs.VisuAlgo.VisualElements.BasicNodeByGroup;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationGenerator {

    private static DoubleProperty rate=new SimpleDoubleProperty(1);

    public static void setRate(double targetRate){
        rate.setValue(targetRate);
    }

    public static FadeTransition getAppearAnimation(Node node,double time){
        FadeTransition appearAnimation=new FadeTransition(Duration.millis(time));
        appearAnimation.setFromValue(0.0f);
        appearAnimation.setToValue(1.0f);
        appearAnimation.setNode(node);
        appearAnimation.rateProperty().bind(rate);
        return appearAnimation;
    }

    public static FadeTransition getDisappearAnimation(Node node,double time){
        FadeTransition disappearAnimation=new FadeTransition(Duration.millis(time));
        disappearAnimation.setFromValue(1.0f);
        disappearAnimation.setToValue(0.0f);
        disappearAnimation.setNode(node);
        disappearAnimation.rateProperty().bind(rate);
        return disappearAnimation;
    }

    public static TranslateTransition getMoveAnimation(Node node,double time,double toX,double toY){
        TranslateTransition translateTransition=new TranslateTransition(Duration.millis(time));
        translateTransition.setNode(node);
        translateTransition.setFromX(0);
        translateTransition.setFromY(0);
        if(node instanceof BasicNodeByGroup) {
            translateTransition.setToX(toX-((BasicNodeByGroup) node).getRadius()-node.getLayoutX());
            translateTransition.setToY(toY-((BasicNodeByGroup) node).getRadius()-node.getLayoutY());
        }
        else {
            translateTransition.setToX(toX);
            translateTransition.setToY(toY);
        }
        translateTransition.rateProperty().bind(rate);
        return translateTransition;
    }

    public static RotateTransition getRotateAnimation(Node node,double time){
        RotateTransition rotateTransition=new RotateTransition(Duration.millis(time));
        //rotateTransition.setAxis();
        return null;
    }
}
