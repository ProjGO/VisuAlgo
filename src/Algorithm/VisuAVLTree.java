package Algorithm;

import BasicAnimation.AnimationGenerator;
import BasicVisuDS.Node;
import VisualElements.Edge.Edge;
import VisualElements.Edge.UnwUndirEdge;
import VisualElements.Node.BasicNode;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
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

    private Node LLRotation(Node root,boolean rootIsLeftChild){
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
            nodeMove1 = reCalcNodeLayoutAndGetAnima(leftChild, parent, rootIsLeftChild);
            Animation nodeMove2 = reCalcNodeLayoutAndGetAnima(root, leftChild, leftChild.layoutX, leftChild.layoutY, false);
            ParallelTransition nodeMove = new ParallelTransition(nodeMove1, nodeMove2);

            animationManager.addNewAnimation(new SequentialTransition(edgeMove, nodeMove));
            root.height=Math.max(getHeight(root.leftChild),getHeight(root.rightChild))+1;
            leftChild.height=Math.max(getHeight(leftChild.leftChild),getHeight(leftChild.rightChild))+1;
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
            leftChild.parent=null;
            this.root=leftChild;
            Animation animation=reCalcNodeLayoutAndGetAnima(leftChild);
            animation.setOnFinished(e->{
                root.edge=new UnwUndirEdge(root.visuNode,leftChild.visuNode);
                Node.getAnchorPane().getChildren().add(root.edge);
                Node.getAnchorPane().getChildren().remove(leftChild.edge);
                leftChild.edge=null;
            });
            animationManager.addNewAnimation(animation);
            root.height=Math.max(getHeight(root.leftChild),getHeight(root.rightChild))+1;
            leftChild.height=Math.max(getHeight(leftChild.leftChild),getHeight(leftChild.rightChild))+1;
            return leftChild;
        }
    }//LL和RR旋转中会直接生成动画并加入到animationManager中,并且考虑了对根节点的特殊处理

    private Node RRRotation(Node root,boolean rootIsLeftChild){
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

            root.height=Math.max(getHeight(root.leftChild),getHeight(root.rightChild))+1;
            rightChild.height=Math.max(getHeight(rightChild.leftChild),getHeight(rightChild.rightChild))+1;

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
            rightChild.parent=null;
            this.root=rightChild;
            Animation animation=reCalcNodeLayoutAndGetAnima(rightChild);
            animation.setOnFinished(e->{
                root.edge=new UnwUndirEdge(root.visuNode,rightChild.visuNode);
                Node.getAnchorPane().getChildren().add(root.edge);
                Node.getAnchorPane().getChildren().remove(rightChild.edge);
                rightChild.edge=null;
            });
            animationManager.addNewAnimation(animation);

            root.height=Math.max(getHeight(root.leftChild),getHeight(root.rightChild))+1;
            rightChild.height=Math.max(getHeight(rightChild.leftChild),getHeight(rightChild.rightChild))+1;

            return rightChild;
        }
    }

    private Node LRRotation(Node root,boolean rootIsLeftChild){
        root.leftChild=RRRotation(root.leftChild,true);
        return LLRotation(root,rootIsLeftChild);
    }

    private Node RLRotation(Node root,boolean rootIsLeftChild){
        root.rightChild=LLRotation(root.rightChild,false);
        return RRRotation(root,rootIsLeftChild);
    }

    private Node innerInsert(Node curNode, int value, boolean isLeftChild){
        if(value<curNode.value.get()){
            if(curNode.haveLeftChild()){
                animationManager.addNewAnimation(curNode.leftChild.edge.getToFromEmphaAnimation());
                animationManager.addNewAnimation(curNode.leftChild.visuNode.getEmphasizeAnimation());
                curNode.leftChild= innerInsert(curNode.leftChild,value,true);
                if(getHeight(curNode.leftChild)-getHeight(curNode.rightChild)>1){
                    if(value<curNode.leftChild.value.get())
                        curNode=LLRotation(curNode,isLeftChild);
                    else
                        curNode=LRRotation(curNode,isLeftChild);
                }
            }else{
                addNode(value,curNode,true);
            }
        }else if(value>curNode.value.get()){
            if(curNode.haveRightChild()){
                animationManager.addNewAnimation(curNode.rightChild.edge.getToFromEmphaAnimation());
                animationManager.addNewAnimation(curNode.rightChild.visuNode.getEmphasizeAnimation());
                curNode.rightChild= innerInsert(curNode.rightChild,value,false);
                if(getHeight(curNode.rightChild)-getHeight(curNode.leftChild)>1){
                    if(value<curNode.rightChild.value.get())
                        curNode=RLRotation(curNode,isLeftChild);
                    else
                        curNode=RRRotation(curNode,isLeftChild);
                }
            }else{
                addNode(value,curNode,false);
            }
        }
        curNode.height=Math.max(getHeight(curNode.leftChild),getHeight(curNode.rightChild))+1;
        if(curNode==root)
            reCalcNodeLayoutAndGetAnima(curNode);
        else
            reCalcNodeLayoutAndGetAnima(curNode, curNode.parent,isLeftChild);
        return curNode;
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
                    rebalance(curNode.parent);
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
                        rebalance(curNode.parent);
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

    private void rebalance(Node curNode){
        while(curNode!=null) {
            boolean isLeftChild = curNode.parent != null && curNode.value.get() < curNode.parent.value.get();
            if (getHeight(curNode.leftChild) - getHeight(curNode.rightChild) > 1)
                curNode = LLRotation(curNode, isLeftChild);
            else if (getHeight(curNode.leftChild) - getHeight(curNode.rightChild) < -1)
                curNode = RRRotation(curNode, isLeftChild);
            curNode.height = Math.max(getHeight(curNode.leftChild), getHeight(curNode.rightChild)) + 1;
            curNode=curNode.parent;
        }
    }
}
