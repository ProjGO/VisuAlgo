package Algorithm;

import BasicAnimation.AnimationGenerator;
import BasicVisuDS.TreeNode;
import VisualElements.Edge.Edge;
import VisualElements.Edge.UnwUndirEdge;
import VisualElements.Node.BasicVisuNode;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.AnchorPane;

public class VisuAVLTree extends VisuBSTree {
    public VisuAVLTree(AnchorPane anchorPane){
        super(anchorPane);
    }

    private int getHeight(TreeNode treeNode){
        if(treeNode ==null)
            return -1;
        else
            return treeNode.height;
    }

    private TreeNode LLRotation(TreeNode root, boolean rootIsLeftChild){
        if(root.parent!=null) {
            TreeNode parent = root.parent, leftChild = root.leftChild;
            BasicVisuNode parentVisuNode = parent.visuNode, leftChildVisuNode = leftChild.visuNode;

            Animation leftChildEdgeMove = AnimationGenerator.changeEdgeToNode(anchorPane, leftChild.edge, parentVisuNode.getLayoutX(), parentVisuNode.getLayoutY(), parentVisuNode);
            Animation rootEdgeMove = AnimationGenerator.changeEdgeToNode(anchorPane, root.edge, leftChildVisuNode.getLayoutX(), leftChildVisuNode.getLayoutY(), leftChildVisuNode);
            ParallelTransition edgeMove = new ParallelTransition(leftChildEdgeMove, rootEdgeMove);
            if (leftChild.haveRightChild())
                edgeMove.getChildren().add(AnimationGenerator.changeEdgeToNode(anchorPane, root.leftChild.rightChild.edge, root.visuNode.getLayoutX(), root.visuNode.getLayoutY(), root.visuNode));

            TreeNode tmp = root.leftChild.rightChild;
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
            nodeMove1 = reCalcNodeLayoutAndGetAnima(leftChild, parent, rootIsLeftChild);
            Animation nodeMove2 = reCalcNodeLayoutAndGetAnima(root, leftChild, leftChild.layoutX, leftChild.layoutY, false);
            ParallelTransition nodeMove = new ParallelTransition(nodeMove1, nodeMove2);

            animationManager.addNewAnimation(new SequentialTransition(edgeMove, nodeMove));
            root.height=Math.max(getHeight(root.leftChild),getHeight(root.rightChild))+1;
            leftChild.height=Math.max(getHeight(leftChild.leftChild),getHeight(leftChild.rightChild))+1;
            return leftChild;
        }else{
            TreeNode leftChild=root.leftChild;
            if (leftChild.haveRightChild()) {
                animationManager.addNewAnimation(AnimationGenerator.changeEdgeToNode(anchorPane, leftChild.rightChild.edge, root.visuNode.getLayoutX(), root.visuNode.getLayoutY(), root.visuNode));
                leftChild.rightChild.parent = root;
            }
            root.leftChild = leftChild.rightChild;
            root.parent=leftChild;
            leftChild.rightChild=root;
            leftChild.parent=null;
            this.root=leftChild;
            Animation animation=reCalcNodeLayoutAndGetAnima(leftChild);
            animation.setOnFinished(e->{
                root.edge=new UnwUndirEdge(root.visuNode,leftChild.visuNode);
                anchorPane.getChildren().add(root.edge);
                anchorPane.getChildren().remove(leftChild.edge);
                leftChild.edge=null;
            });
            animationManager.addNewAnimation(animation);
            root.height=Math.max(getHeight(root.leftChild),getHeight(root.rightChild))+1;
            leftChild.height=Math.max(getHeight(leftChild.leftChild),getHeight(leftChild.rightChild))+1;
            return leftChild;
        }
    }//LL和RR旋转中会直接生成动画并加入到animationManager中,并且考虑了对根节点的特殊处理

