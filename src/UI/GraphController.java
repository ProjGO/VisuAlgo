package UI;

import BasicVisuDS.VisuGraph;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphController implements Initializable {
    VisuGraph visuGraph;

    @FXML
    private Button addNodeButton,addEdgeButton;

    @FXML
    private TextField weightField;

    @FXML
    private AnchorPane graphPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        visuGraph=new VisuGraph(graphPane);
    }

    public void setAnchorPane(AnchorPane anchorPane){
        visuGraph.setAnchorPane(anchorPane);
    }

    public void onAddNodeClick(ActionEvent actionEvent){

        EventHandler<MouseEvent> mouseEventEventHandler=new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("cyka");
            }
        };

        graphPane.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEventEventHandler);
    }

    public void onAddEdgeClick(ActionEvent actionEvent) {
    }
}





class AddNodeHandler implements EventHandler<MouseEvent> {

    AnchorPane root;

    public AddNodeHandler(AnchorPane root){
        this.root=root;
    }

    @Override
    public void handle(MouseEvent e){
        if(e.getEventType()==MouseEvent.MOUSE_CLICKED&&e.getButton()== MouseButton.PRIMARY) {

        }
    }
}
