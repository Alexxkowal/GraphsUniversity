package com.graphs.models;

import com.graphs.models.edge.Edge;

import java.util.Set;

public interface Graph<T> {
    boolean addNode(Node<T> node);

    boolean removeNode(Node<T> node);

    void addEdge(Node<T> firstNode, Node<T> secondNode);

    void addEdge(Node<T> firstNode, Node<T> secondNode, double weight);

    void removeEdge(Node<T> firstNode, Node<T> secondNode);

    boolean isWeighted();

    Set<Edge<T>> getEdges(Node<T> node);

    Set<Node<T>> getNodes();

    Set<Edge<T>> getAllEdges();

    boolean hasEdge(Node<T> firsNode, Node<T> secondNode);

    boolean hasNode(T content);

    String getAdjacencyList(Node<T> node);

    Set<Node<T>> getSharedNeighbor(Node<T> u, Node<T> v);
}
