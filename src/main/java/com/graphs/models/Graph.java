package com.graphs.models;

public interface Graph<T> {
    void addNode(Node<T> node);

    void removeNode(Node<T> node);

    void addEdge(Node<T> firstNode, Node<T> secondNode);

    void addEdge(Node<T> firstNode, Node<T> secondNode, int weight);

    void removeEdge(Node<T> firstNode, Node<T> secondNode);

}
