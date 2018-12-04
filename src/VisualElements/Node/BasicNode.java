package VisualElements.Node;

import BasicAnimation.AnimationGenerator;
import Parameters.Parameters;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BasicNode extends Group {
    private static Color circleColor= Parameters.nodeColor;
    private static double radius= Parameters.nodeRadius;

    private Circle circle,outline;
    private Text text;
    private SimpleIntegerProperty data;

    private static boolean isDragging=false;

    public BasicNode(double x, double y, Integer _data,boolean dragable){

        circle=new Circle(radius);
        circle.setStrokeWidth(Parameters.nodeStrokeWidth);
        circle.setStroke(Color.BLACK);
        circle.setFill(circleColor);
        circle.setLayoutX(0);
        circle.setLayoutY(0);

        outline=new Circle(1.3*radius);
        outline.setFill(Color.RED);
        outline.setLayoutX(0);
        outline.setLayoutY(0);
        outline.setOpacity(0.0f);

        text=new Text(_data.toString());
        text.setFont(new Font(20));
        text.setFill(Parameters.nodeTextColor);
        text.setLayoutX(-0.5*text.getBoundsInLocal().getWidth());
        text.setLayoutY(0.3*text.getBoundsInLocal().getHeight());

        data=new SimpleIntegerProperty(_data);
        ChangeListener changeListener=(prop,oldValue,newValue)->
            text.setText(newValue.toString());
        data.addListener(changeListener);

        this.setLayoutX(x);
        this.setLayoutY(y);

        this.setOpacity(0);

        if(dragable)
            setDragable();

        this.getChildren().addAll(outline,circle,text);
    }

    public IntegerProperty getDataProperty(){
        return data;
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
            if(e.getButton()== MouseButton.PRIMARY) {
                if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    System.out.println("clicked");
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
            }
        }
    }
    private void setDragable(){
        DragEventHandler dragEventHandler=new DragEventHandler(this);
        this.setOnMousePressed(dragEventHandler);
        this.setOnMouseDragged(dragEventHandler);
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
        FadeTransition textDisappear=AnimationGenerator.getDisappearAnimation(text);
        //textDisappear.setOnFinished(e->text.setText(newValue.toString()));
        textDisappear.setOnFinished(e->data.setValue(newValue));
        FadeTransition textAppear=AnimationGenerator.getAppearAnimation(text);
        sequentialTransition.getChildren().addAll(textDisappear,textAppear);
        return sequentialTransition;
    }
}
