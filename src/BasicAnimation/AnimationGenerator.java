package BasicAnimation;

import javafx.animation.*;
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

    public static Timeline getMoveAnimation(Node node,double time,double toX,double toY){
        Timeline timeline=new Timeline();
        KeyValue kvX=new KeyValue(node.layoutXProperty(),toX);
        KeyValue kvY=new KeyValue(node.layoutYProperty(),toY);
        KeyFrame kf=new KeyFrame(Duration.millis(time),kvX,kvY);
        timeline.getKeyFrames().add(kf);
        timeline.rateProperty().bind(rate);
        return timeline;
    }

    public static RotateTransition getRotateAnimation(Node node,double time){
        RotateTransition rotateTransition=new RotateTransition(Duration.millis(time));
        //rotateTransition.setAxis();
        return null;
    }
}
