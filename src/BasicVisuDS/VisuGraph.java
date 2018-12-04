package BasicVisuDS;

import BasicAnimation.AnimationGenerator;
import VisualElements.Node.BasicNode;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class VisuGraph {

}

class ClickHandler implements EventHandler<MouseEvent> {

    private AnchorPane root;

    public ClickHandler(AnchorPane root){
        this.root=root;
    }

    @Override
    public void handle(MouseEvent e){
        if(e.getEventType()==MouseEvent.MOUSE_CLICKED&&e.getButton()== MouseButton.SECONDARY) {
            BasicNode basicNode=new BasicNode(e.getSceneX(), e.getSceneY(), 5, true);
            root.getChildren().add(basicNode);
            AnimationGenerator.getAppearAnimation(basicNode).play();
        }
    }
}