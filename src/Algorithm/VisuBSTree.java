package Algorithm;

import BasicAnimation.AnimationGenerator;
import BasicVisuDS.VisuBinaryTree;
import javafx.scene.layout.AnchorPane;

public class VisuBSTree extends VisuBinaryTree {

    public VisuBSTree(AnchorPane anchorPane){
        super(anchorPane);
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

}
