package com.lab.graph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RandomWalk {
    private Graph graph;
    private volatile boolean stopRequested = false;

    public RandomWalk(Graph graph) {
        this.graph = graph;
    }

    public void requestStop() {
        stopRequested = true;
    }

    public void performRandomWalk() throws IOException {
        Random random = new Random();
        List<String> nodes = new ArrayList<>(graph.getAdjacencyList().keySet());
        if (nodes.isEmpty()) {
            System.out.println("图为空。");
            return;
        }
        String current = nodes.get(random.nextInt(nodes.size()));
        Set<String> visitedEdges = new HashSet<>();
        List<String> path = new ArrayList<>();
        path.add(current);

        try (FileWriter writer = new FileWriter("random_walk_output.txt")) {
            writer.write(current + " ");  // 将起始节点写入文件

            while (!stopRequested) {
                if (!graph.getAdjacencyList().containsKey(current) || graph.getAdjacencyList().get(current).isEmpty()) {
                    System.out.println("当前节点没有出边，游走停止。");
                    break;  // 如果当前节点没有出边，则停止
                }
                List<String> possibleNodes = new ArrayList<>(graph.getAdjacencyList().get(current).keySet());
                String next = possibleNodes.get(random.nextInt(possibleNodes.size()));
                String edge = current + " -> " + next;
                if (!visitedEdges.add(edge)) {  // 检查第一次重复的边
                    writer.write(next + " ");  // 即使是重复边也记录下一个节点
                    System.out.println("出现重复边，游走停止。");
                    break;
                }
                writer.write(next + " ");  // 将下一个节点写入文件
                current = next;
            }
        }
        System.out.println("Random walk completed and saved to random_walk_output.txt.");
    }
}
//////这里是B1修改呀////这里是B1修改呀
////这里是第一次修改呀
////这里是B2修改呀////这里是B2修改呀////这里是B2修改呀
//这里是第二次修改2.0
//这里是第二次修改2.0
//这是B2的修改

