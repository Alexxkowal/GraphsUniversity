package com.graphs.models.edge;

import com.graphs.models.Node;

import java.util.List;

public class EdgeFactory<T>{
    public static <T> Edge<T> createDirectedEdge(Node<T> startNode, Node<T> endNode, int weight){
        return new Edge<T>(startNode, endNode, weight);
    }
    public static <T> List<Edge<T>> createUndirectedEdge(Node<T> node1, Node<T> node2, int weight){
        return List.of(new Edge<>(node1, node2, weight), new Edge<>(node2, node1, weight));
    }

    public static <T> Edge<T> createDirectedEdge(Node<T> startNode, Node<T> endNode){
        return new Edge<T>(startNode, endNode, 1);
    }
    public static <T> List<Edge<T>> createUndirectedEdge(Node<T> node1, Node<T> node2){
        return List.of(new Edge<>(node1, node2, 1), new Edge<>(node2, node1, 1));
    }
}
