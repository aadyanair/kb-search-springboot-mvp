package com.kbsearch.model;

import java.util.List;

public class Chunk {
    private int id;
    private String text;
    private List<Float> embedding;

    // Default constructor
    public Chunk() {}

    // Full constructor with ID
    public Chunk(int id, String text, List<Float> embedding) {
        this.id = id;
        this.text = text;
        this.embedding = embedding;
    }

    // New convenient constructor without ID
    public Chunk(String text, List<Float> embedding) {
        this.text = text;
        this.embedding = embedding;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Float> getEmbedding() {
        return embedding;
    }

    // Optional setters if needed
    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEmbedding(List<Float> embedding) {
        this.embedding = embedding;
    }
}
