package com.lab.graph;

import java.util.List;

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
}
