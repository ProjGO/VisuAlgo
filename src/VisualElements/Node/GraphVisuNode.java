package VisualElements.Node;

import BasicAnimation.AnimationGenerator;
import BasicVisuDS.GraphNode;
import Parameters.Parameters;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GraphVisuNode extends BasicVisuNode {

    private Text distText,lastNodeText;

    public GraphVisuNode(double x,double y,Integer data,boolean dragable){
        super(x,y,data,dragable);

        distText =new Text("∞");
        distText.setFont(new Font(20));
        distText.setLayoutX(-0.5* distText.getBoundsInLocal().getWidth());
        distText.setLayoutY(-Parameters.nodeRadius-5);
        distText.setOpacity(0);
        lastNodeText=new Text();
        lastNodeText.setFont(new Font(20));
        lastNodeText.setLayoutX(-0.5* distText.getBoundsInLocal().getWidth());
        lastNodeText.setLayoutY(Parameters.nodeRadius+5);
        lastNodeText.setOpacity(0);
        getChildren().addAll(distText,lastNodeText);
    }

    public Animation getDistChangeAnima(int newDis){
        Animation textDisappear=AnimationGenerator.getDisappearAnimation(distText);
        textDisappear.setOnFinished(e-> {
            if(newDis== GraphNode.inf)
                distText.setText("∞");
            else
                distText.setText(Integer.toString(newDis));
        });
        Animation textAppear=AnimationGenerator.getAppearAnimation(distText);
        return new SequentialTransition(textDisappear,textAppear);
    }

    public Animation getDistAppearAnima(){
        return AnimationGenerator.getAppearAnimation(distText);
    }

    public Animation getDistDisappearAnima(){
        return AnimationGenerator.getDisappearAnimation(distText);
    }

    public Animation getLastNodeChangeAnima(int newLastNodeId){
        Animation textDisappear=AnimationGenerator.getDisappearAnimation(lastNodeText);
        textDisappear.setOnFinished(e-> {
            if(newLastNodeId== GraphNode.inf)
                lastNodeText.setText("-");
            else
                lastNodeText.setText(Integer.toString(newLastNodeId));
        });
        Animation textAppear=AnimationGenerator.getAppearAnimation(lastNodeText);
        return new SequentialTransition(textDisappear,textAppear);
    }

}
