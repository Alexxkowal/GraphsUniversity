package com.graphs.models;

public class Edge<T> {
    private final Node<T> startNode;
    private final Node<T> endNode;
    private final int weight;

    protected Edge(Node<T> startNode, Node<T> endNode, int weight) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }
}
