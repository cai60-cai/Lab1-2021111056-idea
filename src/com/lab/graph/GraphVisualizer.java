package com.lab.graph;

import java.io.*;
import java.util.*;

public class GraphVisualizer {
    public static void showDirectedGraph(Graph graph, String outputFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(outputFile)) {
            // 添加图的声明
            out.println("digraph G {");

            // 输出边的描述
            for (Map.Entry<String, Map<String, Integer>> entry : graph.getAdjacencyList().entrySet()) {
                for (Map.Entry<String, Integer> edge : entry.getValue().entrySet()) {
                    out.println(entry.getKey() + " -> " + edge.getKey() + " [label=" + edge.getValue() + "]");
                }
            }

            // 结束图的声明
            out.println("}");
        }
    }

    public static void renderGraph(String dotFile, String outputFile) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tpng", dotFile, "-o", outputFile);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Graphviz did not process correctly.");
        }
    }
}
