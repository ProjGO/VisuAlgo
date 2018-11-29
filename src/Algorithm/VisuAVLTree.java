package Algorithm;

import BasicAnimation.AnimationGenerator;
import BasicVisuDS.Node;
import BasicVisuDS.VisuBinaryTree;
import VisualElements.Node.BasicNode;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.AnchorPane;

public class VisuAVLTree extends VisuBSTree {
    public VisuAVLTree(AnchorPane anchorPane){
        super(anchorPane);
    }

    private int getHeight(Node node){
        if(node==null)
            return -1;
        else
            return node.height;
    }

    public Node LLRotation(Node root,boolean rootIsLeftChild){
        Node parent=root.parent,leftChild=root.leftChild;
        BasicNode parentVisuNode=parent.visuNode,leftChildVisuNode=leftChild.visuNode;

        Animation leftChildEdgeMove= AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(),leftChild.edge,parentVisuNode.getLayoutX(),parentVisuNode.getLayoutY(),parentVisuNode);
        Animation rootEdgeMove=AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(),root.edge,leftChildVisuNode.getLayoutX(),leftChildVisuNode.getLayoutY(),leftChildVisuNode);
        ParallelTransition edgeMove=new ParallelTransition(leftChildEdgeMove,rootEdgeMove);
        if(leftChild.haveRightChild())
            animationManager.addNewAnimation(AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(),root.leftChild.rightChild.edge,root.visuNode.getLayoutX(),root.visuNode.getLayoutY(),root.visuNode));

        Node tmp=root.leftChild.rightChild;
        root.leftChild.parent=parent;
        if(rootIsLeftChild)
            root.parent.leftChild=root.leftChild;
        else
            root.parent.rightChild=root.leftChild;
        root.parent=leftChild;
        leftChild.rightChild=root;
        root.leftChild=tmp;
        if(tmp!=null)
            tmp.parent=root;

        Animation nodeMove1=reCalcNodeLayoutAndGetAnima(leftChild,parent,rootIsLeftChild);
        Animation nodeMove2=reCalcNodeLayoutAndGetAnima(root,leftChild,leftChild.layoutX,leftChild.layoutY,false);
        ParallelTransition nodeMove=new ParallelTransition(nodeMove1,nodeMove2);

        animationManager.addNewAnimation(new SequentialTransition(edgeMove,nodeMove));

        return leftChild;
    }

    public Node RRRotation(Node root,boolean rootIsLeftChild){
        Node parent=root.parent,rightChild=root.rightChild;

    }
}