    private TreeNode RRRotation(TreeNode root, boolean rootIsLeftChild){
        if(root.parent!=null) {
            TreeNode parent = root.parent, rightChild = root.rightChild;
            BasicVisuNode parentVisuNode = parent.visuNode, rightChildVisuNode = rightChild.visuNode;

            Animation rightChildEdgeMove = AnimationGenerator.changeEdgeToNode(anchorPane, rightChild.edge, parentVisuNode.getLayoutX(), parentVisuNode.getLayoutY(), parentVisuNode);
            Animation rootEdgeMove = AnimationGenerator.changeEdgeToNode(anchorPane, root.edge, rightChildVisuNode.getLayoutX(), rightChildVisuNode.getLayoutY(), rightChildVisuNode);
            ParallelTransition edgeMove = new ParallelTransition(rightChildEdgeMove, rootEdgeMove);
            if (rightChild.haveLeftChild())
                edgeMove.getChildren().add(AnimationGenerator.changeEdgeToNode(anchorPane, rightChild.leftChild.edge, root.visuNode.getLayoutX(), root.visuNode.getLayoutY(), root.visuNode));

            TreeNode tmp = root.rightChild.leftChild;
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

            root.height=Math.max(getHeight(root.leftChild),getHeight(root.rightChild))+1;
            rightChild.height=Math.max(getHeight(rightChild.leftChild),getHeight(rightChild.rightChild))+1;

            return rightChild;
        }else{
            TreeNode rightChild=root.rightChild;
            if (rightChild.haveLeftChild()) {
                animationManager.addNewAnimation(AnimationGenerator.changeEdgeToNode(anchorPane, rightChild.leftChild.edge, root.visuNode.getLayoutX(), root.visuNode.getLayoutY(), root.visuNode));
                rightChild.leftChild.parent = root;
            }
            root.rightChild = rightChild.leftChild;
            root.parent=rightChild;
            rightChild.leftChild=root;
            rightChild.parent=null;
            this.root=rightChild;
            Animation animation=reCalcNodeLayoutAndGetAnima(rightChild);
            animation.setOnFinished(e->{
                root.edge=new UnwUndirEdge(root.visuNode,rightChild.visuNode);
                anchorPane.getChildren().add(root.edge);
                anchorPane.getChildren().remove(rightChild.edge);
                rightChild.edge=null;
            });
            animationManager.addNewAnimation(animation);

            root.height=Math.max(getHeight(root.leftChild),getHeight(root.rightChild))+1;
            rightChild.height=Math.max(getHeight(rightChild.leftChild),getHeight(rightChild.rightChild))+1;

            return rightChild;
        }
    }

    private TreeNode LRRotation(TreeNode root, boolean rootIsLeftChild){
        root.leftChild=RRRotation(root.leftChild,true);
        return LLRotation(root,rootIsLeftChild);
    }

    private TreeNode RLRotation(TreeNode root, boolean rootIsLeftChild){
        root.rightChild=LLRotation(root.rightChild,false);
        return RRRotation(root,rootIsLeftChild);
    }

    private TreeNode innerInsert(TreeNode curTreeNode, int value, boolean isLeftChild){
        if(value< curTreeNode.value.get()){
            if(curTreeNode.haveLeftChild()){
                animationManager.addNewAnimation(curTreeNode.leftChild.edge.getToFromEmphaAnimation());
                animationManager.addNewAnimation(curTreeNode.leftChild.visuNode.getEmphasizeAnimation());
                curTreeNode.leftChild= innerInsert(curTreeNode.leftChild,value,true);
                if(getHeight(curTreeNode.leftChild)-getHeight(curTreeNode.rightChild)>1){
                    if(value< curTreeNode.leftChild.value.get())
                        curTreeNode =LLRotation(curTreeNode,isLeftChild);
                    else
                        curTreeNode =LRRotation(curTreeNode,isLeftChild);
                }
            }else{
                addNode(value, curTreeNode,true);
            }
        }else if(value> curTreeNode.value.get()){
            if(curTreeNode.haveRightChild()){
                animationManager.addNewAnimation(curTreeNode.rightChild.edge.getToFromEmphaAnimation());
                animationManager.addNewAnimation(curTreeNode.rightChild.visuNode.getEmphasizeAnimation());
                curTreeNode.rightChild= innerInsert(curTreeNode.rightChild,value,false);
                if(getHeight(curTreeNode.rightChild)-getHeight(curTreeNode.leftChild)>1){
                    if(value< curTreeNode.rightChild.value.get())
                        curTreeNode =RLRotation(curTreeNode,isLeftChild);
                    else
                        curTreeNode =RRRotation(curTreeNode,isLeftChild);
                }
            }else{
                addNode(value, curTreeNode,false);
            }
        }
        curTreeNode.height=Math.max(getHeight(curTreeNode.leftChild),getHeight(curTreeNode.rightChild))+1;
        if(curTreeNode ==root)
            reCalcNodeLayoutAndGetAnima(curTreeNode);
        else
            reCalcNodeLayoutAndGetAnima(curTreeNode, curTreeNode.parent,isLeftChild);
        return curTreeNode;
    }

    private void reBalance(TreeNode curTreeNode){
        while(curTreeNode !=null) {
            boolean isLeftChild = curTreeNode.parent != null && curTreeNode.value.get() < curTreeNode.parent.value.get();
            if (getHeight(curTreeNode.leftChild) - getHeight(curTreeNode.rightChild) > 1)
                curTreeNode = LLRotation(curTreeNode, isLeftChild);
            else if (getHeight(curTreeNode.leftChild) - getHeight(curTreeNode.rightChild) < -1)
                curTreeNode = RRRotation(curTreeNode, isLeftChild);
            curTreeNode.height = Math.max(getHeight(curTreeNode.leftChild), getHeight(curTreeNode.rightChild)) + 1;
            curTreeNode = curTreeNode.parent;
        }
    }

    @Override
    public boolean insert(int value){
        if(root==null)
            addFirstNode(value);
        else
            innerInsert(root,value,false);
        return true;
    }

    @Override
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
                    reBalance(curTreeNode.parent);
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
                        reBalance(curTreeNode.parent);
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
                            removeRoot.setOnFinished(e-> anchorPane.getChildren().removeAll(oldRoot.visuNode));
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
