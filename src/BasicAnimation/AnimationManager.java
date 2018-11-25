package BasicAnimation;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;

import java.util.ArrayList;

public class AnimationManager {
    private ArrayList<Animation> animations=new ArrayList<>();

    public void addNewAnimation(Animation animation){
        animations.add(animation);
    }

    public SequentialTransition getStep(int stepId){
        SequentialTransition sequentialTransition=new SequentialTransition(animations.get(stepId));
        return sequentialTransition;
    }

    public SequentialTransition getAll(){
        SequentialTransition sequentialTransition=new SequentialTransition();
        for(int i=0;i<animations.size();i++)
            sequentialTransition.getChildren().add(animations.get(i));
        return sequentialTransition;
    }

    public void clear(){
        animations.clear();
    }
}
