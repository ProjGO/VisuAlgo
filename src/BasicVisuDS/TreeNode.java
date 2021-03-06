package BasicVisuDS;

import Parameters.Parameters;
import VisualElements.Edge.UnwUndirEdge;
import VisualElements.Node.BasicVisuNode;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.AnchorPane;

public class TreeNode {
    public SimpleIntegerProperty value;
    public TreeNode leftChild,rightChild,parent;
    int depth;//最上面的节点depth为0
    public int height;//最下层是0
    public BasicVisuNode visuNode;
    public double layoutX,layoutY;
    public UnwUndirEdge edge=null;//节点所属的边以自己为from，以父节点为to
    private boolean isRoot;

    TreeNode(int value, TreeNode parent, boolean isLeftChild){
        double layoutX,layoutY;
        layoutX=parent.visuNode.getLayoutX()+ 2* Parameters.nodeRadius*(isLeftChild?-8/Math.pow(2,parent.depth):8/Math.pow(2,parent.depth));
        layoutY=parent.visuNode.getLayoutY()+ Parameters.TreeLayerHeight;
        initialize(value,layoutX,layoutY);
        this.parent=parent;
        this.depth=parent.depth+1;
        edge=new UnwUndirEdge(visuNode,parent.visuNode);
    }

    TreeNode(int value, double layoutX, double layoutY){
        initialize(value,layoutX,layoutY);
        this.parent=null;
        this.depth=0;
        isRoot=true;
    }

    private void initialize(int value,double layoutX,double layoutY){
        this.value=new SimpleIntegerProperty(value);
        leftChild=null;
        rightChild=null;
        visuNode=new BasicVisuNode(layoutX,layoutY,value,false);
        this.layoutX=layoutX;
        this.layoutY=layoutY;
        visuNode.getDataProperty().bindBidirectional(this.value);
        height=0;
        isRoot=false;
    }

    public boolean haveLeftChild(){
        return leftChild!=null;
    }

    public boolean haveRightChild(){
        return rightChild!=null;
    }
}