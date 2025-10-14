package com.kbsearch.service;

import com.kbsearch.model.Chunk;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetrievalService {

    private final List<Chunk> chunks = new ArrayList<>();
    private final EmbeddingService embeddingService;

    public RetrievalService(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    public void addChunk(Chunk chunk) {
        chunks.add(chunk);
    }

    public String query(String userQuery) {
        if (chunks.isEmpty()) return "No documents ingested yet.";

        List<Float> queryEmbedding = embeddingService.embedText(userQuery);

        Chunk bestChunk = null;
        double maxSim = -1;
        for (Chunk c : chunks) {
            double sim = embeddingService.cosineSim(c.getEmbedding(), queryEmbedding);
            if (sim > maxSim) {
                maxSim = sim;
                bestChunk = c;
            }
        }

        if (bestChunk == null) return "No relevant results found.";

        // --- extract key sentences related to the query ---
        String[] sentences = bestChunk.getText().split("[.!?]\\s*");
        StringBuilder summary = new StringBuilder();
        String lowerQuery = userQuery.toLowerCase();

        for (String s : sentences) {
            String ls = s.toLowerCase();
            if (ls.contains("project") || ls.contains("develop") || ls.contains("machine learning")
                    || ls.contains("ai") || ls.contains("yolo") || ls.contains("system")) {
                summary.append(s.trim()).append(". ");
            }
        }

        String cleanSummary = summary.toString().trim();
        if (cleanSummary.isEmpty()) {
            cleanSummary = bestChunk.getText().substring(0, Math.min(300, bestChunk.getText().length())) + "...";
        }

        // Final polish
        return "Answer: " + cleanSummary.replaceAll("\\s+", " ");
    }

    // ✅ New method for controller
    public void storeEmbeddings(List<String> chunkTexts, List<float[]> embeddings) {
        for (int i = 0; i < chunkTexts.size(); i++) {
            // Convert float[] to List<Float>
            float[] arr = embeddings.get(i);
            List<Float> embList = new ArrayList<>();
            for (float f : arr) embList.add(f);

            // Assign incremental ID
            Chunk chunk = new Chunk(i + 1, chunkTexts.get(i), embList);
            addChunk(chunk);
        }
    }
}
