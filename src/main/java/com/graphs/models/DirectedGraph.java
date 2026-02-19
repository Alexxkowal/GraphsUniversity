package com.graphs.models;

import com.graphs.models.edge.EdgeFactory;

public class DirectedGraph<T> extends AbstractGraph<T>{
    public DirectedGraph(boolean isWeighted) {
        super(isWeighted);
    }

    public DirectedGraph(){
        super();
    }

    public DirectedGraph(String path){
        super(path);
    }

    public DirectedGraph(Graph<T> graph){
        super(graph);
    }

    @Override
    public void addEdge(Node<T> firstNode, Node<T> secondNode, int weight) {
        addNode(firstNode);
        addNode(secondNode);
        edges.get(firstNode).add(EdgeFactory.createDirectedEdge(firstNode, secondNode, weight));
    }

    @Override
    public void removeEdge(Node<T> firstNode, Node<T> secondNode) {
        if (edges.containsKey(firstNode)) edges.get(firstNode).removeIf(e -> e.getEndNode().equals(secondNode));
    }
}
