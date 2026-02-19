package com.graphs.models.edge;

import com.graphs.models.Node;

import java.util.Objects;

public class Edge<T> {
    private final Node<T> startNode;
    private final Node<T> endNode;
    private final int weight;

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
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        if (this == obj) return true;
        Edge<T> edge = (Edge<T>) obj;
        return (this.getStartNode().equals(edge.getStartNode()) && this.getEndNode().equals(edge.getEndNode())
                && this.getWeight() == edge.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startNode, endNode, weight);
    }
}
