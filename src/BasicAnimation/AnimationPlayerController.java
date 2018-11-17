package BasicAnimation;

import javafx.animation.Animation;

import java.util.ArrayList;

public class AnimationPlayerController {

    private ArrayList<Animation> animations=new ArrayList<>();
    private int curAnimationIndex;

    public void addAnimation(Animation animation){
        animations.add(animation);
        if(animations.size()>1)
            animations.get(animations.size()-2).setOnFinished(e->{
                animations.get(animations.size()-1).play();
            });
    }

    public void playFrom(int index){
        animations.get(index).play();
    }

    public void playFromStart(){
        playFrom(0);
    }
}
