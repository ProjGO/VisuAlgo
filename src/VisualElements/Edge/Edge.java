package VisualElements.Edge;

import BasicAnimation.AnimationGenerator;
import Parameters.Parameters;
import VisualElements.Node.BasicNode;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Edge extends Group {

    protected SimpleDoubleProperty toXProperty = new SimpleDoubleProperty(), toYProperty = new SimpleDoubleProperty();//这里的是这个Group内部的坐标值
    private DoubleProperty fromX,fromY,toX,toY;//这里的值是AnchorPane中的坐标值
    private BasicEdge basicEdge;

    private SimpleDoubleProperty zero = new SimpleDoubleProperty(0);

    protected void initialize(BasicNode from, BasicNode to, BasicEdge _basicEdge) {
        basicEdge=_basicEdge;
        bindLayoutProperty(from.layoutXProperty(),from.layoutYProperty(),to.layoutXProperty(),to.layoutYProperty());
        basicEdge.setFromNodeXProperty(zero);
        basicEdge.setFromNodeYProperty(zero);
        basicEdge.setToNodeXProperty(toXProperty);
        basicEdge.setToNodeYProperty(toYProperty);
        basicEdge.initialize();
        this.getChildren().add(basicEdge);

        /*Line lineX=new Line(0,0,50,0);
        Line lineY=new Line(0,0,0,50);
        getChildren().addAll(lineX,lineY);*/
    }

    public void bindLayoutProperty(DoubleProperty _fromX,DoubleProperty _fromY,DoubleProperty _toX,DoubleProperty _toY){
        bindFromLayoutProperty(_fromX,_fromY);
        bindToLayoutProperty(_toX,_toY);
    }

    public void bindFromLayoutProperty(DoubleProperty _fromX,DoubleProperty _fromY){
        fromX=_fromX;
        fromY=_fromY;
        layoutXProperty().bindBidirectional(fromX);
        layoutYProperty().bindBidirectional(fromY);
    }

    public void bindToLayoutProperty(DoubleProperty _toX,DoubleProperty _toY){
        toX=_toX;
        toY=_toY;
        toXProperty.bind(toX.subtract(layoutXProperty()));
        toYProperty.bind(toY.subtract(layoutYProperty()));
    }

    public void unbindFromLayoutProperty(){
        layoutXProperty().unbindBidirectional(fromX);
        layoutYProperty().unbindBidirectional(fromY);
    }

    public void unbindToLayoutProperty(){
        toXProperty.unbind();
        toYProperty.unbind();
    }

    public double getToX(){ return toX.get(); }
    public double getToY(){ return toY.get(); }
    public double getFromX() {return fromX.get();}
    public double getFromY() {return fromY.get();}

    public void setFillColor(Color color){
        basicEdge.setFill(color);
    }

    public SequentialTransition getEmphasizeAnimation(){
        SequentialTransition sequentialTransition=new SequentialTransition();
        for(int i=0;i<Parameters.emphaAnimaTimes;i++){
            sequentialTransition.getChildren().add(AnimationGenerator.getFillTransition(basicEdge,Parameters.edgeColor,Parameters.emphaAnimaColor));
            sequentialTransition.getChildren().add(AnimationGenerator.getFillTransition(basicEdge,Parameters.emphaAnimaColor,Parameters.edgeColor));
        }
        return sequentialTransition;
    }

    public SequentialTransition getFromToEmphaAnimation(){
        BasicNode tempFromNode=new BasicNode(0,0,0,false),tempToNode=new BasicNode(0,0,0,false);
        UnwUndirEdge tempEdge=new UnwUndirEdge(tempFromNode,tempToNode);
        tempEdge.setFillColor(Color.ORANGE);
        getChildren().addAll(tempFromNode,tempToNode,tempEdge);
        SequentialTransition sequentialTransition=new SequentialTransition();
        sequentialTransition.getChildren().add(AnimationGenerator.getMoveAnimation(tempToNode,toXProperty.get(),toYProperty.get()));
        sequentialTransition.getChildren().add(AnimationGenerator.getDisappearAnimation(tempEdge));
        sequentialTransition.setOnFinished(e-> {
            getChildren().removeAll(tempFromNode,tempToNode,tempEdge);
            System.out.println("done");
        });
        return sequentialTransition;
    }

    public SequentialTransition getToFromEmphaAnimation(){
        BasicNode tempFromNode=new BasicNode(toXProperty.get(),toYProperty.get(),0,false),tempToNode=new BasicNode(toXProperty.get(),toYProperty.get(),0,false);
        UnwUndirEdge tempEdge=new UnwUndirEdge(tempFromNode,tempToNode);
        tempEdge.setFillColor(Color.ORANGE);
        getChildren().addAll(tempFromNode,tempToNode,tempEdge);
        SequentialTransition sequentialTransition=new SequentialTransition();
        sequentialTransition.getChildren().add(AnimationGenerator.getMoveAnimation(tempToNode,0,0));
        sequentialTransition.getChildren().add(AnimationGenerator.getDisappearAnimation(tempEdge));
        sequentialTransition.setOnFinished(e-> getChildren().removeAll(tempFromNode,tempToNode,tempEdge));
        return sequentialTransition;
    }

    public SequentialTransition getAppearAnimation(){
        SequentialTransition appearAnimation=new SequentialTransition();

        BasicNode TempFromNode=new BasicNode(toXProperty.get(),toYProperty.get(),0,false);
        getChildren().add(TempFromNode);
        basicEdge.setFromNodeXProperty(TempFromNode.layoutXProperty());
        basicEdge.setFromNodeYProperty(TempFromNode.layoutYProperty());
        basicEdge.setFill(Color.ORANGE);
        appearAnimation.getChildren().add(AnimationGenerator.getMoveAnimation(TempFromNode,0,0));
        FillTransition fillTransition=new FillTransition(Duration.millis(500),basicEdge,Color.ORANGE,Parameters.edgeColor);
        appearAnimation.getChildren().add(fillTransition);
        appearAnimation.setOnFinished(e->{
            basicEdge.setFromNodeXProperty(zero);
            basicEdge.setFromNodeYProperty(zero);
            getChildren().remove(TempFromNode);
        });
        return appearAnimation;
    }
}