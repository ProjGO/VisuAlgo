package BasicVisuDS;

import BasicAnimation.AnimationGenerator;
import BasicAnimation.AnimationManager;
import Parameters.Parameters;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.AnchorPane;

//最下层32个节点，紧挨着排，节点直径20，宽32*40=1280
//共六层，层间高度120，上下各留60,高720

public class VisuBinaryTree extends VisuDS{
    public TreeNode root=null;

    public VisuBinaryTree(AnchorPane anchorPane){
        TreeNode.setAnchorPane(anchorPane);
    }

    public void addNode(int value, TreeNode parent, boolean isLeftChild){
        TreeNode newTreeNode =new TreeNode(value,parent,isLeftChild);
        if(isLeftChild)
            parent.leftChild= newTreeNode;
        else
            parent.rightChild= newTreeNode;
        if(root!=null)
            animationManager.addNewAnimation(newTreeNode.edge.getAppearAnimation(false));
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newTreeNode.visuNode));
        animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(newTreeNode.visuNode));
    }

    public void addFirstNode(int value){
        TreeNode newTreeNode =new TreeNode(value, Parameters.rootLayoutX, Parameters.rootLayoutY);
        root= newTreeNode;
        animationManager.addNewAnimation(AnimationGenerator.getAppearAnimation(newTreeNode.visuNode));
        animationManager.addNewAnimation(AnimationGenerator.getNodeEmphAnimation(newTreeNode.visuNode));
    }

    protected SequentialTransition getNodeEmphaAnimation(TreeNode treeNode){
        return AnimationGenerator.getNodeEmphAnimation(treeNode.visuNode);
    }

    protected SequentialTransition getEdgeEmphaAnimation(TreeNode treeNode, boolean isLeftEdge){
        if(isLeftEdge)
            return treeNode.leftChild.edge.getEmphasizeAnimation();
        else
            return treeNode.rightChild.edge.getEmphasizeAnimation();
    }

    private void innerReCalcNodeLayoutsAndGetAnima(TreeNode curTreeNode, TreeNode parent, double parentLayoutX, double parentLayoutY, boolean isLeftChild, ParallelTransition nodeMoveAnima){//根据根节点位置递归计算这颗子树各节点新的位置并生成动画
        //中间两个layout的参数是因为在动画播放前节点位置并不会改变，而动画是在播放前一次生成完成的，所以要用参数告知节点应该去的地方
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
        //nodeMoveAnima.getChildren().add(AnimationGenerator.changeEdgeToNode(TreeNode.anchorPane,root.edge,newParent.visuNode.getLayoutX(),newParent.visuNode.getLayoutY(),newParent.visuNode));
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

    @Override
    public void setAnchorPane(AnchorPane anchorPane) {
        TreeNode.setAnchorPane(anchorPane);
    }
}