package BasicVisuDS;

import BasicAnimation.AnimationGenerator;
import Parameters.Parameters;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//最下层32个节点，紧挨着排，节点直径20，宽32*40=1280
//共六层，层间高度120，上下各留60,高720

public abstract class VisuBinaryTree extends VisuDS{
    protected TreeNode root=null;

    public VisuBinaryTree(AnchorPane anchorPane){
        this.anchorPane=anchorPane;
    }

    protected void addNode(int value, TreeNode parent, boolean isLeftChild){
        TreeNode newTreeNode =new TreeNode(value,parent,isLeftChild);
        anchorPane.getChildren().addAll(newTreeNode.edge,newTreeNode.visuNode);
        if(isLeftChild)
            parent.leftChild= newTreeNode;
        else
            parent.rightChild= newTreeNode;
        if(root!=null)
            animationManager.addNewAnimation(newTreeNode.edge.getAppearAnimation(false));
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newTreeNode.visuNode));
        animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(newTreeNode.visuNode));
    }

    protected void addFirstNode(int value){
        TreeNode newTreeNode =new TreeNode(value, Parameters.rootLayoutX, Parameters.rootLayoutY);
        anchorPane.getChildren().add(newTreeNode.visuNode);
        root= newTreeNode;
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newTreeNode.visuNode));
        animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(newTreeNode.visuNode));
    }

    protected SequentialTransition getNodeEmphaAnimation(TreeNode treeNode){
        return AnimationGenerator.getNodeEmphAnimation(treeNode.visuNode);
    }

    protected Animation getEdgeEmphaAnimation(TreeNode treeNode, boolean isLeftEdge){
        if(isLeftEdge)
            return treeNode.leftChild.edge.getEmphasizeAnimation();
        else
            return treeNode.rightChild.edge.getEmphasizeAnimation();
    }

    private void innerReCalcNodeLayoutsAndGetAnima(TreeNode curTreeNode, TreeNode parent, double parentLayoutX, double parentLayoutY, boolean isLeftChild, ParallelTransition nodeMoveAnima){//根据根节点位置递归计算这颗子树各节点新的位置并生成动画,中间两个layout的参数是因为在动画播放前节点位置并不会改变，而动画是在播放前一次生成完成的，所以要用参数告知节点应该去的地方
        double newLayoutX,newLayoutY;
        if(parent!=null) {
            newLayoutX = parentLayoutX + 2 * Parameters.nodeRadius * (isLeftChild ? -8 / Math.pow(2, parent.depth) : 8 / Math.pow(2, parent.depth));
            newLayoutY = parentLayoutY + Parameters.TreeLayerHeight;
            curTreeNode.depth = parent.depth + 1;
        }else{
            newLayoutX=Parameters.rootLayoutX;
            newLayoutY=Parameters.rootLayoutY;
            curTreeNode.depth=0;
        }
        curTreeNode.layoutX = newLayoutX;
        curTreeNode.layoutY = newLayoutY;
        nodeMoveAnima.getChildren().add(AnimationGenerator.getMoveAnimation(curTreeNode.visuNode, newLayoutX, newLayoutY));

        if(curTreeNode.haveLeftChild())
            innerReCalcNodeLayoutsAndGetAnima(curTreeNode.leftChild, curTreeNode,newLayoutX,newLayoutY,true,nodeMoveAnima);
        if(curTreeNode.haveRightChild())
            innerReCalcNodeLayoutsAndGetAnima(curTreeNode.rightChild, curTreeNode,newLayoutX,newLayoutY,false,nodeMoveAnima);
    }

    protected ParallelTransition reCalcNodeLayoutAndGetAnima(TreeNode root, TreeNode newParent, boolean isLeftChild){
        ParallelTransition nodeMoveAnima=new ParallelTransition();
        innerReCalcNodeLayoutsAndGetAnima(root,newParent,newParent.visuNode.getLayoutX(),newParent.visuNode.getLayoutY(),isLeftChild,nodeMoveAnima);
        //nodeMoveAnima.getChildren().add(AnimationGenerator.changeEdgeToNode(TreeNode.anchorPane,root.visuEdge,newParent.visuNode.getLayoutX(),newParent.visuNode.getLayoutY(),newParent.visuNode));
        return nodeMoveAnima;
    }

    protected ParallelTransition reCalcNodeLayoutAndGetAnima(TreeNode root, TreeNode newParent, double newParentX, double newParentY, boolean isLeftChild){
        ParallelTransition nodeMoveAnima=new ParallelTransition();
        innerReCalcNodeLayoutsAndGetAnima(root,newParent,newParentX,newParentY,isLeftChild,nodeMoveAnima);
        return nodeMoveAnima;
    }

    protected ParallelTransition reCalcNodeLayoutAndGetAnima(TreeNode root){
        ParallelTransition nodeMoveAnima=new ParallelTransition();
        innerReCalcNodeLayoutsAndGetAnima(root,null,Parameters.rootLayoutX,Parameters.rootLayoutY,false,nodeMoveAnima);//中间三个参数在这都没用
        return nodeMoveAnima;
    }

    private ArrayList<TreeNode> getAllTreeNode(){
        ArrayList<TreeNode> nodes=new ArrayList<>();
        Queue<TreeNode> q=new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()){
            TreeNode curNode=q.poll();
            nodes.add(curNode);
            if(curNode.haveLeftChild())
                q.offer(curNode.leftChild);
            if(curNode.haveRightChild())
                q.offer(curNode.rightChild);
        }
        return nodes;
    }

    public void deleteAllNodesAndGetAnima(){
        ArrayList<TreeNode> nodes=getAllTreeNode();
        ParallelTransition disappearAnimation=new ParallelTransition();
        for(TreeNode node:nodes){
            disappearAnimation.getChildren().add(node.visuNode.getDisappearAnimation());
            if(node.edge!=null)
                disappearAnimation.getChildren().add(node.edge.getDisappearAnimation());
        }
        animationManager.addNewAnimation(disappearAnimation);
        root=null;
    }

    @Override
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane=anchorPane;
    }

    public abstract boolean insert(int value);

    public abstract  boolean delete(int value);

    public abstract boolean find(int value);

    public abstract int findMax(TreeNode root);

    public abstract int findMin(TreeNode root);

    public int findMax(){
        return findMax(root);
    }

    public int findMin(){
        return findMin(root);
    }
}