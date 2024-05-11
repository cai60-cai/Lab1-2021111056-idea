package com.lab.graph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PathFinder {
    private final Graph graph;

    public PathFinder(Graph graph) {
        this.graph = graph;
    }

    public Map<String, List<List<String>>> findAllShortestPaths(String start) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, List<String>> predecessors = new HashMap<>();
        Map<String, List<List<String>>> allPaths = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        graph.getAdjacencyList().keySet().forEach(vertex -> {
            distances.put(vertex, Integer.MAX_VALUE);
            predecessors.put(vertex, new ArrayList<>());
        });

        distances.put(start, 0);
        queue.add(new AbstractMap.SimpleEntry<>(start, 0));

        while (!queue.isEmpty()) {
            String current = queue.poll().getKey();
            if (current == null || !distances.containsKey(current)) {
                continue;
            }

            int currentDistance = distances.get(current);
            Map<String, Integer> neighbors = graph.getAdjacencyList().getOrDefault(current, Collections.emptyMap());

            for (Map.Entry<String, Integer> neighborEntry : neighbors.entrySet()) {
                String neighbor = neighborEntry.getKey();
                if (!distances.containsKey(neighbor)) {
                    continue;
                }

                int weight = neighborEntry.getValue();
                int newDistance = (currentDistance == Integer.MAX_VALUE) ? Integer.MAX_VALUE : currentDistance + weight;

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    predecessors.get(neighbor).clear();
                    predecessors.get(neighbor).add(current);
                    queue.add(new AbstractMap.SimpleEntry<>(neighbor, newDistance));
                } else if (newDistance == distances.get(neighbor)) {
                    predecessors.get(neighbor).add(current);
                }
            }
        }

        for (String vertex : graph.getAdjacencyList().keySet()) {
            allPaths.put(vertex, buildPaths(predecessors, vertex));
        }

        return allPaths;
    }

    private List<List<String>> buildPaths(Map<String, List<String>> predecessors, String target) {
        List<List<String>> paths = new ArrayList<>();
        if (target == null || predecessors.get(target).isEmpty()) {
            paths.add(Collections.singletonList(target));
            return paths;
        }
        for (String pred : predecessors.get(target)) {
            List<List<String>> subPaths = buildPaths(predecessors, pred);
            for (List<String> subPath : subPaths) {
                List<String> path = new ArrayList<>(subPath);
                path.add(target);
                paths.add(path);
            }
        }
        return paths;
    }

    public List<List<String>> findShortestPath(String start, String end) {
        Map<String, List<List<String>>> allPaths = findAllShortestPaths(start);
        return allPaths.getOrDefault(end, Collections.emptyList());
    }

    public static int calculatePathWeight(Graph graph, List<String> path) {
        int totalWeight = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String current = path.get(i);
            String next = path.get(i + 1);
            int weight = graph.getWeight(current, next); // 获取当前边的权值
            totalWeight += weight;
        }
        return totalWeight;
    }

    public void generateGraphvizFile(String folderName, String filename, List<List<String>> allShortestPaths) throws IOException {
        FileWriter writer = new FileWriter(folderName + "/" + filename);
        writer.write("digraph G {\n");

        // 用于存储所有最短路径中的边
        Set<String> shortestPathEdges = new HashSet<>();
        for (List<String> path : allShortestPaths) {
            for (int i = 0; i < path.size() - 1; i++) {
                shortestPathEdges.add(path.get(i) + " -> " + path.get(i + 1));
            }
        }

        // 添加所有节点和边，跳过最短路径中的边
        for (String vertex : graph.getAdjacencyList().keySet()) {
            writer.write("    " + vertex + ";\n");
            Map<String, Integer> neighbors = graph.getAdjacencyList().get(vertex);
            for (String neighbor : neighbors.keySet()) {
                String edge = vertex + " -> " + neighbor;
                if (!shortestPathEdges.contains(edge)) { // 只添加不是最短路径的边
                    writer.write("    " + edge + ";\n");
                }
            }
        }

        int colorIndex = 0; // 为了确保每个路径有不同的颜色，可以使用索引
        for (List<String> path : allShortestPaths) {
            String color = getRandomColor(colorIndex); // 为路径选择随机颜色，传入索引以确保颜色不同
            colorIndex+=100;
            writer.write("    { ");
            writer.write("edge [color=\"" + color + "\"]; ");
            for (int i = 0; i < path.size() - 1; i++) {
                writer.write(path.get(i) + " -> " + path.get(i + 1) + "; ");
            }
            writer.write("}\n");
        }


        writer.write("}\n");
        writer.close();
    }

    private String getRandomColor(int colorIndex) {
        Random random = new Random(colorIndex);
        int r = random.nextInt(200) + 55;
        int g = random.nextInt(200) + 55;
        int b = random.nextInt(200) + 55;
        return String.format("#%02x%02x%02x", r, g, b);
    }




}
//////这里是B1修改呀
////这里是B1修改呀
//这里是第一次修改呀
////这里是B2修改呀
//这里是第二次修改2.0
//这里是第二次修改2.0
//这是C4的修改
//这是C4的修改
//这是C4的修改
//这是C4的修改
//这是C4的修改
//这是C4的修改
//这是C4的修改
//这是C4的修改
