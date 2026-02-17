package com.graphs.models.edge;

import com.graphs.models.Node;

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
}
