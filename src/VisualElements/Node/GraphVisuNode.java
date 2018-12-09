package VisualElements.Node;

import BasicAnimation.AnimationGenerator;
import Parameters.Parameters;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GraphVisuNode extends BasicVisuNode {

    private Text distText;

    public GraphVisuNode(double x,double y,Integer data,boolean dragable){
        super(x,y,data,dragable);

        distText =new Text("∞");
        distText.setFont(new Font(20));
        distText.setLayoutX(-0.5* distText.getBoundsInLocal().getWidth());
        distText.setLayoutY(-Parameters.nodeRadius-5);
        distText.setOpacity(0);
        getChildren().add(distText);

    }

    public Animation getDisChangeAnima(int newDis){
        Animation textDisappear=AnimationGenerator.getDisappearAnimation(distText);
        textDisappear.setOnFinished(e-> distText.setText(Integer.toString(newDis)));
        Animation textAppear=AnimationGenerator.getAppearAnimation(distText);
        return new SequentialTransition(textDisappear,textAppear);
    }

    public Animation setDistVisibleAnima(){
        return AnimationGenerator.getAppearAnimation(distText);
    }

}
