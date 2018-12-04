package Algorithm;

import BasicAnimation.AnimationGenerator;
import BasicAnimation.AnimationManager;
import BasicVisuDS.Node;
import BasicVisuDS.VisuBinaryTree;
import VisualElements.Edge.Edge;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.layout.AnchorPane;

import javax.xml.crypto.dsig.XMLSignature;

public class VisuBSTree extends VisuBinaryTree {

    public VisuBSTree(AnchorPane anchorPane){
        super(anchorPane);
    }

    public boolean insert(int value){
        boolean inserted=false;
        if(root==null) {
            addFirstNode(value);
            inserted=true;
        }
        else{
            Node curNode=root;
            while(!inserted){
                animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(curNode.visuNode));
                if(value<curNode.value.get()){
                    if(curNode.haveLeftChild()) {
                        curNode = curNode.leftChild;
                        animationManager.addNewAnimation(curNode.edge.getToFromEmphaAnimation());
                    }else{
                        addNode(value,curNode,true);
                        inserted=true;
                    }
                }else if(value>curNode.value.get()){
                    if(curNode.haveRightChild()) {
                        curNode = curNode.rightChild;
                        animationManager.addNewAnimation(curNode.edge.getToFromEmphaAnimation());
                    }else{
                        addNode(value,curNode,false);
                        inserted=true;
                    }
                }else{
                    break;
                }
            }
        }
        return inserted;
    }

    public boolean find(int value){
        boolean found=false;
        Node curNode=root;
        while(!found){
            animationManager.addNewAnimation(getNodeEmphaAnimation(curNode));
            if(curNode==null){
                found=false;
                break;
            }if(value<curNode.value.get()) {
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,true));
                curNode = curNode.leftChild;
            }else if(value>curNode.value.get()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,false));
                curNode=curNode.rightChild;
            }else{
                found=true;
                animationManager.addNewAnimation(getNodeEmphaAnimation(curNode));
            }
        }
        return found;
    }

    public int findMax(Node root){
        Node curNode=root;
        while(true){
            animationManager.addNewAnimation(getNodeEmphaAnimation(curNode));
            if(curNode.haveRightChild()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,false));
                curNode=curNode.rightChild;
            }else
                break;
        }
        return curNode.value.get();
    }

    public int findMin(Node root){
        Node curNode=root;
        while(true){
            animationManager.addNewAnimation(getNodeEmphaAnimation(curNode));
            if(curNode.haveLeftChild()) {
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode, true));
                curNode = curNode.leftChild;
            }else
                break;
        }
        return curNode.value.get();
    }

    public boolean delete(int value){
        boolean deleted=false;
        Node curNode=root;
        while(!deleted){
            if(curNode==null){
                break;
            }
            if(value<curNode.value.get()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,true));
                curNode = curNode.leftChild;
            }else if(value>curNode.value.get()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,false));
                curNode=curNode.rightChild;
            }else{//找到了要删除的节点
                if(curNode.haveLeftChild()&&curNode.haveRightChild()){//如果有两个儿子则用右子树中的最小值代替当前节点的值,并在右子树中继续删除右子树中的最小值
                    int tmp=findMin(curNode.rightChild);
                    animationManager.addNewAnimation(curNode.visuNode.getTextChangeAnima(tmp));
                    value=tmp;
                    curNode=curNode.rightChild;
                    animationManager.addNewAnimation(curNode.edge.getToFromEmphaAnimation());
                    animationManager.addNewAnimation(curNode.visuNode.getEmphasizeAnimation());
                }else{//如果只有一个儿子或没有儿子
                    if(curNode!=root) {//如果当前节点不是根节点
                        boolean isLeftChild = curNode.value.get() < curNode.parent.value.get();
                        animationManager.addNewAnimation(AnimationGenerator.getDisappearAnimation(curNode.visuNode));
                        FadeTransition edgeDisappearAnima = AnimationGenerator.getDisappearAnimation(curNode.edge);
                        Node finalCurNode = curNode;
                        edgeDisappearAnima.setOnFinished(e -> Node.getAnchorPane().getChildren().removeAll(finalCurNode.visuNode, finalCurNode.edge));
                        animationManager.addNewAnimation(edgeDisappearAnima);
                        if (curNode.haveLeftChild()) {
                            if (isLeftChild)
                                curNode.parent.leftChild = curNode.leftChild;
                            else
                                curNode.parent.rightChild = curNode.leftChild;
                            animationManager.addNewAnimation(AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), curNode.leftChild.edge, curNode.parent.visuNode.getLayoutX(), curNode.parent.visuNode.getLayoutY(), curNode.parent.visuNode));
                            animationManager.addNewAnimation(reCalcNodeLayoutAndGetAnima(curNode.leftChild, curNode.parent, isLeftChild));
                        } else if (curNode.haveRightChild()) {
                            if (isLeftChild)
                                curNode.parent.leftChild = curNode.rightChild;
                            else
                                curNode.parent.rightChild = curNode.rightChild;
                            animationManager.addNewAnimation(AnimationGenerator.changeEdgeToNode(Node.getAnchorPane(), curNode.rightChild.edge, curNode.parent.visuNode.getLayoutX(), curNode.parent.visuNode.getLayoutY(), curNode.parent.visuNode));
                            animationManager.addNewAnimation(reCalcNodeLayoutAndGetAnima(curNode.rightChild, curNode.parent, isLeftChild));
                        }else{
                            if(isLeftChild)
                                curNode.parent.leftChild=null;
                            else
                                curNode.parent.rightChild=null;
                        }
                    }else{//要删除根节点
                        Node child,oldRoot;
                        ParallelTransition removeRootAnima;
                        if(curNode.haveLeftChild()) {//如果根节点有左儿子
                            child = root.leftChild;
                            oldRoot = root;
                            Edge edge = child.edge;
                            removeRootAnima = new ParallelTransition();
                            removeRootAnima.getChildren().add(AnimationGenerator.getDisappearAnimation(root.visuNode));
                            removeRootAnima.getChildren().add(AnimationGenerator.getDisappearAnimation(root.leftChild.edge));
                            removeRootAnima.setOnFinished(e ->Node.getAnchorPane().getChildren().removeAll(oldRoot.visuNode, edge));
                            animationManager.addNewAnimation(removeRootAnima);
                            child.parent = null;
                            root = child;
                            animationManager.addNewAnimation(reCalcNodeLayoutAndGetAnima(child));
                        }else if(curNode.haveRightChild()){
                            child = root.rightChild;
                            oldRoot = root;
                            Edge edge = child.edge;
                            removeRootAnima = new ParallelTransition();
                            removeRootAnima.getChildren().add(AnimationGenerator.getDisappearAnimation(root.visuNode));
                            removeRootAnima.getChildren().add(AnimationGenerator.getDisappearAnimation(root.rightChild.edge));
                            removeRootAnima.setOnFinished(e ->Node.getAnchorPane().getChildren().removeAll(oldRoot.visuNode, edge));
                            animationManager.addNewAnimation(removeRootAnima);
                            child.parent = null;
                            root = child;
                            animationManager.addNewAnimation(reCalcNodeLayoutAndGetAnima(child));
                        }else{
                            oldRoot=root;
                            Animation removeRoot=AnimationGenerator.getDisappearAnimation(root.visuNode);
                            removeRoot.setOnFinished(e->{Node.getAnchorPane().getChildren().removeAll(oldRoot.visuNode);});
                            animationManager.addNewAnimation(removeRoot);
                            root=null;
                        }
                    }
                    deleted=true;
                }
            }
        }
        return deleted;
    }
}
