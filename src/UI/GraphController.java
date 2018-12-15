package UI;

import Algorithm.Graph.*;
import BasicAnimation.AnimationGenerator;
import BasicVisuDS.VisuGraph;
import BasicVisuDS.VisuGraphException;
import VisualElements.Edge.*;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphController  extends BasicController implements Initializable {
    private VisuGraph visuGraph;

    @FXML
    private Button addNodeButton,addEdgeButton,DFSButton,BFSButton;
    @FXML
    private TextField weightField;
    @FXML
    private CheckBox directedCheckBox,weightedCheckBox;
    @FXML
    private AnchorPane graphPane;
    @FXML
    private Text hintInfo,instructionText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        visuGraph=new VisuGraph(graphPane);
        hintInfo.setFont(new Font(15));
        instructionText.setFont(new Font(20));
        hintInfo.setText("点击\"添加节点\"开始建图");
        instructionText.setText("");
        weightField.setDisable(true);
        weightedCheckBox.selectedProperty().addListener((prop,oldValue,newValue)->{
            if(!newValue){
                weightField.setText("");
                weightField.setDisable(true);
            }else{
                weightField.setDisable(false);
            }
        });
    }

    public void onAddNodeClick(ActionEvent actionEvent){
        AnimationGenerator.setRate(3.0);
        reset();
        visuGraph.getAllAnimation().play();
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
        AnimationGenerator.setRate(3.0);
        reset();
        visuGraph.getAllAnimation().play();
        hintInfo.setText("请输入权值(0~100)并依次点击起始、终止节点添加有向边(若不输入则将随机赋值)");
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
                        int state=Utilities.judgeInput(weightStr);
                        Edge newEdge=null;
                        if(state==0||state==-1) {//输入合法或没有输入
                            visuGraph.clearAllAnimation();
                            if(state==-1)
                                weightField.setText(Integer.toString(new Random().nextInt()%20+20));
                            try{
                                if(directedCheckBox.isSelected()) {
                                    if(weightedCheckBox.isSelected())
                                        newEdge = new WDirEdge(visuGraph.getNode(fromNodeIdx.get()).getVisuNode(), visuGraph.getNode(toNodeIdx.get()).getVisuNode(), Integer.parseInt(weightField.getText()));
                                    else
                                        newEdge=new UnwDirEdge(visuGraph.getNode(fromNodeIdx.get()).getVisuNode(), visuGraph.getNode(toNodeIdx.get()).getVisuNode());
                                    visuGraph.addDirEdge(fromNodeIdx.get(), toNodeIdx.get(), newEdge);
                                }else{
                                    if(weightedCheckBox.isSelected())
                                        newEdge=new WUndirEdge(visuGraph.getNode(fromNodeIdx.get()).getVisuNode(),visuGraph.getNode(toNodeIdx.get()).getVisuNode(),Integer.parseInt(weightField.getText()));
                                    else
                                        newEdge=new UnwUndirEdge(visuGraph.getNode(fromNodeIdx.get()).getVisuNode(),visuGraph.getNode(toNodeIdx.get()).getVisuNode());
                                    visuGraph.addUDirEdge(fromNodeIdx.get(),toNodeIdx.get(),newEdge);
                                }
                            }catch(VisuGraphException exception){
                                hintInfo.setText(exception.getInfo());
                                visuGraph.clearAllAnimation();
                                visuGraph.setNodeUnselected(fromNodeIdx.get());
                                visuGraph.getAllAnimation().play();
                                fromNodeSelected.set(false);
                                return;
                            }
                            graphPane.getChildren().add(newEdge);
                            visuGraph.addNewAnimation(newEdge.getAppearAnimation(true));
                            visuGraph.setNodeUnselected(fromNodeIdx.get());
                            visuGraph.getAllAnimation().play();
                            fromNodeSelected.set(false);
                            weightField.clear();
                        }else if(state==1)
                            hintInfo.setText("请输入数字");
                        else if(state==2)
                            hintInfo.setText("请输入0~100间的数字");
                    }
                }
                pressed.set(false);
            }
        });

    }

    public void onDeleteClick(ActionEvent actionEvent) {
        reset();
        visuGraph.getAllAnimation().play();
        hintInfo.setText("请选择要删除的节点");
        AtomicBoolean pressed=new AtomicBoolean(false);
        graphPane.setOnMousePressed(e->pressed.set(true));
        graphPane.setOnMouseDragged(e-> pressed.set(false));
        graphPane.setOnMouseReleased(e->{
            if(pressed.get()) {
                visuGraph.clearAllAnimation();
                visuGraph.resetSelectState();
                int selectedNodeIdx = visuGraph.getSelectedNodeIdx(e.getX(), e.getY());
                if (selectedNodeIdx >= 0)
                    visuGraph.deleteNode(selectedNodeIdx);
                Animation animation = visuGraph.getAllAnimation();
                animation.setOnFinished(event -> hintInfo.setText("完毕"));
                animation.play();
            }
        });
    }

    public void onDFSButtonClick(ActionEvent actionEvent){
        VisuDFS visuDFS=new VisuDFS(visuGraph);
        reset();
        visuGraph.getAllAnimation().play();
        hintInfo.setText("请选择起始节点");
        AtomicBoolean pressed=new AtomicBoolean(false);
        graphPane.setOnMousePressed(e->pressed.set(true));
        graphPane.setOnMouseDragged(e-> pressed.set(false));
        graphPane.setOnMouseReleased(e->{
            if(pressed.get()) {
                visuGraph.clearAllAnimation();
                visuGraph.resetSelectState();
                int selectedNodeIdx = visuGraph.getSelectedNodeIdx(e.getX(), e.getY());
                if (selectedNodeIdx >= 0)
                    visuDFS.setStartNodeIdx(selectedNodeIdx);
                visuDFS.start();
                Animation animation = visuGraph.getAllAnimation();
                animation.setOnFinished(event -> hintInfo.setText("完毕"));
                animation.play();
            }
        });
    }

    public void onBFSButtonClick(ActionEvent actionEvent) {
        VisuBFS visuBFS=new VisuBFS(visuGraph);
        reset();
        visuGraph.getAllAnimation().play();
        hintInfo.setText("请选择起始节点");
        AtomicBoolean pressed=new AtomicBoolean(false);
        graphPane.setOnMousePressed(e->pressed.set(true));
        graphPane.setOnMouseDragged(e-> pressed.set(false));
        graphPane.setOnMouseReleased(e->{
            if(pressed.get()) {
                visuGraph.clearAllAnimation();
                visuGraph.resetSelectState();
                int selectedNodeIdx = visuGraph.getSelectedNodeIdx(e.getX(), e.getY());
                if (selectedNodeIdx >= 0)
                    visuBFS.setStartNodeIdx(selectedNodeIdx);
                visuBFS.start();
                Animation animation = visuGraph.getAllAnimation();
                animation.setOnFinished(event -> hintInfo.setText("完毕"));
                animation.play();
            }
        });
    }

    public void onDijkstraClick(ActionEvent actionEvent) {
        Dijkstra dijkstra=new Dijkstra(visuGraph,instructionText);
        AnimationGenerator.setRate(1.0);
        visuGraph.clearAllAnimation();
        visuGraph.resetDistLastAndGetAnima();
        visuGraph.showAllDistAndLastNode();
        visuGraph.resetSelectState();
        visuGraph.getAllAnimation().play();
        hintInfo.setText("请选择起始节点");
        AtomicBoolean pressed=new AtomicBoolean(false);
        graphPane.setOnMousePressed(e->pressed.set(true));
        graphPane.setOnMouseDragged(e-> pressed.set(false));
        graphPane.setOnMouseReleased(e-> {
            if(pressed.get()) {
                //AnimationGenerator.setRate(1.0);
                visuGraph.clearAllAnimation();
                visuGraph.resetSelectState();
                visuGraph.resetDistLastAndGetAnima();
                int selectedNodeIdx = visuGraph.getSelectedNodeIdx(e.getX(), e.getY());
                if (selectedNodeIdx >= 0)
                    dijkstra.setStartNodeIdx(selectedNodeIdx);
                dijkstra.start();
                Animation animation = visuGraph.getAllAnimation();
                animation.setOnFinished(event -> {
                    hintInfo.setText("完毕");
                    instructionText.setText("所有节点的最短距离已找到");
                });
                animation.playFromStart();
            }
        });
    }

    public void onPrimClicked(ActionEvent actionEvent) {
        Prim prim=new Prim(visuGraph,instructionText);
        AnimationGenerator.setRate(1.0);
        visuGraph.clearAllAnimation();
        visuGraph.resetDistLastAndGetAnima();
        visuGraph.showAllDistAndLastNode();
        visuGraph.resetSelectState();
        visuGraph.getAllAnimation().play();
        hintInfo.setText("请选择根节点");
        AtomicBoolean pressed=new AtomicBoolean(false);
        graphPane.setOnMousePressed(e->pressed.set(true));
        graphPane.setOnMouseDragged(e-> pressed.set(false));
        graphPane.setOnMouseReleased(e-> {
            if(pressed.get()) {
                //AnimationGenerator.setRate(1.0);
                visuGraph.clearAllAnimation();
                visuGraph.resetSelectState();
                visuGraph.resetDistLastAndGetAnima();
                int selectedNodeIdx = visuGraph.getSelectedNodeIdx(e.getX(), e.getY());
                if (selectedNodeIdx >= 0)
                    prim.setRootNodeIdx(selectedNodeIdx);
                int weight=prim.start();
                Animation animation = visuGraph.getAllAnimation();
                animation.setOnFinished(event -> {
                    hintInfo.setText("完毕");
                    instructionText.setText("最小生成树已找到,权值为"+weight);
                });
                animation.playFromStart();
            }
        });
    }

    public void onKruskalClick(ActionEvent actionEvent) {
        reset();
        AnimationGenerator.setRate(0.5);
        hintInfo.setText("...");
        visuGraph.getAllAnimation().play();
        Kruskal kruskal=new Kruskal(visuGraph,instructionText);
        int weight=kruskal.start();
        Animation kruskalAnimation=visuGraph.getAllAnimation();
        kruskalAnimation.setOnFinished(e-> {
            instructionText.setText("最小生成树已找到,权值为" + weight);
            hintInfo.setText("完毕");
        });
        kruskalAnimation.play();
    }

    public void onClearAllClick(ActionEvent actionEvent) {
        visuGraph.clearAllAnimation();
        visuGraph.clearAll();
        visuGraph.getAllAnimation().play();
    }

    private void reset(){
        visuGraph.clearAllAnimation();
        visuGraph.resetSelectState();
        visuGraph.resetLastVis();
        visuGraph.hideAllDistAndLastNode();
        instructionText.setText("");
    }

    public void onPrimEntered(MouseEvent mouseEvent) {
        lastHint=hintInfo.getText();
        hintInfo.setText("使用Prim算法求最小生成树(最好对无向连通图使用:))");
    }

    public void onKruskalEntered(MouseEvent mouseEvent) {
        lastHint=hintInfo.getText();
        hintInfo.setText("使用Kruskal算法求最小生成树(最好对无向连通图使用:))");
    }

    public void onPrimExited(MouseEvent mouseEvent) {
        hintInfo.setText(lastHint);
    }

    public void onKruskalExited(MouseEvent mouseEvent) {
        hintInfo.setText(lastHint);
    }

    public void onBackEntered(MouseEvent mouseEvent) {
        lastHint=hintInfo.getText();
        hintInfo.setText("返回主页面");
    }

    public void onBackClick(ActionEvent actionEvent) {
        main.getCurStage().close();
        try {
            main.showStage(new Stage(),"VisuAlgo","Welcome");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onBackExited(MouseEvent mouseEvent) {
        hintInfo.setText(lastHint);
    }
}