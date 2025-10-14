package com.kbsearch.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EmbeddingService {

    // Dummy embedding generator (replace with actual model if needed)
    public List<Float> embedText(String text) {
        Random random = new Random(text.hashCode());
        List<Float> embedding = new ArrayList<>();
        for (int i = 0; i < 384; i++) {
            embedding.add(random.nextFloat());
        }
        return embedding;
    }

    public double cosineSim(List<Float> v1, List<Float> v2) {
        double dot = 0.0, normA = 0.0, normB = 0.0;
        for (int i = 0; i < v1.size(); i++) {
            dot += v1.get(i) * v2.get(i);
            normA += Math.pow(v1.get(i), 2);
            normB += Math.pow(v2.get(i), 2);
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    // ✅ New method for controller
    public List<float[]> generateEmbeddings(List<String> chunks) {
        List<float[]> embeddings = new ArrayList<>();
        for (String chunk : chunks) {
            List<Float> embList = embedText(chunk);
            float[] arr = new float[embList.size()];
            for (int i = 0; i < embList.size(); i++) {
                arr[i] = embList.get(i);
            }
            embeddings.add(arr);
        }
        return embeddings;
    }
}
