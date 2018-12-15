package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeController extends BasicController {
    @FXML
    Text intructionText;

    public void onBinaryTreeClicked(ActionEvent actionEvent) {
        main.getCurStage().close();
        try {
            main.showStage(new Stage(),"BinaryTree","Tree");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onGraphClicked(ActionEvent actionEvent) {
        main.getCurStage().close();
        try {
            main.showStage(new Stage(),"Graph","Graph");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onBinTreeEntered(MouseEvent mouseEvent) {
        intructionText.setText("包括了二叉搜索树、自平衡二叉树的增删查改等操作的可视化");
    }

    public void onBinTreeExited(MouseEvent mouseEvent) {
        intructionText.setText("");
    }

    public void onGraphEntered(MouseEvent mouseEvent) {
        intructionText.setText("包括了图的搜索、最短路、最小生成树的算法的可视化");
    }

    public void onGraphExited(MouseEvent mouseEvent) {
        intructionText.setText("");
    }
}
