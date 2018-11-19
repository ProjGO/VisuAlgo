package cn.edu.bit.cs.VisuAlgo.VisualElements;

import BasicAnimation.AnimationGenerator;
import javafx.animation.SequentialTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BasicNode extends Group {
    private Circle circle,outline;
    private Text text;
    private DoubleProperty centerX,centerY;
    private Line XAxis,YAxis;
    private Color circleColor=Color.AQUAMARINE;
    private SimpleIntegerProperty data;

    public BasicNode(double x, double y, double radius, String data){

        XAxis=new Line();
        YAxis=new Line();
        XAxis.setStartX(0); XAxis.setStartY(0);
        XAxis.setEndX(50);  XAxis.setEndY(0);
        YAxis.setStartX(0); YAxis.setStartY(0);
        YAxis.setEndX(0);  YAxis.setEndY(50);
        getChildren().addAll(XAxis,YAxis);

        circle=new Circle(radius);
        circle.setFill(circleColor);
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

    public void setDragable(){
        DragEventHandler dragEventHandler=new DragEventHandler(this);
        this.setOnMousePressed(dragEventHandler);
        this.setOnMouseDragged(dragEventHandler);
    }

    public void setColor(Color color){
        circleColor=color;
        circle.setFill(color);
    }
}

class DragEventHandler implements  EventHandler<MouseEvent>{

    Node _node;
    double oldSceneX, oldSceneY;
    double oldLayoutX,oldLayoutY;

    public DragEventHandler(Node node){
        _node=node;
    }

    @Override
    public void handle(MouseEvent e){
        if(e.getEventType()==MouseEvent.MOUSE_PRESSED){
            oldSceneX =e.getSceneX();
            oldSceneY =e.getSceneY();
            oldLayoutX=_node.getLayoutX();
            oldLayoutY= _node.getLayoutY();
        } else if(e.getEventType()==MouseEvent.MOUSE_DRAGGED) {
            double newLayoutX=e.getSceneX() - oldSceneX + oldLayoutX;
            double newLayoutY=e.getSceneY() - oldSceneY + oldLayoutY;
            _node.setLayoutX(newLayoutX);
            _node.setLayoutY(newLayoutY);
        }
    }
}