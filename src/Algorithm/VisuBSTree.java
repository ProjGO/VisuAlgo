package Algorithm;

import BasicAnimation.AnimationGenerator;
import BasicVisuDS.TreeNode;
import BasicVisuDS.VisuBinaryTree;
import VisualElements.Edge.Edge;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.layout.AnchorPane;

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
            TreeNode curTreeNode =root;
            while(!inserted){
                animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(curTreeNode.visuNode));
                if(value< curTreeNode.value.get()){
                    if(curTreeNode.haveLeftChild()) {
                        curTreeNode = curTreeNode.leftChild;
                        animationManager.addNewAnimation(curTreeNode.edge.getToFromEmphaAnimation());
                    }else{
                        addNode(value, curTreeNode,true);
                        inserted=true;
                    }
                }else if(value> curTreeNode.value.get()){
                    if(curTreeNode.haveRightChild()) {
                        curTreeNode = curTreeNode.rightChild;
                        animationManager.addNewAnimation(curTreeNode.edge.getToFromEmphaAnimation());
                    }else{
                        addNode(value, curTreeNode,false);
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
        TreeNode curTreeNode =root;
        while(!found){
            animationManager.addNewAnimation(getNodeEmphaAnimation(curTreeNode));
            if(curTreeNode ==null){
                break;
            }if(value< curTreeNode.value.get()) {
                if(!curTreeNode.haveLeftChild())
                    break;
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curTreeNode,true));
                curTreeNode = curTreeNode.leftChild;
            }else if(value> curTreeNode.value.get()){
                if(!curTreeNode.haveRightChild())
                    break;
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curTreeNode,false));
                curTreeNode = curTreeNode.rightChild;
            }else{
                found=true;
                animationManager.addNewAnimation(getNodeEmphaAnimation(curTreeNode));
            }
        }
        return found;
    }

    public int findMax(TreeNode root){
        TreeNode curTreeNode =root;
        while(true){
            animationManager.addNewAnimation(getNodeEmphaAnimation(curTreeNode));
            if(curTreeNode.haveRightChild()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curTreeNode,false));
                curTreeNode = curTreeNode.rightChild;
            }else
                break;
        }
        return curTreeNode.value.get();
    }

    public int findMin(TreeNode root){
        TreeNode curTreeNode =root;
        while(true){
            animationManager.addNewAnimation(getNodeEmphaAnimation(curTreeNode));
            if(curTreeNode.haveLeftChild()) {
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curTreeNode, true));
                curTreeNode = curTreeNode.leftChild;
            }else
                break;
        }
        return curTreeNode.value.get();
    }

    public boolean delete(int value){
        boolean deleted=false;
        TreeNode curTreeNode =root;
        while(!deleted){
            if(curTreeNode ==null){
                break;
            }
            if(value< curTreeNode.value.get()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curTreeNode,true));
                curTreeNode = curTreeNode.leftChild;
            }else if(value> curTreeNode.value.get()){
                animationManager.addNewAnimation(getEdgeEmphaAnimation(curTreeNode,false));
                curTreeNode = curTreeNode.rightChild;
            }else{//找到了要删除的节点
                if(curTreeNode.haveLeftChild()&& curTreeNode.haveRightChild()){//如果有两个儿子则用右子树中的最小值代替当前节点的值,并在右子树中继续删除右子树中的最小值
                    int tmp=findMin(curTreeNode.rightChild);
                    animationManager.addNewAnimation(curTreeNode.visuNode.getTextChangeAnima(tmp));
                    value=tmp;
                    curTreeNode = curTreeNode.rightChild;
                    animationManager.addNewAnimation(curTreeNode.edge.getToFromEmphaAnimation());
                    animationManager.addNewAnimation(curTreeNode.visuNode.getEmphasizeAnimation());
                }else{//如果只有一个儿子或没有儿子
                    if(curTreeNode !=root) {//如果当前节点不是根节点
                        boolean isLeftChild = curTreeNode.value.get() < curTreeNode.parent.value.get();
                        animationManager.addNewAnimation(AnimationGenerator.getDisappearAnimation(curTreeNode.visuNode));
                        FadeTransition edgeDisappearAnima = AnimationGenerator.getDisappearAnimation(curTreeNode.edge);
                        TreeNode finalCurTreeNode = curTreeNode;
                        edgeDisappearAnima.setOnFinished(e -> anchorPane.getChildren().removeAll(finalCurTreeNode.visuNode, finalCurTreeNode.edge));
                        animationManager.addNewAnimation(edgeDisappearAnima);
                        if (curTreeNode.haveLeftChild()) {
                            if (isLeftChild)
                                curTreeNode.parent.leftChild = curTreeNode.leftChild;
                            else
                                curTreeNode.parent.rightChild = curTreeNode.leftChild;
                            animationManager.addNewAnimation(AnimationGenerator.changeEdgeToNode(anchorPane, curTreeNode.leftChild.edge, curTreeNode.parent.visuNode.getLayoutX(), curTreeNode.parent.visuNode.getLayoutY(), curTreeNode.parent.visuNode));
                            animationManager.addNewAnimation(reCalcNodeLayoutAndGetAnima(curTreeNode.leftChild, curTreeNode.parent, isLeftChild));
                        } else if (curTreeNode.haveRightChild()) {
                            if (isLeftChild)
                                curTreeNode.parent.leftChild = curTreeNode.rightChild;
                            else
                                curTreeNode.parent.rightChild = curTreeNode.rightChild;
                            animationManager.addNewAnimation(AnimationGenerator.changeEdgeToNode(anchorPane, curTreeNode.rightChild.edge, curTreeNode.parent.visuNode.getLayoutX(), curTreeNode.parent.visuNode.getLayoutY(), curTreeNode.parent.visuNode));
                            animationManager.addNewAnimation(reCalcNodeLayoutAndGetAnima(curTreeNode.rightChild, curTreeNode.parent, isLeftChild));
                        }else{
                            if(isLeftChild)
                                curTreeNode.parent.leftChild=null;
                            else
                                curTreeNode.parent.rightChild=null;
                        }
                    }else{//要删除根节点
                        TreeNode child,oldRoot;
                        ParallelTransition removeRootAnima;
                        if(curTreeNode.haveLeftChild()) {//如果根节点有左儿子
                            child = root.leftChild;
                            oldRoot = root;
                            Edge edge = child.edge;
                            removeRootAnima = new ParallelTransition();
                            removeRootAnima.getChildren().add(AnimationGenerator.getDisappearAnimation(root.visuNode));
                            removeRootAnima.getChildren().add(AnimationGenerator.getDisappearAnimation(root.leftChild.edge));
                            removeRootAnima.setOnFinished(e -> anchorPane.getChildren().removeAll(oldRoot.visuNode, edge));
                            animationManager.addNewAnimation(removeRootAnima);
                            child.parent = null;
                            root = child;
                            animationManager.addNewAnimation(reCalcNodeLayoutAndGetAnima(child));
                        }else if(curTreeNode.haveRightChild()){
                            child = root.rightChild;
                            oldRoot = root;
                            Edge edge = child.edge;
                            removeRootAnima = new ParallelTransition();
                            removeRootAnima.getChildren().add(AnimationGenerator.getDisappearAnimation(root.visuNode));
                            removeRootAnima.getChildren().add(AnimationGenerator.getDisappearAnimation(root.rightChild.edge));
                            removeRootAnima.setOnFinished(e -> anchorPane.getChildren().removeAll(oldRoot.visuNode, edge));
                            animationManager.addNewAnimation(removeRootAnima);
                            child.parent = null;
                            root = child;
                            animationManager.addNewAnimation(reCalcNodeLayoutAndGetAnima(child));
                        }else{
                            oldRoot=root;
                            Animation removeRoot=AnimationGenerator.getDisappearAnimation(root.visuNode);
                            removeRoot.setOnFinished(e->{
                                anchorPane.getChildren().removeAll(oldRoot.visuNode);});
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
