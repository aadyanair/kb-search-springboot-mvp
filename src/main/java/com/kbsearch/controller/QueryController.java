package com.kbsearch.controller;

import com.kbsearch.service.RetrievalService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/query")
@CrossOrigin
public class QueryController {

    private final RetrievalService retrievalService;

    public QueryController(RetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    @PostMapping
    public Map<String, String> query(@RequestBody Map<String, String> request) {
        String userQuery = request.get("query");
        String answer = retrievalService.query(userQuery);
        return Map.of("response", answer);
    }
}
