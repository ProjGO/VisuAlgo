package UI;

import Algorithm.BinaryTree.VisuAVLTree;
import Algorithm.BinaryTree.VisuBSTree;
import BasicVisuDS.VisuBinaryTree;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class TreeController extends BasicController implements Initializable {
    private VisuBinaryTree visuBinaryTree;
    private String lastHint;

    @FXML
    Button insertButton,findButton,deleteButton,AVLButton,BSTButton,deleteAllButton;
    @FXML
    TextField dataInputField;
    @FXML
    Text hintInfo,treeTypeText;
    @FXML
    AnchorPane treePane;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        visuBinaryTree =new VisuBSTree(treePane);
        hintInfo.setText("请输入数据(0-100的数字)后点击\"插入\"按钮插入新节点(若不输入将随机产生数据)");
        hintInfo.setFont(new Font(15));
        treeTypeText.setText("BST(二叉搜索树)");
    }

    public void onInsertClick(ActionEvent actionEvent) {
        visuBinaryTree.clearAllAnimation();
        String input= dataInputField.getText();
        int state=Utilities.judgeInput(input);
        if(state==0||state==-1) {
            hintInfo.setText("正在插入新节点");
            dataInputField.setDisable(true);
            insertButton.setDisable(true);
            if(state==-1)
                dataInputField.setText(Integer.toString(new Random().nextInt()%50+50));
            int value=Integer.parseInt(dataInputField.getText());
            boolean inserted=visuBinaryTree.insert(value);
            Animation insertAnimation=visuBinaryTree.getAllAnimation();
            String hintInfoText=inserted?"完毕":"完毕\n"+value+"已经存在";
            insertAnimation.setOnFinished(e->{
                hintInfo.setText(hintInfoText);
                dataInputField.setDisable(false);
                insertButton.setDisable(false);
                dataInputField.setText("");
            });
            insertAnimation.play();
        }else if(state==1){
            hintInfo.setText("请输入数字");
        }else if(state==2){
            hintInfo.setText("请输入0-100间的数字");
        }
    }

    public void onFindClick(ActionEvent actionEvent) {
        visuBinaryTree.clearAllAnimation();
        String input= dataInputField.getText();
        int state=Utilities.judgeInput(input);
        if(state==0) {
            hintInfo.setText("正在查找节点");
            dataInputField.setDisable(true);
            insertButton.setDisable(true);
            int value=Integer.parseInt(dataInputField.getText());
            boolean inserted=visuBinaryTree.find(value);
            Animation insertAnimation=visuBinaryTree.getAllAnimation();
            String hintInfoText=inserted?"节点"+value+"已找到":"未找到"+value;
            insertAnimation.setOnFinished(e->{
                hintInfo.setText(hintInfoText);
                dataInputField.setDisable(false);
                insertButton.setDisable(false);
                dataInputField.setText("");
            });
            insertAnimation.play();
        }else if(state==1||state==-1){
            hintInfo.setText("请输入数字");
        }else if(state==2){
            hintInfo.setText("请输入0-100间的数字");
        }
    }

    public void onDeleteClick(ActionEvent actionEvent) {
        visuBinaryTree.clearAllAnimation();
        String input= dataInputField.getText();
        int state=Utilities.judgeInput(input);
        if(state==0) {
            hintInfo.setText("正在删除节点");
            dataInputField.setDisable(true);
            insertButton.setDisable(true);
            int value=Integer.parseInt(dataInputField.getText());
            boolean inserted=visuBinaryTree.delete(value);
            Animation insertAnimation=visuBinaryTree.getAllAnimation();
            String hintInfoText=inserted?"节点"+value+"已删除":"未找到"+value;
            insertAnimation.setOnFinished(e->{
                hintInfo.setText(hintInfoText);
                dataInputField.setDisable(false);
                insertButton.setDisable(false);
                dataInputField.setText("");
            });
            insertAnimation.play();
        }else if(state==1||state==-1){
            hintInfo.setText("请输入数字");
        }else if(state==2){
            hintInfo.setText("请输入0-100间的数字");
        }
    }

    public void onDeleteAllClick(ActionEvent actionEvent) {
        visuBinaryTree.clearAllAnimation();
        visuBinaryTree.deleteAllNodesAndGetAnima();
        visuBinaryTree.getAllAnimation().play();
    }

    public void onBSTButtonClick(ActionEvent actionEvent) {
        if(visuBinaryTree instanceof  VisuAVLTree) {
            visuBinaryTree.clearAllAnimation();
            visuBinaryTree.deleteAllNodesAndGetAnima();
            Animation delAllAnimation=visuBinaryTree.getAllAnimation();
            hintInfo.setText("请输入数据(0-100的数字)后点击\"插入\"按钮插入新节点(若不输入将随机产生数据)");
            treeTypeText.setText("BST(二叉搜索树)");
            delAllAnimation.setOnFinished(e->visuBinaryTree=new VisuBSTree(treePane));
            delAllAnimation.play();
        }
    }

    public void onAVLButtonClick(ActionEvent actionEvent) {
        if(visuBinaryTree instanceof  VisuBSTree) {
            visuBinaryTree.clearAllAnimation();
            visuBinaryTree.deleteAllNodesAndGetAnima();
            Animation delAllAnimation=visuBinaryTree.getAllAnimation();
            hintInfo.setText("请输入数据(0-100的数字)后点击\"插入\"按钮插入新节点(若不输入将随机产生数据)");
            treeTypeText.setText("AVL(自平衡二叉树)");
            delAllAnimation.setOnFinished(e->visuBinaryTree=new VisuAVLTree(treePane));
            delAllAnimation.play();
        }
    }

    public void onFindMinClick(ActionEvent actionEvent) {
        hintInfo.setText("正在查找最小值");
        visuBinaryTree.clearAllAnimation();
        int minValue=visuBinaryTree.findMin();
        Animation findMinAnima=visuBinaryTree.getAllAnimation();
        findMinAnima.setOnFinished(e->hintInfo.setText("最小值:"+minValue));
        findMinAnima.play();
    }

    public void onFindMaxClick(ActionEvent actionEvent) {
        hintInfo.setText("正在查找最大值");
        visuBinaryTree.clearAllAnimation();
        int maxValue=visuBinaryTree.findMax();
        Animation findMinAnima=visuBinaryTree.getAllAnimation();
        findMinAnima.setOnFinished(e->hintInfo.setText("最大值:"+maxValue));
        findMinAnima.play();
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

    public void onBSTEntered(MouseEvent mouseEvent) {
        lastHint=hintInfo.getText();
        hintInfo.setText("切换至二叉搜索树");
    }

    public void onBSTExited(MouseEvent mouseEvent) {
        hintInfo.setText(lastHint);
    }

    public void onAVLEntered(MouseEvent mouseEvent) {
        lastHint=hintInfo.getText();
        hintInfo.setText("切换至自平衡二叉树");
    }

    public void onAVLExited(MouseEvent mouseEvent) {
        hintInfo.setText(lastHint);
    }
}
