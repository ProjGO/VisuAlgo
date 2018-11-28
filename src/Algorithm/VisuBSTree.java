package Algorithm;

import BasicAnimation.AnimationGenerator;
import BasicAnimation.AnimationManager;
import BasicVisuDS.Node;
import BasicVisuDS.VisuBinaryTree;
import javafx.scene.layout.AnchorPane;

import javax.xml.crypto.dsig.XMLSignature;

public class VisuBSTree extends VisuBinaryTree {

    public VisuBSTree(AnchorPane anchorPane){
        super(anchorPane);
    }

    private boolean nodeIsLeftChild(Node node){
        return node.value.get()<nodes[node.parent].value.get();
    }

    public boolean insert(int value){
        boolean inserted=false;
        if(nodeCnt==0) {
            addFirstNode(value);
            inserted=true;
        }
        else{
            curNode=root;
            while(!inserted){
                animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(nodes[curNode].visuNode));
                if(value<nodes[curNode].value.get()){
                    if(nodes[curNode].haveLeftChild()) {
                        curNode = nodes[curNode].leftChild;
                        animationManager.addNewAnimation(nodes[curNode].edge.getToFromEmphaAnimation());
                    }else{
                        addNode(value,curNode,true);
                        inserted=true;
                    }
                }else if(value>nodes[curNode].value.get()){
                    if(nodes[curNode].haveRightChild()) {
                        curNode = nodes[curNode].rightChild;
                        animationManager.addNewAnimation(nodes[curNode].edge.getToFromEmphaAnimation());
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
        curNode=0;
        while(!found){
            animationManager.addNewAnimation(getNodeEmphaAnimation(curNode));
            if(curNode==-1){
                found=false;
                break;
            }if(value<nodes[curNode].value.get()) {
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,true));
                curNode = nodes[curNode].leftChild;
            }else if(value>nodes[curNode].value.get()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,false));
                curNode=nodes[curNode].rightChild;
            }else{
                found=true;
                animationManager.addNewAnimation(getNodeEmphaAnimation(curNode));
            }
        }
        return found;
    }

    public int findMax(int root){
        curNode=root;
        while(true){
            animationManager.addNewAnimation(getNodeEmphaAnimation(curNode));
            if(nodes[curNode].haveRightChild()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,false));
                curNode=nodes[curNode].rightChild;
            }else
                break;
        }
        return nodes[curNode].value.get();
    }

    public int findMin(int root){
        curNode=root;
        while(true){
            animationManager.addNewAnimation(getNodeEmphaAnimation(curNode));
            if(nodes[curNode].haveLeftChild()) {
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode, true));
                curNode = nodes[curNode].leftChild;
            }else
                break;
        }
        return nodes[curNode].value.get();
    }

    public boolean delete(int value){
        boolean deleted=false;
        curNode=0;
        while(!deleted){
            if(curNode==-1){
                break;
            }
            if(value<nodes[curNode].value.get()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,true));
                curNode = nodes[curNode].leftChild;
            }else if(value>nodes[curNode].value.get()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curNode,false));
                curNode=nodes[curNode].rightChild;
            }else{
                if(nodes[curNode].haveLeftChild()&&nodes[curNode].haveRightChild()){
                    int tmp=findMin(nodes[curNode].rightChild);
                    nodes[curNode].value.setValue(tmp);
                    value=tmp;
                    curNode=nodes[curNode].rightChild;
                    animationManager.addNewAnimation(nodes[curNode].edge.getToFromEmphaAnimation());
                    animationManager.addNewAnimation(nodes[curNode].visuNode.getEmphasizeAnimation());
                }else{
                    boolean isLeftChild=nodeIsLeftChild(nodes[curNode]);
                    animationManager.addNewAnimation(AnimationGenerator.getDisappearAnimation(nodes[curNode].visuNode));
                    animationManager.addNewAnimation(AnimationGenerator.getDisappearAnimation(nodes[curNode].edge));
                    if(nodes[curNode].haveLeftChild()){
                        if(isLeftChild)
                            nodes[nodes[curNode].parent].leftChild=nodes[curNode].leftChild;
                        else
                            nodes[nodes[curNode].parent].rightChild=nodes[curNode].leftChild;
                        reCalcNodeLayoutAndGetAnima(nodes[nodes[curNode].leftChild],nodes[nodes[curNode].parent],true);
                    }else if(nodes[curNode].haveRightChild()){
                        if(isLeftChild)
                            nodes[nodes[curNode].parent].leftChild=nodes[curNode].rightChild;
                        else
                            nodes[nodes[curNode].parent].rightChild=nodes[curNode].rightChild;
                        reCalcNodeLayoutAndGetAnima(nodes[nodes[curNode].rightChild],nodes[nodes[curNode].parent],false);
                    }
                    Node.getAnchorPane().getChildren().removeAll(nodes[curNode].visuNode,nodes[curNode].edge);
                    deleted=true;
                }
            }
        }
        return deleted;
    }
}
