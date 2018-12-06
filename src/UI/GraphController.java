package UI;

import BasicVisuDS.VisuGraph;
import VisualElements.Edge.Edge;
import VisualElements.Edge.WDirEdge;
import VisualElements.Node.BasicVisuNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.plaf.basic.BasicBorders;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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

    public void onAddNodeClick(ActionEvent actionEvent){
        graphPane.setOnMouseClicked(e-> {
            visuGraph.clearAllAnimation();
            visuGraph.addNode(e.getX(), e.getY());
            visuGraph.getAllAnimation().play();
        });
    }

    public void onAddEdgeClick(ActionEvent actionEvent) {
        AtomicBoolean fromNodeSelected= new AtomicBoolean(false);
        AtomicInteger fromNodeIdx = new AtomicInteger(),toNodeIdx=new AtomicInteger();

        graphPane.setOnMouseClicked(e->{
            if(!fromNodeSelected.get()) {
                int _fromIdx = visuGraph.getSelectedNodeIdx(e.getX(), e.getY());
                if (_fromIdx >= 0) {
                    visuGraph.clearAllAnimation();
                    visuGraph.setNodeSelected(_fromIdx);
                    visuGraph.getAllAnimation().play();
                    fromNodeIdx.set(_fromIdx);
                    fromNodeSelected.set(true);
                }
            }else{
                int _toIdx=visuGraph.getSelectedNodeIdx(e.getX(),e.getY());
                if(_toIdx>=0&&_toIdx!=fromNodeIdx.get()){
                    toNodeIdx.set(_toIdx);
                    WDirEdge wDirEdge=new WDirEdge(visuGraph.getNode(fromNodeIdx.get()).getVisuNode(),visuGraph.getNode(toNodeIdx.get()).getVisuNode(),Integer.parseInt(weightField.getText()));
                    visuGraph.addEdge(fromNodeIdx.get(),toNodeIdx.get(),wDirEdge);
                    visuGraph.setNodeSelected(toNodeIdx.get());
                    visuGraph.setNodeUnselected(fromNodeIdx.get());
                    visuGraph.setNodeUnselected(toNodeIdx.get());
                    visuGraph.getAllAnimation().play();
                    fromNodeSelected.set(false);
                }
            }
        });

    }
}