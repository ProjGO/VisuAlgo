package cn.edu.bit.cs.VisuAlgo.VisualElements;

import BasicAnimation.AnimationGenerator;
import javafx.animation.SequentialTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BasicNodeByStackPane extends StackPane {
    private Circle circle,outline;
    private Text text;
    private DoubleProperty centerX,centerY;

    public BasicNodeByStackPane(double x, double y, double radius, String data){

        circle=new Circle(radius);
        circle.setFill(Color.AQUAMARINE);

        outline=new Circle(1.1*radius);
        outline.setFill(Color.RED);
        outline.setOpacity(0.0f);

        text=new Text(data);
        text.setFont(new Font(20));
        text.setFill(Color.WHITE);

        this.setLayoutX(x);
        this.setLayoutY(y);

        centerX=new SimpleDoubleProperty();
        centerY=new SimpleDoubleProperty();
        centerX.bind(this.layoutXProperty());
        centerY.bind(this.layoutYProperty());

        this.setOpacity(0.5f);
        this.getChildren().addAll(outline,circle,text);
    }

    public Point2D getCenterCoordinate() {
        return new Point2D(getLayoutX(), getLayoutY());
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

    public void setPosition(double x,double y){
        this.setLayoutX(x);
        this.setLayoutY(y);
    }
}
