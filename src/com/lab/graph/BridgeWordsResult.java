package com.lab.graph;

import java.util.List;
import java.util.Random;

public class BridgeWordsResult {
    private final List<String> bridgeWords;
    private final String errorMessage;

    public BridgeWordsResult(List<String> bridgeWords, String errorMessage) {
        this.bridgeWords = bridgeWords;
        this.errorMessage = errorMessage;
    }

    public List<String> getBridgeWords() {
        return bridgeWords;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasError() {
        return errorMessage != null && !errorMessage.isEmpty();
    }

    public static String getRandomBridgeWord(Graph graph, String word1, String word2) {
        BridgeWordsResult result = graph.queryBridgeWords(word1, word2);
        if (result == null || result.hasError() || result.getBridgeWords() == null || result.getBridgeWords().isEmpty()) {
            return null;  // 检查是否有错误或者桥接词列表是否为空
        }
        List<String> bridges = result.getBridgeWords();  // 获取桥接词列表
        return bridges.get(new Random().nextInt(bridges.size()));  // 随机选择一个桥接词
    }
}
