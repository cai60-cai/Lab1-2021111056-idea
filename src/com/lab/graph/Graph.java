package com.lab.graph;

import java.util.*;



public class Graph {
    private Map<String, Map<String, Integer>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(String src, String dest) {
        adjacencyList.putIfAbsent(src, new HashMap<>());
        Map<String, Integer> edges = adjacencyList.get(src);
        edges.put(dest, edges.getOrDefault(dest, 0) + 1);
    }

    public BridgeWordsResult queryBridgeWords(String word1, String word2) {
        if (!adjacencyList.containsKey(word1) && !adjacencyList.containsKey(word2)) {
            return new BridgeWordsResult(null, "Neither '" + word1 + "' nor '" + word2 + "' is in the graph.");
        }
        if (!adjacencyList.containsKey(word1)) {
            return new BridgeWordsResult(null, "'" + word1 + "' is not in the graph.");
        }
        if (!adjacencyList.containsKey(word2)) {
            return new BridgeWordsResult(null, "'" + word2 + "' is not in the graph.");
        }

        List<String> bridgeWords = new ArrayList<>();
        Map<String, Integer> potentialBridges = adjacencyList.get(word1);
        for (String candidate : potentialBridges.keySet()) {
            if (adjacencyList.containsKey(candidate) && adjacencyList.get(candidate).containsKey(word2)) {
                bridgeWords.add(candidate);
            }
        }

        return new BridgeWordsResult(bridgeWords, null);
    }

    public Map<String, Map<String, Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getWeight(String current, String next) {
        if (adjacencyList.containsKey(current) && adjacencyList.get(current).containsKey(next)) {
            return adjacencyList.get(current).get(next);
        }
        // 如果没有找到边或权重，则返回一个默认值（例如 -1）
        return -1;
    }

}
//这里是第一次修改呀
////这里是第二次修改2.0
//dsdsdsd
//这里是第二次修改2.0
//这里是第二次修改2.0
////这是C4的修改
//这是C4的修改
//这是C4的修改
//这是C4的修改
//这是C4的修改

//这是B2的修改//这是B2的修改//这是B2的修改
//这是B2的修改


//这是IDEA的修改//这是idea修改//这是idea的修改//这是IDEA的修改//这是idea修改//这是idea的修改//这是IDEA的修改//这是idea修改
// 这是idea的修改//这是IDEA的修改//这是idea修改//这是idea的修改//这是IDEA的修改//这是idea修改//这是idea的修改

