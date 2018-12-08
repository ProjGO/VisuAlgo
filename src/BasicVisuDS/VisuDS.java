package BasicVisuDS;

import BasicAnimation.AnimationManager;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.AnchorPane;

abstract public class VisuDS {
    protected AnimationManager animationManager=new AnimationManager();

    public void clearAllAnimation(){
        animationManager.clear();
    }

    public SequentialTransition getAllAnimation(){
        return animationManager.getAll();
    }

    abstract public void setAnchorPane(AnchorPane anchorPane);

    public void addNewAnimation(Animation animation){
        animationManager.addNewAnimation(animation);
    }
}
