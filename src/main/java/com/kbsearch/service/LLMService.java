package com.kbsearch.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class LLMService {
    private static final String API_URL = "https://api-inference.huggingface.co/models/google/flan-t5-large";
    private static final String API_KEY = System.getenv("HF_API_TOKEN");
    private final RestTemplate rest = new RestTemplate();

    public String synthesize(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> req = Map.of(
                    "inputs", prompt,
                    "parameters", Map.of("max_new_tokens", 200)
            );

            HttpEntity<Map<String, Object>> ent = new HttpEntity<>(req, headers);
            ResponseEntity<List> res = rest.postForEntity(API_URL, ent, List.class);

            if (res.getBody() == null || res.getBody().isEmpty()) {
                return "No response from model.";
            }

            Map out = (Map) res.getBody().get(0);
            Object gen = out.get("generated_text");
            return gen != null ? gen.toString() : "No generated text.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error contacting LLM API: " + e.getMessage();
        }
    }
}
