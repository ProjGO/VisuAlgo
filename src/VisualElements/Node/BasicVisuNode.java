package VisualElements.Node;

import BasicAnimation.AnimationGenerator;
import Parameters.Parameters;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BasicVisuNode extends Group {
    private static Color circleColor= Parameters.nodeColor;
    private static double radius= Parameters.nodeRadius;

    private Circle circle,outline;
    private Text dataText;
    private SimpleIntegerProperty data;

    public BasicVisuNode(double x, double y, Integer data, boolean dragable){

        circle=new Circle(radius);
        circle.setStrokeWidth(Parameters.nodeStrokeWidth);
        circle.setStroke(Color.BLACK);
        circle.setFill(circleColor);
        circle.setLayoutX(0);
        circle.setLayoutY(0);

        outline=new Circle(1.3*radius);
        outline.setFill(Parameters.emphaAnimaColor);
        outline.setLayoutX(0);
        outline.setLayoutY(0);
        outline.setOpacity(0.0f);

        dataText =new Text(data.toString());
        //dataText=new Text(String.valueOf('A'+data-1));
        dataText.setFont(new Font(20));
        dataText.setFill(Parameters.nodeTextColor);
        dataText.setLayoutX(-0.5* dataText.getBoundsInLocal().getWidth());
        dataText.setLayoutY(0.3* dataText.getBoundsInLocal().getHeight());

        this.data=new SimpleIntegerProperty(data);
        this.data.addListener((prop,oldValue,newValue)->
                dataText.setText(newValue.toString()));

        this.setLayoutX(x);
        this.setLayoutY(y);

        this.setOpacity(0);

        if(dragable)
            setDragable();

        this.getChildren().addAll(outline,circle, dataText);
    }

    public IntegerProperty getDataProperty(){
        return data;
    }

    private void setDragable(){
        MouseEventHandler mouseEventHandler =new MouseEventHandler(this);
        this.setOnMousePressed(mouseEventHandler);
        this.setOnMouseDragged(mouseEventHandler);
        //this.setOnMouseClicked(mouseEventHandler);
    }

    public void setColor(Color color){
        circleColor=color;
        circle.setFill(color);
    }

    public SequentialTransition getEmphasizeAnimation(){
        SequentialTransition emphasizeAnimation=new SequentialTransition();
        for(int i=0;i<Parameters.emphaAnimaTimes;i++) {
            emphasizeAnimation.getChildren().add(AnimationGenerator.getAppearAnimation(outline));
            emphasizeAnimation.getChildren().add(AnimationGenerator.getDisappearAnimation(outline));
        }
        return emphasizeAnimation;
    }

    public SequentialTransition getTextChangeAnima(Integer newValue){//产生节点数字改变的动画并且真的改变节点的值
        SequentialTransition sequentialTransition=new SequentialTransition();
        FadeTransition textDisappear=AnimationGenerator.getDisappearAnimation(dataText);
        //textDisappear.setOnFinished(e->dataText.setText(newValue.toString()));
        textDisappear.setOnFinished(e->data.setValue(newValue));
        FadeTransition textAppear=AnimationGenerator.getAppearAnimation(dataText);
        sequentialTransition.getChildren().addAll(textDisappear,textAppear);
        return sequentialTransition;
    }

    public FadeTransition getSelectedAnimation(){
        FadeTransition fadeTransition=new FadeTransition(Duration.millis(Parameters.emphaAnimaDuration),outline);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(0.7);
        return fadeTransition;
    }

    public FadeTransition getUnselectedAnimation(){
        FadeTransition fadeTransition=new FadeTransition(Duration.millis(Parameters.emphaAnimaDuration),outline);
        fadeTransition.setToValue(0.0);
        return fadeTransition;
    }

    public FillTransition getVisitedAnimation(){
        outline.setOpacity(0.7);
        FillTransition fillTransition=new FillTransition(Duration.millis(Parameters.emphaAnimaDuration),outline);
        fillTransition.setToValue(Color.GREENYELLOW);
        return fillTransition;
    }

    public FadeTransition getDisappearAnimation(){
        return AnimationGenerator.getDisappearAnimation(this);
    }
}

class MouseEventHandler implements  EventHandler<MouseEvent>{

    private Node _node;
    private double oldSceneX, oldSceneY;
    private double oldLayoutX,oldLayoutY;

    MouseEventHandler(Node node){
        _node=node;
    }

    @Override
    public void handle(MouseEvent e){
        if(e.getButton()== MouseButton.PRIMARY) {
            if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                oldSceneX = e.getSceneX();
                oldSceneY = e.getSceneY();
                oldLayoutX = _node.getLayoutX();
                oldLayoutY = _node.getLayoutY();
            } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                double newLayoutX = e.getSceneX() - oldSceneX + oldLayoutX;
                double newLayoutY = e.getSceneY() - oldSceneY + oldLayoutY;
                _node.setLayoutX(newLayoutX);
                _node.setLayoutY(newLayoutY);
            }
            //e.consume();
        }
    }
}
