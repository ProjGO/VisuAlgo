package BasicVisuDS;

import BasicAnimation.AnimationGenerator;
import BasicAnimation.AnimationManager;
import Parameters.Parameters;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.AnchorPane;

//最下层32个节点，紧挨着排，节点直径20，宽32*40=1280
//共六层，层间高度120，上下各留60,高720

public class VisuBinaryTree {
    protected Node[] nodes=new Node[64];
    protected int nodeCnt=0;
    protected final int root=0;
    protected AnimationManager animationManager=new AnimationManager();
    protected int curNode=root;
    public VisuBinaryTree(AnchorPane anchorPane){
        Node.setAnchorPane(anchorPane);
    }

    public void addNode(int value,int parent,boolean isLeftChild){
        Node newNode=new Node(value,nodes[parent],isLeftChild);
        newNode.id=nodeCnt;
        if(isLeftChild)
            nodes[parent].leftChild=newNode.id;
        else
            nodes[parent].rightChild=newNode.id;
        nodes[nodeCnt++]=newNode;
        if(nodeCnt>1)
            animationManager.addNewAnimation(newNode.edge.getAppearAnimation());
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newNode.visuNode));
        animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(newNode.visuNode));
    }

    public void addFirstNode(int value){
        Node newNode=new Node(value, Parameters.rootLayoutX, Parameters.rootLayoutY);
        nodes[nodeCnt++]=newNode;
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newNode.visuNode));
        animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(newNode.visuNode));
    }

    public SequentialTransition getAllAnimation(){
        return animationManager.getAll();
    }
}