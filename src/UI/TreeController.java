package UI;

import Algorithm.VisuAVLTree;
import BasicVisuDS.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TreeController implements Initializable {
    private Main main;
    private VisuAVLTree visuAVLTree;

    public void setMain(Main main){
        this.main=main;
    }

    @FXML
    Button insertButton,findButton,deleteButton;

    @FXML
    TextField textField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        visuAVLTree=new VisuAVLTree(null);
    }

    public void setAnchoPane(AnchorPane anchorPane){
        Node.setAnchorPane(anchorPane);
    }

    public void onInsertClick(ActionEvent actionEvent) {
        visuAVLTree.clearAllAnimation();
        visuAVLTree.insert(Integer.parseInt(textField.getText()));
        visuAVLTree.getAllAnimation().play();
    }

    public void onFindClick(ActionEvent actionEvent) {
    }

    public void onDeleteClick(ActionEvent actionEvent) {
    }
}
