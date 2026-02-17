package com.graphs.models;

import com.graphs.models.edge.Edge;
import com.graphs.models.edge.EdgeFactory;

import java.util.List;

public class UndirectedGraph<T> extends AbstractGraph<T> {
    public UndirectedGraph(boolean isWeighted) {
        super(isWeighted);
    }

    @Override
    public void addEdge(Node<T> firstNode, Node<T> secondNode, int weight) {
        addNode(firstNode);
        addNode(secondNode);
        List<Edge<T>> edgeList = EdgeFactory.createUndirectedEdge(firstNode, secondNode, weight);
        edges.get(firstNode).add(edgeList.getFirst());
        edges.get(secondNode).add(edgeList.get(1));
    }

    @Override
    public void removeEdge(Node<T> firstNode, Node<T> secondNode) {
        if (edges.containsKey(firstNode) && edges.containsKey(secondNode)) {
            edges.get(firstNode).removeIf(e -> e.getEndNode().equals(secondNode));
            edges.get(secondNode).removeIf(e -> e.getEndNode().equals(firstNode));
        } else {
            throw new IllegalArgumentException("Nodes doesn't exists");
        }
    }
}
