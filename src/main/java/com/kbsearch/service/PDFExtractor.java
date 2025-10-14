package com.kbsearch.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

@Service
public class PDFExtractor {

    // Extract text from a java.io.File
    public String extractText(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    // Extract text directly from MultipartFile
    public String extractText(MultipartFile multipartFile) throws IOException {
        // Convert MultipartFile to temp File
        File tempFile = File.createTempFile("upload-", ".pdf");
        multipartFile.transferTo(tempFile);
        String text = extractText(tempFile);
        tempFile.delete(); // cleanup temp file
        return text;
    }

    // Optional method to ingest by file path
    public String ingestDocument(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return "File not found: " + filePath;
            }
            String content = extractText(file);
            return "Document ingested successfully. Length: " + content.length();
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }
}
