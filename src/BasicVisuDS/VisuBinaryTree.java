package BasicVisuDS;

import BasicAnimation.AnimationGenerator;
import BasicAnimation.AnimationManager;
import Parameters.Parameters;
import javafx.animation.ParallelTransition;
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

    protected SequentialTransition getNodeEmphaAnimation(int nodeId){
        return AnimationGenerator.getNodeEmphAnimation(nodes[nodeId].visuNode);
    }

    protected SequentialTransition getEdgeEmphaAnimation(int nodeId,boolean isLeftEdge){
        if(isLeftEdge)
            return nodes[nodes[nodeId].leftChild].edge.getEmphasizeAnimation();
        else
            return nodes[nodes[nodeId].rightChild].edge.getEmphasizeAnimation();
    }

    private void innerReCalcNodeLayoutsAndGetAnima(Node curNode, Node Parent,double parenLayoutX,double parentLayoutY, boolean isLeftChild,ParallelTransition nodeMoveAnima){
        double newLayoutX=parenLayoutX+ 2* Parameters.nodeRadius*(isLeftChild?-8/Math.pow(2,Parent.depth):8/Math.pow(2,Parent.depth));
        double newLayoutY=parentLayoutY+Parameters.TreeLayerHeight;
        nodeMoveAnima.getChildren().add(AnimationGenerator.getMoveAnimation(curNode.visuNode,newLayoutX,newLayoutY));
        curNode.depth=Parent.depth+1;
        if(curNode.haveLeftChild())
            innerReCalcNodeLayoutsAndGetAnima(nodes[curNode.leftChild],curNode,newLayoutX,newLayoutY,true,nodeMoveAnima);
        if(curNode.haveRightChild())
            innerReCalcNodeLayoutsAndGetAnima(nodes[curNode.rightChild],curNode,newLayoutX,newLayoutY,false,nodeMoveAnima);
    }

    protected ParallelTransition reCalcNodeLayoutAndGetAnima(Node root,Node newParent,boolean isLeftChild){
        ParallelTransition nodeMoveAnima=new ParallelTransition();
        innerReCalcNodeLayoutsAndGetAnima(root,newParent,newParent.visuNode.getLayoutX(),newParent.visuNode.getLayoutY(),isLeftChild,nodeMoveAnima);
        //nodeMoveAnima.getChildren().add(AnimationGenerator.changeEdgeToNode(Node.anchorPane,root.edge,newParent.visuNode.getLayoutX(),newParent.visuNode.getLayoutY(),newParent.visuNode));
        return nodeMoveAnima;
    }
}