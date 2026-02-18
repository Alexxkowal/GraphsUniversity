package com.graphs.utils;

import com.graphs.models.DirectedGraph;
import com.graphs.models.Graph;
import com.graphs.models.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextGraphSerializerTest {

    @Test
    void testExportAndImport() {
        DirectedGraph<String> directedGraph = new DirectedGraph<>(true);
        TextGraphSerializer<String> textGraphSerializer = new TextGraphSerializer<>();
        directedGraph.addEdge(new Node<>("A"), new Node<>("B"), 5);
        textGraphSerializer.exportGraph(directedGraph, "test.txt");
        DirectedGraph<String> directedGraph2 = new DirectedGraph<>(true);
        textGraphSerializer.importGraph(directedGraph2, "test.txt");
        Assertions.assertEquals(directedGraph.getNodes().size(), directedGraph2.getNodes().size());
        Node<String> nodeA = new Node<>("A");
        var edgesA = directedGraph2.getEdges(nodeA);

        Assertions.assertFalse(edgesA.isEmpty(), "Список ребер не должен быть пустым!");
        Assertions.assertEquals(5, edgesA.get(0).getWeight(), "Вес ребра должен быть 5!");
        Assertions.assertEquals("B", edgesA.get(0).getEndNode().getContent(), "Конечный узел должен быть B!");
    }
}