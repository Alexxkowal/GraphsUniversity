package com.graphs.models;

import com.graphs.models.edge.Edge;

import java.util.*;

public abstract class AbstractGraph<T> implements Graph<T>{
    protected Map<Node<T>, List<Edge<T>>> edges;
    protected final boolean isWeighted;

    public AbstractGraph(boolean isWeighted) {
        this.isWeighted = isWeighted;
        this.edges = new HashMap<>();
    }

    @Override
    public void addNode(Node<T> node) {
        edges.putIfAbsent(node, new ArrayList<>());
    }

    @Override
    public void removeNode(Node<T> node) {
        edges.remove(node);
        for (List<Edge<T>> list: edges.values() )
        {
            list.removeIf(e -> e.getEndNode().equals(node));
        }
    }

    @Override
    public void addEdge(Node<T> firstNode, Node<T> secondNode) {
        this.addEdge(firstNode, secondNode, 1);
    }

    @Override
    public abstract void addEdge(Node<T> firstNode, Node<T> secondNode, int weight);

    @Override
    public  abstract void removeEdge(Node<T> firstNode, Node<T> secondNode);

    public Map<Node<T>, List<Edge<T>>> getEdges() {
        return edges;
    }

    public boolean isWeighted() {
        return isWeighted;
    }

    public List<Edge<T>> getEdges(Node<T> node){
        return edges.get(node);
    };

    public Set<Node<T>> getNodes(){
        return edges.keySet();
    };
}
