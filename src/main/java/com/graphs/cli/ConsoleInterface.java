package com.graphs.cli;

import com.graphs.models.DirectedGraph;
import com.graphs.models.Graph;
import com.graphs.models.Node;
import com.graphs.models.UndirectedGraph;
import com.graphs.models.edge.Edge;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleInterface {
    private Graph<String> graph;
    private final Scanner scanner = new Scanner(System.in);
    Map<String, Graph<String>> graphs = new HashMap<>();
    String currentGraphName;

    public void start() {
        System.out.println("Для выхода из программы введите 0");
        graph = initialize();
        String choose;
        while (true) {
            System.out.printf("Текущий граф %s \n", currentGraphName);
            System.out.println("Для выхода из программы введите 0");
            System.out.println("1 - Добавить вершину");
            System.out.println("2 - Удалить вершину");
            System.out.println("3 - Добавить ребро");
            System.out.println("4 - Удалить ребро");
            System.out.println("5 - Показать все ребра");
            System.out.println("6 - Показать все узлы");
            System.out.println("7 - Показать список смежности для узла");
            System.out.println("8 - Показать список смежности ");
            System.out.println("9 - Создать новый граф");
            System.out.println("10 - Переключить граф");
            System.out.println("11 - Вывести все изолированные вершины орграфа (степени 0).");
            System.out.println("12 - Вывести вершины, имеющие дугу друг в друга в орграфе");
            System.out.println("13 - Вывести общую вершину для двух узлов");
            System.out.println("14 - Создать граф пересечение");
            System.out.println("15 - Количество путей из u в v");
            System.out.println("16 - топологическая сортировка");
            System.out.println("17 - Кракас Прима");
            choose = scanner.nextLine().trim();

            switch (choose) {
                case "0":
                    System.exit(0);
                case "1":
                    addNode();
                    break;
                case "2":
                    removeNode();
                    break;
                case "3":
                    addEdge();
                    break;
                case "4":
                    removeEdge();
                case "5":
                    showEdges();
                    break;
                case "6":
                    showAllNodes();
                    break;
                case "7":
                    showNodeAdjacencyList();
                    break;
                case "8":
                    showAdjacencyList();
                    break;
                case "9":
                    this.graph = initialize();
                    break;
                case "10":
                    changeGraph();
                    break;
                case "11":
                    showIsolatedNodesInDirectedGraph();
                    break;
                case "12":
                    showMutualNodesInDirectedGraph();
                    break;
                case "13":
                    showSharedNeighbor();
                    break;
                case "14":
                    intersection();
                    break;
                case "15":
                    paths();
                case "16":
                    topologicalSort();
                case "17":
                    carcas();
                default:
                    System.out.println("Неправильный ввод, повторите попытку");
                    break;
            }
        }
    }

    private void carcas() {
        if (graph instanceof UndirectedGraph<String> undirectedGraph) {
            var edges = undirectedGraph.findMinimumSpanningTree();
            if (edges.isEmpty()) {
                System.out.println("Граф пуст");
            } else {
                for (var e : edges) {
                    System.out.println(e);
                }
            }
        } else {
            System.out.println("Граф направленный!!");
        }
    }

    private void topologicalSort() {
        try {
            if (graph instanceof DirectedGraph<String> directedGraph) {
                var sortedNodes = directedGraph.topologicalSort();
                if (sortedNodes.isEmpty()) {
                    System.out.println("Граф пуст.");
                } else {
                    for (Node<String> node : sortedNodes) {
                        System.out.println(node);
                    }
                }
            } else {
                System.out.println("Граф ненаправленный, сортировка невозможна!");
            }
        } catch (IllegalStateException e) {
            System.out.println("Ошибка " + e.getMessage());
        }
    }

    private void showNodeAdjacencyList() {
        String content;
        System.out.println("Введите узел ");
        content = scanner.nextLine().trim();
        if (graph.hasNode(content)) {
            System.out.println(graph.getAdjacencyList(new Node<>(content)));
        } else {
            System.out.println("Такого узла нет");
        }
    }

    private void showAdjacencyList() {
        System.out.println(graph.toString());
    }

    private Graph<String> initialize() {
        String graphName;
        String direct;
        String isWeightedInput;
        Graph<String> createdGraph;
        boolean isWeighted = false;
        boolean isDirected = false;
        boolean firstChoose = false;
        boolean secondChoose = false;
        System.out.println("Введите название графа: ");
        graphName = scanner.nextLine().trim();
        while (graphs.containsKey(graphName) || graphName.isEmpty()) {
            System.out.println("Имя занято, введите другое");
            graphName = scanner.nextLine().trim();
        }
        while (!firstChoose) {
            System.out.println("Выберите взвешенность графа 1) Взешенный, 2) Невзвешенный");
            isWeightedInput = scanner.nextLine();
            switch (isWeightedInput) {
                case "1":
                    isWeighted = true;
                    firstChoose = true;
                    break;
                case "2":
                    firstChoose = true;
                    break;
                default:
                    System.out.println("Некорректный ввод");
                    break;
            }
        }
        while (!secondChoose) {
            System.out.println("Выберите тип графа: 1) Направленный; 2) Ненаправленный");
            direct = scanner.nextLine();
            switch (direct) {
                case "1":
                    isDirected = true;
                    secondChoose = true;
                    break;
                case "2":
                    secondChoose = true;
                    break;
                default:
                    System.out.println("Некорректный ввод");
                    break;
            }
        }
        if (isDirected) {
            System.out.println("Создан направленный граф, взвешенность: " + (isWeighted ? "взвешенный" : "невзвешенный"));
            createdGraph = new DirectedGraph<>(isWeighted);
        } else {
            System.out.println("Создан ненаправленный граф, взвешенность: " + (isWeighted ? "взвешенный" : "невзвешенный"));
            createdGraph = new UndirectedGraph<>(isWeighted);
        }
        setCurrentGraphName(graphName);
        graphs.put(graphName, createdGraph);
        return createdGraph;
    }

    private void addNode() {
        System.out.println("Введите содержимое узла");
        String content = scanner.nextLine().trim();
        Node<String> node = new Node<>(content.trim());
        boolean isAdded = graph.addNode(node);
        if (isAdded) {
            System.out.println("Узел с контентом " + content + " успешно добавлен в граф");
        } else {
            System.out.println("Узел с таким именем уже существует");
        }
    }

    private void removeNode() {
        System.out.println("Введите узел");
        String content = scanner.nextLine().trim();
        Node<String> nodeForRemove = new Node<>(content.trim());
        boolean isRemoved = graph.removeNode(nodeForRemove);
        if (isRemoved) {
            System.out.println("Узел " + content + " успешно удален");
        } else {
            System.out.println("Такого узла нет в графе");
        }
    }

    private void addEdge() {
        System.out.println("Введите первый узел");
        String firstNodeContent = scanner.nextLine().trim();
        System.out.println("Введите второй узел");
        String secondNodeContent = scanner.nextLine().trim();
        Node<String> firstNode = new Node<>(firstNodeContent);
        Node<String> secondNode = new Node<>(secondNodeContent);
        boolean firstChoose = false;
        boolean secondChoose = false;
        String choose;

        if (!graph.getNodes().contains(firstNode)) {
            System.out.println("Узла с именем " + firstNodeContent + " нет в графе, создать его?");
            System.out.println("1 - создать, 2 - не создавать (выйти)");
            while (!firstChoose) {
                choose = scanner.nextLine();
                switch (choose) {
                    case "1":
                        System.out.println("Узел успешно создан");
                        firstChoose = true;
                        break;
                    case "2":
                        return;
                    default:
                        System.out.println("Некорректный ввод");
                        break;
                }
            }
        }
        if (!graph.getNodes().contains(secondNode)) {
            System.out.println("Узла с именем " + secondNodeContent + " нет в графе, создать его?");
            System.out.println("1 - создать, 2 - не создавать (выйти)");

            while (!secondChoose) {
                choose = scanner.nextLine();
                switch (choose) {
                    case "1":
                        System.out.println("Узел успешно создан");
                        secondChoose = true;
                        break;
                    case "2":
                        return;
                    default:
                        System.out.println("Некорректный ввод");
                        break;
                }
            }
        }
        if (graph.hasEdge(new Node<>(firstNodeContent), new Node<>(secondNodeContent))) {
            String rewrite;
            System.out.println("Такая связь уже существует. Перезаписать?");
            System.out.println("1 - да, другое - нет");
            rewrite = scanner.nextLine();
            switch (rewrite) {
                case "1":
                    System.out.println("Перезапись...");
                    break;
                default:
                    return;
            }
        }
        if (graph.isWeighted()) {
            System.out.println("Введите вес");
            int weight = Integer.parseInt(scanner.nextLine());
            System.out.println("Связь " + firstNode + " и " + secondNode + " с весом " + weight + " создана");
            graph.addEdge(firstNode, secondNode, weight);
            return;
        }
        System.out.println("Связь " + firstNode + " и " + secondNode + " создана");
        graph.addEdge(firstNode, secondNode);
    }

    private void removeEdge() {
        System.out.println("Введите первый узел");
        String firstNodeContent = scanner.nextLine().trim();
        System.out.println("Введите второй узел");
        String secondNodeContent = scanner.nextLine().trim();
        Node<String> firstNode = new Node<>(firstNodeContent);
        Node<String> secondNode = new Node<>(secondNodeContent);
        if (!(graph.getNodes().contains(firstNode))) {
            System.out.println("Узла с именем " + firstNodeContent + " не существует");
            return;
        }
        if (!(graph.getNodes().contains(secondNode))) {
            System.out.println("Узла с именем " + secondNodeContent + " не существует");
            return;
        }
        System.out.println("Связь " + firstNodeContent + " и " + secondNodeContent + " удалена");
        graph.removeEdge(firstNode, secondNode);
    }

    private void showEdges() {
        var allEdges = graph.getAllEdges();
        if (allEdges.isEmpty()) {
            System.out.println("Связей пока нет");
            return;
        }
        System.out.println("Список всех ребер:");
        for (var e : allEdges) {
            System.out.print(e.getStartNode() + "-----" + e.getEndNode());
            if (graph.isWeighted()) {
                System.out.println(" вес: " + e.getWeight());
            } else {
                System.out.println();
            }
        }
    }

    private void showAllNodes() {
        System.out.println("Все узлы в графе: ");
        for (var node : graph.getNodes()) {
            System.out.println(node);
        }
    }

    private void changeGraph() {
        if (graphs.keySet().isEmpty()) {
            System.out.println("Нет доступных графов");
            return;
        }
        System.out.println("Доступные графы");
        for (String name : graphs.keySet()) {
            System.out.println(name);
        }
        System.out.println("Введите название графа для переключения или exit для выхода");
        String name = scanner.nextLine().trim();
        while (true) {
            if (name.equals("exit")) {
                return;
            } else {
                if (graphs.containsKey(name)) {
                    this.graph = graphs.get(name);
                    this.currentGraphName = name;
                    return;
                } else {
                    System.out.println("Такого графа не существует");
                }
            }
            name = scanner.nextLine().trim();
        }
    }

    private void setCurrentGraphName(String name) {
        currentGraphName = name;
    }

    private void showIsolatedNodesInDirectedGraph() {
        if (!(graph instanceof DirectedGraph<String>)) {
            System.out.println("Граф не ориентированный");
            return;
        } else {
            for (Node<String> node : ((DirectedGraph<String>) graph).getIsolationNodes()) {
                System.out.println(node);
            }
        }
    }

    private void showMutualNodesInDirectedGraph() {
        if (!(graph instanceof DirectedGraph<String>)) {
            System.out.println("Граф не ориентированный");
            return;
        } else {
            for (Set<Node<String>> set : ((DirectedGraph<String>) graph).getMutualNodes()) {
                String pairString = set.stream().map(Node::toString).collect(Collectors.joining("<->"));
                System.out.println(pairString);
            }
        }
    }

    private void showSharedNeighbor() {
        System.out.println("Введите первую вершину");
        String content1 = scanner.nextLine().trim();
//        if (graph.hasNode(content1)) {
//            System.out.println("Нет такого узла в графе");
//            return;
//        }
        System.out.println("Введите вторую вершину");
        String content2 = scanner.nextLine().trim();
//        if (graph.hasNode(content2)) {
//            System.out.println("Нет такого узла в графе");
//            return;
//        }
        Set<Node<String>> result = graph.getSharedNeighbor(new Node<>(content1), new Node<>(content2));
        if (result.isEmpty()) {
            System.out.println("Нет общей вершины");
        } else {
            System.out.println("Общие вершины: ");
            for (Node<String> node : result) {
                System.out.println(node);
            }
        }
    }

    private void intersection() {
        try {
            System.out.println("Введите название первого графа");
            Graph<String> first = graphs.get(scanner.nextLine().trim());
            System.out.println("Введите название первого графа");
            Graph<String> second = graphs.get(scanner.nextLine().trim());
            Graph<String> result = Graph.graphIntersection(first, second);
            System.out.println("Введите имя нового графа");
            String name = scanner.nextLine().trim();
            graphs.put(name, result);
            System.out.println("Граф создан");


        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            System.out.println("Графы разных классов");
        }
    }

    private void paths() {
        System.out.println("Введите первый узел");
        String startContent = scanner.nextLine().trim();
        if (!graph.hasNode(startContent)) {
            System.out.println("Такого узла нет в графе");
            return;
        }
        System.out.println("Введите второй узел");
        String endContent = scanner.nextLine().trim();
        if (!graph.hasNode(endContent)) {
            System.out.println("Такого узла нет в графе");
            return;
        }
        Node<String> start = new Node<>(startContent);
        Node<String> end = new Node<>(endContent);
        int result = graph.countPaths(start, end);
        System.out.println(result);
    }
}



