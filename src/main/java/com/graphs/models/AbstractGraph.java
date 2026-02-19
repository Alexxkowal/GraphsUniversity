package com.graphs.models;

import com.graphs.models.edge.Edge;
import com.graphs.models.edge.EdgeFactory;
import com.graphs.utils.TextGraphSerializer;

import java.util.*;

public abstract class AbstractGraph<T> implements Graph<T> {
    protected Map<Node<T>, List<Edge<T>>> edges = new HashMap<>();
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
            List<Edge<T>> edgeList = new ArrayList<>();
            for (Edge<T> edge : graph.getEdges(node)) {
                edgeList.add(EdgeFactory.copy(edge));
            }
            this.edges.put(node, edgeList);
        }

    }

    @Override
    public void addNode(Node<T> node) {
        edges.putIfAbsent(node, new ArrayList<>());
    }

    @Override
    public void removeNode(Node<T> node) {
        edges.remove(node);
        for (List<Edge<T>> list : edges.values()) {
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
    public abstract void removeEdge(Node<T> firstNode, Node<T> secondNode);

    public Map<Node<T>, List<Edge<T>>> getEdges() {
        return edges;
    }

    public boolean isWeighted() {
        return isWeighted;
    }

    public List<Edge<T>> getEdges(Node<T> node) {
        return edges.get(node);
    }

    ;

    public Set<Node<T>> getNodes() {
        return edges.keySet();
    }

    ;

    public List<Edge<T>> getAllEdges() {
        Set<Edge<T>> edgeSet = new HashSet<>();
        for (List<Edge<T>> listEdges : edges.values()) {
            edgeSet.addAll(listEdges);
        }
        return new ArrayList<>(edgeSet);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        if (o == this) return true;
        AbstractGraph<?> graph = (AbstractGraph<?>) o;
        if (this.edges.size() != graph.getEdges().size()) return false;
        for (Node<T> node : this.getNodes()) {
            List<Edge<T>> thisList = this.getEdges(node);
            List<?> graphList = graph.getEdges((Node) node);
            if (graphList == null) return false;
            if (!new HashSet<>(thisList).equals(new HashSet<>(graphList))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Map.Entry<Node<T>, List<Edge<T>>> entry : edges.entrySet()) {
            result += entry.getKey().hashCode() + new HashSet<>(entry.getValue()).hashCode();
        }
        return result;
    }
}
