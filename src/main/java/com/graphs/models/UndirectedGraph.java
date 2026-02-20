package com.graphs.models;

import com.graphs.models.edge.Edge;
import com.graphs.models.edge.EdgeFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UndirectedGraph<T> extends AbstractGraph<T> {
    public UndirectedGraph(boolean isWeighted) {
        super(isWeighted);
    }

    public UndirectedGraph() {
        super();
    }

    public UndirectedGraph(String path) {
        super(path);
    }

    public UndirectedGraph(Graph<T> graph) {
        super(graph);
    }

    @Override
    public void addEdge(Node<T> firstNode, Node<T> secondNode, int weight) {
        addNode(firstNode);
        addNode(secondNode);
        List<Edge<T>> edgeList = EdgeFactory.createUndirectedEdge(firstNode, secondNode, weight);
        edges.get(firstNode).remove(edgeList.getFirst());
        edges.get(secondNode).remove(edgeList.get(1));
        edges.get(firstNode).add(edgeList.getFirst());
        edges.get(secondNode).add(edgeList.get(1));
    }


    @Override
    public void removeEdge(Node<T> firstNode, Node<T> secondNode) {
        Set<Edge<T>> firstSet = edges.get(firstNode);
        if (firstSet != null) {
            firstSet.remove(EdgeFactory.createDirectedEdge(firstNode, secondNode, 0));
        }
        Set<Edge<T>> secondSet = edges.get(secondNode);
        if (secondSet != null) {
            secondSet.remove(EdgeFactory.createDirectedEdge(secondNode, firstNode, 0));
        }
    }

    @Override
    public Set<Edge<T>> getAllEdges() {
        Set<Edge<T>> edgeSet = new HashSet<>();
        for (Set<Edge<T>> setEdges : edges.values()) {
            for (var edge : setEdges) {
                if (edge.getStartNode().hashCode() > edge.getEndNode().hashCode()) {
                    continue;
                }
                edgeSet.add(edge);
            }
        }
        return edgeSet;
    }
}
