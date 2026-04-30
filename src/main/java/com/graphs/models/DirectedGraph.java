package com.graphs.models;

import com.graphs.models.edge.Edge;
import com.graphs.models.edge.EdgeFactory;

import java.util.*;

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
    public void addEdge(Node<T> firstNode, Node<T> secondNode, double weight) {
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

    public Set<Node<T>> getIsolationNodes() {
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

    public Set<Set<Node<T>>> getMutualNodes() {
        HashSet<Set<Node<T>>> mutualNodes = new HashSet<>();
        for (Map.Entry<Node<T>, Set<Edge<T>>> entry : edges.entrySet()) {
            Node<T> node = entry.getKey();
            for (Edge<T> edge : entry.getValue()) {
                if (hasEdge(edge.getEndNode(), node)) {
                    mutualNodes.add(new HashSet<>(List.of(node, edge.getEndNode())));
                }
            }
        }
        return mutualNodes;
    }


    public List<Node<T>> topologicalSort() {
        LinkedList<Node<T>> stack = new LinkedList<>();
        Set<Node<T>> visited = new HashSet<>();
        Set<Node<T>> recursiveStack = new HashSet<>();
        for (Node<T> node : getNodes()) {
            if (!visited.contains(node)) {
                if (!topoSortDFS(node, visited, recursiveStack, stack)) {
                    throw new IllegalStateException("Граф содержит цикл! Топологическая сортировка невозможна.");
                }
            }
        }
        return stack;
    }

    private boolean topoSortDFS(Node<T> current, Set<Node<T>> visited,
                                Set<Node<T>> recursiveStack, LinkedList<Node<T>> stack) {
        if (recursiveStack.contains(current)) {
            return false;
        }
        if (visited.contains(current)) {
            return true;
        }
        visited.add(current);
        recursiveStack.add(current);
        Set<Edge<T>> neighbors = edges.getOrDefault(current, Collections.emptySet());
        for (Edge<T> edge : neighbors) {
            if (!topoSortDFS(edge.getEndNode(), visited, recursiveStack, stack)) {
                return false;
            }
        }
        recursiveStack.remove(current);
        stack.addFirst(current);

        return true;
    }
}
