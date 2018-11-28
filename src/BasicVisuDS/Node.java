package BasicVisuDS;

import Parameters.Parameters;
import VisualElements.Edge.UnwUndirEdge;
import VisualElements.Node.BasicNode;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.AnchorPane;

public class Node{
    static AnchorPane anchorPane;
    public SimpleIntegerProperty value;
    public Node leftChild,rightChild,parent;
    public int depth;//最上面的节点depth为0
    public int height;//最下层是0
    public BasicNode visuNode;
    public UnwUndirEdge edge=null;//节点所属的边以自己为from，以父节点为to

    public Node(int value,Node parent,boolean isLeftChild){
        double layoutX,layoutY;
        layoutX=parent.visuNode.getLayoutX()+ 2* Parameters.nodeRadius*(isLeftChild?-8/Math.pow(2,parent.depth):8/Math.pow(2,parent.depth));
        layoutY=parent.visuNode.getLayoutY()+ Parameters.TreeLayerHeight;
        initialize(value,layoutX,layoutY);
        this.parent=parent;
        this.depth=parent.depth+1;
        edge=new UnwUndirEdge(visuNode,parent.visuNode);
        anchorPane.getChildren().addAll(edge);
    }

    public Node(int value,double layoutX,double layoutY){
        initialize(value,layoutX,layoutY);
        this.parent=null;
        this.depth=0;
    }

    private void initialize(int value,double layoutX,double layoutY){
        this.value=new SimpleIntegerProperty(value);
        leftChild=null;
        rightChild=null;
        visuNode=new BasicNode(layoutX,layoutY,value,false);
        visuNode.getDataProperty().bind(this.value);
        height=0;
        anchorPane.getChildren().add(visuNode);
    }

    public static void setAnchorPane(AnchorPane anchorPane){
        Node.anchorPane =anchorPane;
    }

    public static AnchorPane getAnchorPane(){
        return anchorPane;
    }

    public boolean haveLeftChild(){
        return leftChild!=null;
    }

    public boolean haveRightChild(){
        return rightChild!=null;
    }
}