package com.kbsearch.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kbsearch.service.PDFExtractor;
import com.kbsearch.service.EmbeddingService;
import com.kbsearch.service.RetrievalService;
import com.kbsearch.model.Chunk;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ingest")
public class IngestController {

    private final PDFExtractor pdfExtractor;
    private final EmbeddingService embeddingService;
    private final RetrievalService retrievalService;

    // Constructor injection
    public IngestController(PDFExtractor pdfExtractor,
                            EmbeddingService embeddingService,
                            RetrievalService retrievalService) {
        this.pdfExtractor = pdfExtractor;
        this.embeddingService = embeddingService;
        this.retrievalService = retrievalService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> ingestPDF(@RequestParam("file") MultipartFile file) {
        try {
            // Extract text directly from MultipartFile
            String extractedText = pdfExtractor.extractText(file);

            // Split text into chunks
            List<String> chunks = chunkText(extractedText);

            // Generate embeddings
            List<float[]> embeddings = embeddingService.generateEmbeddings(chunks);

            // Store embeddings
            retrievalService.storeEmbeddings(chunks, embeddings);

            return ResponseEntity.ok("✅ PDF Ingested Successfully: " + chunks.size() + " chunks created.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Ingestion failed: " + e.getMessage());
        }
    }

    private List<String> chunkText(String text) {
        int chunkSize = 500;
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < text.length(); i += chunkSize) {
            chunks.add(text.substring(i, Math.min(i + chunkSize, text.length())));
        }
        return chunks;
    }
}
