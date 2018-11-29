package Algorithm;

import BasicAnimation.AnimationGenerator;
import BasicVisuDS.Node;
import BasicVisuDS.VisuBinaryTree;
import VisualElements.Edge.UnwUndirEdge;
import VisualElements.Node.BasicNode;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.AnchorPane;

import javax.swing.table.TableModel;

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
        if(root.parent!=null) {
            Node parent = root.parent, leftChild = root.leftChild;
            BasicNode parentVisuNode = parent.visuNode, leftChildVisuNode = leftChild.visuNode;

            Animation leftChildEdgeMove = AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), leftChild.edge, parentVisuNode.getLayoutX(), parentVisuNode.getLayoutY(), parentVisuNode);
            Animation rootEdgeMove = AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), root.edge, leftChildVisuNode.getLayoutX(), leftChildVisuNode.getLayoutY(), leftChildVisuNode);
            ParallelTransition edgeMove = new ParallelTransition(leftChildEdgeMove, rootEdgeMove);
            if (leftChild.haveRightChild())
                edgeMove.getChildren().add(AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), root.leftChild.rightChild.edge, root.visuNode.getLayoutX(), root.visuNode.getLayoutY(), root.visuNode));

            Node tmp = root.leftChild.rightChild;
            root.leftChild.parent = parent;
            if (rootIsLeftChild)
                root.parent.leftChild = root.leftChild;
            else
                root.parent.rightChild = root.leftChild;
            root.parent = leftChild;
            leftChild.rightChild = root;
            root.leftChild = tmp;
            if (tmp != null)
                tmp.parent = root;

            Animation nodeMove1;
            if (parent != null)
                nodeMove1 = reCalcNodeLayoutAndGetAnima(leftChild, parent, rootIsLeftChild);
            else
                nodeMove1 = reCalcNodeLayoutAndGetAnima(leftChild);
            Animation nodeMove2 = reCalcNodeLayoutAndGetAnima(root, leftChild, leftChild.layoutX, leftChild.layoutY, false);
            ParallelTransition nodeMove = new ParallelTransition(nodeMove1, nodeMove2);

            animationManager.addNewAnimation(new SequentialTransition(edgeMove, nodeMove));

            return leftChild;
        }else{
            Node leftChild=root.leftChild;
            if (leftChild.haveRightChild()) {
                animationManager.addNewAnimation(AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), leftChild.rightChild.edge, root.visuNode.getLayoutX(), root.visuNode.getLayoutY(), root.visuNode));
                leftChild.rightChild.parent = root;
            }
            root.leftChild = leftChild.rightChild;
            root.parent=leftChild;
            leftChild.rightChild=root;
            this.root=leftChild;
            Animation animation=reCalcNodeLayoutAndGetAnima(leftChild);
            animation.setOnFinished(e->{
                root.edge=new UnwUndirEdge(root.visuNode,leftChild.visuNode);
                Node.getAnchorPane().getChildren().add(root.edge);
                Node.getAnchorPane().getChildren().remove(leftChild.edge);
                leftChild.edge=null;
            });
            animationManager.addNewAnimation(animation);
            return leftChild;
        }
    }

    public Node RRRotation(Node root,boolean rootIsLeftChild){
        if(root.parent!=null) {
            Node parent = root.parent, rightChild = root.rightChild;
            BasicNode parentVisuNode = parent.visuNode, rightChildVisuNode = rightChild.visuNode;

            Animation rightChildEdgeMove = AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), rightChild.edge, parentVisuNode.getLayoutX(), parentVisuNode.getLayoutY(), parentVisuNode);
            Animation rootEdgeMove = AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), root.edge, rightChildVisuNode.getLayoutX(), rightChildVisuNode.getLayoutY(), rightChildVisuNode);
            ParallelTransition edgeMove = new ParallelTransition(rightChildEdgeMove, rootEdgeMove);
            if (rightChild.haveLeftChild())
                edgeMove.getChildren().add(AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), rightChild.leftChild.edge, root.visuNode.getLayoutX(), root.visuNode.getLayoutY(), root.visuNode));

            Node tmp = root.rightChild.leftChild;
            root.rightChild.parent = parent;
            if (rootIsLeftChild)
                root.parent.leftChild = root.rightChild;
            else
                root.parent.rightChild = root.rightChild;
            root.parent = rightChild;
            rightChild.leftChild = root;
            root.rightChild = tmp;
            if (tmp != null)
                tmp.parent = root;

            Animation nodeMove1 = reCalcNodeLayoutAndGetAnima(rightChild, parent, rootIsLeftChild);
            Animation nodeMove2 = reCalcNodeLayoutAndGetAnima(root, rightChild, rightChild.layoutX, rightChild.layoutY, true);
            ParallelTransition nodeMove = new ParallelTransition(nodeMove1, nodeMove2);

            animationManager.addNewAnimation(new SequentialTransition(edgeMove, nodeMove));

            return rightChild;
        }else{
            Node rightChild=root.rightChild;
            if (rightChild.haveLeftChild()) {
                animationManager.addNewAnimation(AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), rightChild.leftChild.edge, root.visuNode.getLayoutX(), root.visuNode.getLayoutY(), root.visuNode));
                rightChild.leftChild.parent = root;
            }
            root.rightChild = rightChild.leftChild;
            root.parent=rightChild;
            rightChild.leftChild=root;
            this.root=rightChild;
            Animation animation=reCalcNodeLayoutAndGetAnima(rightChild);
            animation.setOnFinished(e->{
                root.edge=new UnwUndirEdge(root.visuNode,rightChild.visuNode);
                Node.getAnchorPane().getChildren().add(root.edge);
                Node.getAnchorPane().getChildren().remove(rightChild.edge);
                rightChild.edge=null;
            });
            animationManager.addNewAnimation(animation);
            return rightChild;
        }
    }
}
