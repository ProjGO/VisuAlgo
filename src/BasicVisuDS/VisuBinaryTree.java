package BasicVisuDS;

import BasicAnimation.AnimationGenerator;
import BasicAnimation.AnimationManager;
import VisualElements.Edge.UnwUndirEdge;
import Parameters.Parameters;
import VisualElements.Node.BasicNode;
import javafx.animation.SequentialTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.AnchorPane;

//最下层32个节点，紧挨着排，节点直径20，长32*40=1280
//共六层，层间高度120，上下各留60

public class VisuBinaryTree {
    private Node[] nodes=new Node[64];
    private int nodeCnt=0;
    AnimationManager animationManager=new AnimationManager();

    public VisuBinaryTree(AnchorPane anchorPane){
        Node.setAnchorPane(anchorPane);
    }

    public void addNode(int value,int parent,boolean isLeftChild){
        Node newNode=nodeCnt==0?new Node(value, Parameters.rootLayoutX, Parameters.rootLayoutY):new Node(value,nodes[parent],isLeftChild);
        nodes[nodeCnt++]=newNode;
        if(nodeCnt>1)
            animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newNode.edge));
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newNode.visuNode));
    }

    public SequentialTransition getAllAnimation(){
        return animationManager.getAll();
    }
}

class Node{
    private static AnchorPane anchorPane;

    int id;
    SimpleIntegerProperty value;
    int leftChild,rightChild,parent;
    int depth;//最上面的节点depth为0
    int height;
    BasicNode visuNode;
    UnwUndirEdge edge=null;

    public Node(int value,Node parent,boolean isLeftChild){
        double layoutX,layoutY;
        layoutX=parent.visuNode.getLayoutX()+ 2* Parameters.nodeRadius*(isLeftChild?-8/Math.pow(2,parent.depth):8/Math.pow(2,parent.depth));
        layoutY=parent.visuNode.getLayoutY()+ Parameters.layerHeight;
        initialize(value,layoutX,layoutY);
        this.parent=parent.id;
        this.depth=parent.depth+1;
        edge=new UnwUndirEdge(parent.visuNode,visuNode);
        anchorPane.getChildren().addAll(edge);
    }

    public Node(int value,double layoutX,double layoutY){
        initialize(value,layoutX,layoutY);
        this.parent=-1;
        this.depth=0;
    }

    private void initialize(int value,double layoutX,double layoutY){
        this.value=new SimpleIntegerProperty(value);
        leftChild=0;
        rightChild=0;
        visuNode=new BasicNode(layoutX,layoutY,value,false);
        visuNode.getDataProperty().bind(this.value);
        height=0;
        anchorPane.getChildren().add(visuNode);
    }

    public static void setAnchorPane(AnchorPane anchorPane){
        Node.anchorPane =anchorPane;
    }
}
