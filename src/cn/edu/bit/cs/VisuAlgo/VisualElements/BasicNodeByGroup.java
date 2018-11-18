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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BasicNodeByGroup extends Group {
    public Circle circle,outline;
    private Text text;
    private DoubleProperty centerX,centerY;
    private Line XAxis,YAxis;

    public BasicNodeByGroup(double x, double y, double radius, String data){

        XAxis=new Line();
        YAxis=new Line();
        XAxis.setStartX(0); XAxis.setStartY(0);
        XAxis.setEndX(50);  XAxis.setEndY(0);
        YAxis.setStartX(0); YAxis.setStartY(0);
        YAxis.setEndX(0);  YAxis.setEndY(50);
        getChildren().addAll(XAxis,YAxis);

        circle=new Circle(radius);
        circle.setFill(Color.AQUAMARINE);
        circle.setLayoutX(0);
        circle.setLayoutY(0);

        outline=new Circle(1.1*radius);
        outline.setFill(Color.RED);
        outline.setLayoutX(0);
        outline.setLayoutY(0);
        outline.setOpacity(0.0f);

        text=new Text(data);
        text.setFont(new Font(20));
        text.setFill(Color.WHITE);
        text.setLayoutX(-0.5*text.getBoundsInLocal().getWidth());
        text.setLayoutY(0.3*text.getBoundsInLocal().getHeight());

        this.setLayoutX(x);
        this.setLayoutY(y);

        centerX=new SimpleDoubleProperty();
        centerY=new SimpleDoubleProperty();
        centerX.bind(this.layoutXProperty());
        centerY.bind(this.layoutYProperty());

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
        return layoutXProperty();
    }

    public DoubleProperty getCenterY(){
        return layoutYProperty();
    }

    public void setPosition(double x,double y){
        this.setLayoutX(x);
        this.setLayoutY(y);
    }
}
