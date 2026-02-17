package com.graphs.utils;

import com.graphs.models.Graph;

import java.io.FileNotFoundException;

public interface GraphSerializer<T> {
    void exportGraph(Graph<T> graph, String filePath);

    void importGraph(Graph<T> graph, String filePath);
}
