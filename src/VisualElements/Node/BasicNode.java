package VisualElements.Node;

import BasicAnimation.AnimationGenerator;
import VisualElements.ElementParameters;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BasicNode extends Group {
    private static Color circleColor= ElementParameters.nodeColor;
    private static double radius=ElementParameters.nodeRadius;

    private Circle circle,outline;
    private Text text;
    private SimpleIntegerProperty data;

    private static boolean isDragging=false;

    public BasicNode(double x, double y, Integer _data,boolean dragable){

        circle=new Circle(radius);
        circle.setStrokeWidth(5);
        circle.setStroke(Color.BLACK);
        circle.setFill(circleColor);
        circle.setLayoutX(0);
        circle.setLayoutY(0);

        outline=new Circle(1.1*radius);
        outline.setFill(Color.RED);
        outline.setLayoutX(0);
        outline.setLayoutY(0);
        outline.setOpacity(0.0f);

        text=new Text(_data.toString());
        text.setFont(new Font(20));
        text.setFill(ElementParameters.nodeTextColor);
        text.setLayoutX(-0.5*text.getBoundsInLocal().getWidth());
        text.setLayoutY(0.3*text.getBoundsInLocal().getHeight());

        data=new SimpleIntegerProperty(_data);
        ChangeListener changeListener=(prop,oldValue,newValue)->
            text.setText(newValue.toString());
        data.addListener(changeListener);

        this.setLayoutX(x);
        this.setLayoutY(y);

        if(dragable)
            setDragable();

        this.getChildren().addAll(outline,circle,text);
    }

    public SequentialTransition getEmphasizeAnimation(int time,double times){
        SequentialTransition emphasizeAnimation=new SequentialTransition();
        for(int i=0;i<times;i++) {
            emphasizeAnimation.getChildren().add(AnimationGenerator.getAppearAnimation(outline,0.5*time));
            emphasizeAnimation.getChildren().add(AnimationGenerator.getDisappearAnimation(outline,0.5*time));
        }
        return emphasizeAnimation;
    }

    public IntegerProperty getDataProperty(){
        return data;
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
}
