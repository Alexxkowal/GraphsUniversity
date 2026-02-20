package com.graphs.models.edge;

import com.graphs.models.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Edge<T> {
    private final Node<T> startNode;
    private final Node<T> endNode;
    private int weight;

    Edge(Node<T> startNode, Node<T> endNode, int weight) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

    public Node<T> getStartNode() {
        return startNode;
    }

    public Node<T> getEndNode() {
        return endNode;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge<?> edge)) return false;
        return
//                this.weight == edge.getWeight() &&
                        Objects.equals(this.startNode, edge.getStartNode()) &&
                        Objects.equals(this.endNode, edge.getEndNode());
    }

    // Добавлять вес??
    @Override
    public int hashCode() {
        return Objects.hash(startNode, endNode);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return startNode + " " + endNode + " " + weight;
    }

//    public boolean equalsNodeDirected(Edge<T> otherEdge){
//        return this.startNode.equals(otherEdge.getStartNode()) && this.endNode.equals(otherEdge.getEndNode());
//    }
//
//    public boolean equalsNodeUndirected(Edge<T> otherEdge){
//        return (this.startNode.equals(otherEdge.getStartNode()) && this.endNode.equals(otherEdge.getEndNode()) ||
//                this.startNode.equals(otherEdge.getEndNode()) && this.endNode.equals(otherEdge.getStartNode()));
//    }
}
