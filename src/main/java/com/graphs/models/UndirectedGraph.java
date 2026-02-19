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

    public UndirectedGraph(){
        super();
    }

    public UndirectedGraph(String path){
        super(path);
    }

    public UndirectedGraph(Graph<T> graph){
        super(graph);
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

    @Override
    public List<Edge<T>> getAllEdges() {
        Set<Edge<T>> edgeSet = new HashSet<>();
        for (List<Edge<T>> listEdges : edges.values()) {
            for (var edge : listEdges) {
                if (edge.getStartNode().hashCode() > edge.getEndNode().hashCode()) {
                    continue;
                }
                edgeSet.add(edge);
            }
        }
        return new ArrayList<>(edgeSet);
    }
}
