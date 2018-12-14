package BasicVisuDS;

import VisualElements.Edge.Edge;

public class GraphEdge {
    private GraphNode fromNode,toNode;
    Edge visuEdge;

    public GraphEdge(GraphNode fromNode,GraphNode toNode,Edge visuEdge){
        this.fromNode=fromNode;
        this.toNode=toNode;
        this.visuEdge = visuEdge;
    }

    public GraphNode getFromNode(){
        return fromNode;
    }

    public GraphNode getToNode(){
        return toNode;
    }

    public Edge getVisuEdge(){
        return visuEdge;
    }

}
