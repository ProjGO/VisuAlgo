package UI;

import Algorithm.VisuDFS;
import BasicVisuDS.VisuGraph;
import BasicVisuDS.VisuGraphException;
import VisualElements.Edge.Edge;
import VisualElements.Edge.WDirEdge;
import VisualElements.Edge.WUndirEdge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphController implements Initializable {
    private VisuGraph visuGraph;
    private VisuDFS visuDFS;

    @FXML
    private Button addNodeButton,addEdgeButton,DFSButton,BFSButton;
    @FXML
    private TextField weightField;
    @FXML
    private CheckBox directedCheckBox;
    @FXML
    private AnchorPane graphPane;
    @FXML
    private Text hintInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        visuGraph=new VisuGraph(graphPane);
        visuDFS=new VisuDFS(visuGraph);
        hintInfo.setText("点击\"添加节点\"开始建图");

    }

    public void onAddNodeClick(ActionEvent actionEvent){
        hintInfo.setText("在屏幕上点击以添加节点");
        AtomicBoolean pressed=new AtomicBoolean(false);
        graphPane.setOnMousePressed(e->pressed.set(true));
        graphPane.setOnMouseDragged(e->pressed.set(false));
        graphPane.setOnMouseReleased(e->{
            if(pressed.get()){
                visuGraph.clearAllAnimation();
                visuGraph.addNode(e.getX(), e.getY());
                visuGraph.getAllAnimation().play();
                pressed.set(false);
            }
        });
    }

    public void onAddEdgeClick(ActionEvent actionEvent) {
        hintInfo.setText("请输入权值(0~100)并依次点击起始、终止节点添加有向边");
        AtomicBoolean pressed=new AtomicBoolean(false),fromNodeSelected= new AtomicBoolean(false);
        AtomicInteger fromNodeIdx = new AtomicInteger(),toNodeIdx=new AtomicInteger();

        graphPane.setOnMousePressed(e->pressed.set(true));
        graphPane.setOnMouseDragged(e-> pressed.set(false));
        graphPane.setOnMouseReleased(e->{
            if(pressed.get()) {
                if (!fromNodeSelected.get()) {
                    int _fromIdx = visuGraph.getSelectedNodeIdx(e.getX(), e.getY());
                    if (_fromIdx >= 0) {
                        visuGraph.clearAllAnimation();
                        visuGraph.setNodeSelected(_fromIdx);
                        visuGraph.getAllAnimation().play();
                        fromNodeIdx.set(_fromIdx);
                        fromNodeSelected.set(true);
                    }
                } else {
                    int _toIdx = visuGraph.getSelectedNodeIdx(e.getX(), e.getY());
                    if (_toIdx >= 0 && _toIdx != fromNodeIdx.get()) {
                        toNodeIdx.set(_toIdx);
                        String weightStr=weightField.getText();
                        int state=weightInputJudge(weightStr);
                        Edge newEdge=null;
                        if(state==0) {//输入合法
                            try{
                                if(directedCheckBox.isSelected()) {
                                    newEdge = new WDirEdge(visuGraph.getNode(fromNodeIdx.get()).getVisuNode(), visuGraph.getNode(toNodeIdx.get()).getVisuNode(), Integer.parseInt(weightField.getText()));
                                    visuGraph.addDirEdge(fromNodeIdx.get(), toNodeIdx.get(), newEdge);
                                }else{
                                    newEdge=new WUndirEdge(visuGraph.getNode(fromNodeIdx.get()).getVisuNode(),visuGraph.getNode(toNodeIdx.get()).getVisuNode(),Integer.parseInt(weightField.getText()));
                                    visuGraph.addUDirEdge(fromNodeIdx.get(),toNodeIdx.get(),newEdge);
                                }
                            }catch(VisuGraphException exception){
                                hintInfo.setText(exception.getInfo());
                            }
                            graphPane.getChildren().add(newEdge);
                            visuGraph.clearAllAnimation();
                            visuGraph.addNewAnimation(newEdge.getAppearAnimation(true));
                            visuGraph.setNodeUnselected(fromNodeIdx.get());
                            visuGraph.getAllAnimation().play();
                            fromNodeSelected.set(false);
                            weightField.clear();
                        }else if(state==-1)
                            hintInfo.setText("请先输入权值");
                        else if(state==1)
                            hintInfo.setText("请输入数字");
                        else if(state==2)
                            hintInfo.setText("请输入0~100间的数字");
                    }
                }
                pressed.set(false);
            }
        });

    }

    public void onDFSButtonClick(ActionEvent actionEvent){
        hintInfo.setText("请选择起始节点");
        AtomicBoolean pressed=new AtomicBoolean(false);
        graphPane.setOnMousePressed(e->pressed.set(true));
        graphPane.setOnMouseDragged(e-> pressed.set(false));
        graphPane.setOnMouseReleased(e->{
            visuGraph.clearAllAnimation();
            visuGraph.reset();
            int selectedNodeIdx=visuGraph.getSelectedNodeIdx(e.getX(),e.getY());
            if(selectedNodeIdx>=0)
                visuDFS.setStartNode(selectedNodeIdx);
            visuDFS.start();
            visuGraph.getAllAnimation().play();
        });
    }

    public void onBFSButtonClick(ActionEvent actionEvent) {
    }

    private int weightInputJudge(String input){//0 合法 1 不是数字 2 超过范围
        if(input.isEmpty())
            return -1;
        for(int i=0;i<input.length();i++){
            if(!Character.isDigit(input.charAt(i)))
                return 1;
        }
        int weight=Integer.parseInt(input);
        if(weight<0||weight>100)
            return 2;
        return 0;
    }

}