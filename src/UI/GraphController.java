package UI;

import BasicAnimation.AnimationGenerator;
import BasicVisuDS.VisuGraph;
import VisualElements.Node.BasicVisuNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphController implements Initializable {
    VisuGraph visuGraph;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        visuGraph=new VisuGraph(null);
    }

    public void setAnchorPane(AnchorPane anchorPane){
        visuGraph.setAnchorPane(anchorPane);
    }

    public void onAddNodeClick(ActionEvent actionEvent){

    }

    public void onAddEdgeClick(ActionEvent actionEvent) {
    }
}





class AddNodeHandler implements EventHandler<MouseEvent> {

    private AnchorPane root;

    public AddNodeHandler(AnchorPane root){
        this.root=root;
    }

    @Override
    public void handle(MouseEvent e){
        if(e.getEventType()==MouseEvent.MOUSE_CLICKED&&e.getButton()== MouseButton.PRIMARY) {
            BasicVisuNode basicVisuNode =new BasicVisuNode(e.getSceneX(), e.getSceneY(), 5, true);
            root.getChildren().add(basicVisuNode);
            AnimationGenerator.getAppearAnimation(basicVisuNode).play();
        }
    }
}
