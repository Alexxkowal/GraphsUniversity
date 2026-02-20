package com.graphs.models;

import com.graphs.models.edge.Edge;

import java.util.List;
import java.util.Set;

public interface Graph<T> {
    boolean addNode(Node<T> node);

    boolean removeNode(Node<T> node);

    void addEdge(Node<T> firstNode, Node<T> secondNode);

    void addEdge(Node<T> firstNode, Node<T> secondNode, int weight);

    void removeEdge(Node<T> firstNode, Node<T> secondNode);

    boolean isWeighted();

    List<Edge<T>> getEdges(Node<T> node);

    Set<Node<T>> getNodes();

    List<Edge<T>> getAllEdges();

}
