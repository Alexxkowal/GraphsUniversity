package com.graphs.models;

import com.graphs.models.edge.Edge;
import com.graphs.models.edge.EdgeFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectedGraph<T> extends AbstractGraph<T> {
    public DirectedGraph(boolean isWeighted) {
        super(isWeighted);
    }

    public DirectedGraph() {
        super();
    }

    public DirectedGraph(String path) {
        super(path);
    }

    public DirectedGraph(Graph<T> graph) {
        super(graph);
    }

    /*
    Поменял логику, теперь при добавлении связи будет меняться значение веса
     */
    @Override
    public void addEdge(Node<T> firstNode, Node<T> secondNode, int weight) {
        addNode(firstNode);
        addNode(secondNode);
        Edge<T> edge = EdgeFactory.createDirectedEdge(firstNode, secondNode, weight);
        edges.get(firstNode).remove(edge);
        edges.get(firstNode).add(edge);
    }

    @Override
    public void removeEdge(Node<T> firstNode, Node<T> secondNode) {
        Set<Edge<T>> nodeEdges = edges.get(firstNode);
        if (nodeEdges != null) {
            nodeEdges.remove(EdgeFactory.createDirectedEdge(firstNode, secondNode, 0));
        }
    }

    public Set<Node<T>> showIsolationNodes() {
        HashSet<Node<T>> isolatedNodes = new HashSet<>(edges.keySet());
        for (Map.Entry<Node<T>, Set<Edge<T>>> entry : edges.entrySet()) {
            if (entry.getValue().isEmpty()) {
                continue;
            }
            isolatedNodes.remove(entry.getKey());
            for (Edge<T> edge : entry.getValue()) {
                isolatedNodes.remove(edge.getEndNode());
            }
        }
        return isolatedNodes;
    }
}
