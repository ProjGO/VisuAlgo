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
    public Node root=null;
    protected AnimationManager animationManager=new AnimationManager();
    public VisuBinaryTree(AnchorPane anchorPane){
        Node.setAnchorPane(anchorPane);
    }

    public void addNode(int value,Node parent,boolean isLeftChild){
        Node newNode=new Node(value,parent,isLeftChild);
        if(isLeftChild)
            parent.leftChild=newNode;
        else
            parent.rightChild=newNode;
        if(root!=null)
            animationManager.addNewAnimation(newNode.edge.getAppearAnimation());
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newNode.visuNode));
        animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(newNode.visuNode));
    }

    public void addFirstNode(int value){
        Node newNode=new Node(value, Parameters.rootLayoutX, Parameters.rootLayoutY);
        root=newNode;
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newNode.visuNode));
        animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(newNode.visuNode));
    }

    public SequentialTransition getAllAnimation(){
        return animationManager.getAll();
    }

    protected SequentialTransition getNodeEmphaAnimation(Node node){
        return AnimationGenerator.getNodeEmphAnimation(node.visuNode);
    }

    protected SequentialTransition getEdgeEmphaAnimation(Node node,boolean isLeftEdge){
        if(isLeftEdge)
            return node.leftChild.edge.getEmphasizeAnimation();
        else
            return node.rightChild.edge.getEmphasizeAnimation();
    }

    private void innerReCalcNodeLayoutsAndGetAnima(Node curNode, Node Parent,double parenLayoutX,double parentLayoutY, boolean isLeftChild,ParallelTransition nodeMoveAnima){
        double newLayoutX=parenLayoutX+ 2* Parameters.nodeRadius*(isLeftChild?-8/Math.pow(2,Parent.depth):8/Math.pow(2,Parent.depth));
        double newLayoutY=parentLayoutY+Parameters.TreeLayerHeight;
        nodeMoveAnima.getChildren().add(AnimationGenerator.getMoveAnimation(curNode.visuNode,newLayoutX,newLayoutY));
        curNode.depth=Parent.depth+1;
        if(curNode.haveLeftChild())
            innerReCalcNodeLayoutsAndGetAnima(curNode.leftChild,curNode,newLayoutX,newLayoutY,true,nodeMoveAnima);
        if(curNode.haveRightChild())
            innerReCalcNodeLayoutsAndGetAnima(curNode.rightChild,curNode,newLayoutX,newLayoutY,false,nodeMoveAnima);
    }

    protected ParallelTransition reCalcNodeLayoutAndGetAnima(Node root,Node newParent,boolean isLeftChild){
        ParallelTransition nodeMoveAnima=new ParallelTransition();
        innerReCalcNodeLayoutsAndGetAnima(root,newParent,newParent.visuNode.getLayoutX(),newParent.visuNode.getLayoutY(),isLeftChild,nodeMoveAnima);
        //nodeMoveAnima.getChildren().add(AnimationGenerator.changeEdgeToNode(Node.anchorPane,root.edge,newParent.visuNode.getLayoutX(),newParent.visuNode.getLayoutY(),newParent.visuNode));
        return nodeMoveAnima;
    }

    public Node getRoot(){
        return root;
    }
}