package Algorithm;

import BasicAnimation.AnimationGenerator;
import BasicAnimation.AnimationManager;
import BasicVisuDS.Node;
import BasicVisuDS.VisuBinaryTree;
import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;

import javax.xml.crypto.dsig.XMLSignature;

public class VisuBSTree extends VisuBinaryTree {

    public VisuBSTree(AnchorPane anchorPane){
        super(anchorPane);
    }

    private boolean nodeIsLeftChild(Node node){
        return node.value.get()<node.parent.value.get();
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
            }else{
                if(curNode.haveLeftChild()&&curNode.haveRightChild()){
                    int tmp=findMin(curNode.rightChild);
                    animationManager.addNewAnimation(curNode.visuNode.getTextChangeAnima(tmp));
                    value=tmp;
                    curNode=curNode.rightChild;
                    animationManager.addNewAnimation(curNode.edge.getToFromEmphaAnimation());
                    animationManager.addNewAnimation(curNode.visuNode.getEmphasizeAnimation());
                }else{
                    boolean isLeftChild=nodeIsLeftChild(curNode);
                    animationManager.addNewAnimation(AnimationGenerator.getDisappearAnimation(curNode.visuNode));
                    FadeTransition edgeDisappearAnima=AnimationGenerator.getDisappearAnimation(curNode.edge);
                    Node finalCurNode = curNode;
                    edgeDisappearAnima.setOnFinished(e->Node.getAnchorPane().getChildren().removeAll(finalCurNode.visuNode, finalCurNode.edge));
                    animationManager.addNewAnimation(edgeDisappearAnima);
                    if(curNode.haveLeftChild()){
                        if(isLeftChild)
                            curNode.parent.leftChild=curNode.leftChild;
                        else
                            curNode.parent.rightChild=curNode.leftChild;
                        reCalcNodeLayoutAndGetAnima(curNode.leftChild,curNode.parent,true);
                    }else if(curNode.haveRightChild()){
                        if(isLeftChild)
                            curNode.parent.leftChild=curNode.rightChild;
                        else
                            curNode.parent.rightChild=curNode.rightChild;
                        reCalcNodeLayoutAndGetAnima(curNode.rightChild,curNode.parent,false);
                    }
                    deleted=true;
                }
            }
        }
        return deleted;
    }
}
