package com.graphs.models;

import com.graphs.models.edge.Edge;
import com.graphs.models.edge.EdgeFactory;
import com.graphs.utils.TextGraphSerializer;

import java.util.*;

public abstract class AbstractGraph<T> implements Graph<T> {
    protected Map<Node<T>, Set<Edge<T>>> edges = new HashMap<>();
    protected boolean isWeighted = true;

    public AbstractGraph(boolean isWeighted) {
        this.isWeighted = isWeighted;
    }

    public AbstractGraph() {
    }

    public AbstractGraph(String path) {
        TextGraphSerializer<T> serializer = new TextGraphSerializer<>();
        serializer.importGraph(this, path);
    }

    public AbstractGraph(Graph<T> graph) {
        this.isWeighted = graph.isWeighted();
        for (Node<T> node : graph.getNodes()) {
            Set<Edge<T>> edgeList = new HashSet<>();
            for (Edge<T> edge : graph.getEdges(node)) {
                edgeList.add(EdgeFactory.copy(edge));
            }
            this.edges.put(node, edgeList);
        }

    }

    @Override
    public boolean addNode(Node<T> node) {
        return edges.putIfAbsent(node, new HashSet<>()) == null;
    }

    @Override
    public boolean removeNode(Node<T> node) {
        if (edges.remove(node) == null) return false;
        for (Set<Edge<T>> list : edges.values()) {
            list.removeIf(e -> e.getEndNode().equals(node));
        }
        return true;
    }

    @Override
    public void addEdge(Node<T> firstNode, Node<T> secondNode) {
        this.addEdge(firstNode, secondNode, 1);
    }

    @Override
    public abstract void addEdge(Node<T> firstNode, Node<T> secondNode, int weight);

    @Override
    public abstract void removeEdge(Node<T> firstNode, Node<T> secondNode);

    public Map<Node<T>, Set<Edge<T>>> getEdges() {
        return edges;
    }

    public boolean isWeighted() {
        return isWeighted;
    }

    public Set<Edge<T>> getEdges(Node<T> node) {
        return edges.get(node);
    }

    ;

    public Set<Node<T>> getNodes() {
        return edges.keySet();
    }

    public Set<Edge<T>> getAllEdges() {
        Set<Edge<T>> edgeSet = new HashSet<>();
        for (Set<Edge<T>> listEdges : edges.values()) {
            edgeSet.addAll(listEdges);
        }
        return edgeSet;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        if (o == this) return true;
        AbstractGraph<?> graph = (AbstractGraph<?>) o;
        if (this.edges.size() != graph.getEdges().size()) return false;
        for (Node<T> node : this.getNodes()) {
            Set<Edge<T>> thisSet = this.getEdges(node);
            Set<?> graphList = graph.getEdges((Node) node);
            if (graphList == null) return false;
            if (!thisSet.equals(new HashSet<>(graphList))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Map.Entry<Node<T>, Set<Edge<T>>> entry : edges.entrySet()) {
            result += entry.getKey().hashCode() + entry.getValue().hashCode();
        }
        return result;
    }

    public boolean hasEdge(Node<T> start, Node<T> end) {
        Set<Edge<T>> nodeEdges = edges.get(start);
        if (nodeEdges == null) return false;
        return nodeEdges.contains(EdgeFactory.createDirectedEdge(start, end, 0));
    }

}
