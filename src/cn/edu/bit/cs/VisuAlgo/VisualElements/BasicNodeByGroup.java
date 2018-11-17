package cn.edu.bit.cs.VisuAlgo.VisualElements;

import BasicAnimation.AnimationGenerator;
import javafx.animation.SequentialTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BasicNodeByGroup extends Group {
    private Circle circle,outline;
    private Text text;
    private DoubleProperty centerX,centerY;

    public BasicNodeByGroup(double x, double y, double radius, String data){

        circle=new Circle(radius);
        circle.setFill(Color.AQUAMARINE);
        circle.setLayoutX(radius);
        circle.setLayoutY(radius);

        outline=new Circle(1.1*radius);
        outline.setFill(Color.RED);
        outline.setLayoutX(radius);
        outline.setLayoutY(radius);
        outline.setOpacity(0.0f);

        text=new Text(data);
        text.setFont(new Font(20));
        text.setFill(Color.WHITE);
        text.setLayoutX(-0.5*text.getBoundsInLocal().getWidth()+radius);
        text.setLayoutY(0.3*text.getBoundsInLocal().getHeight()+radius);

        this.setLayoutX(x-radius);
        this.setLayoutY(y-radius);

        centerX=new SimpleDoubleProperty();
        centerY=new SimpleDoubleProperty();
        centerX.bind(this.layoutXProperty().add(radius));
        centerY.bind(this.layoutYProperty().add(radius));

        this.getChildren().addAll(outline,circle,text);
    }

    public Point2D getCenterCoordinate() {
        return new Point2D(getLayoutX()+circle.getRadius(), getLayoutY()+circle.getRadius());
    }

    public double getRadius(){
        return circle.getRadius();
    }

    public void setText(String targetText){
        text.setText(targetText);
    }

    public SequentialTransition getEmphasizeAnimation(int time,double times){
        SequentialTransition emphasizeAnimation=new SequentialTransition();
        for(int i=0;i<times;i++) {
            emphasizeAnimation.getChildren().add(AnimationGenerator.getAppearAnimation(outline,0.5*time));
            emphasizeAnimation.getChildren().add(AnimationGenerator.getDisappearAnimation(outline,0.5*time));
        }
        return emphasizeAnimation;
    }

    public DoubleProperty getCenterX(){
        return centerX;
    }

    public DoubleProperty getCenterY(){
        return centerY;
    }
}
