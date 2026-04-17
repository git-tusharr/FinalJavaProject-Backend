package com.example.controller;

import com.example.dto.*;
import com.example.service.ProductQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/questions")
@RequiredArgsConstructor
public class ProductQnAController {

    private final ProductQuestionService service;

    // CUSTOMER: ASK QUESTION
    @PostMapping
    public String askQuestion(
            @PathVariable Long productId,
            @RequestBody QuestionRequestDto dto
    ) {
        service.askQuestion(productId, dto);
        return "Question submitted";
    }

    // ADMIN: ANSWER QUESTION
    @PostMapping("/{questionId}/answer")
    public String answerQuestion(
            @PathVariable Long questionId,
            @RequestBody AnswerRequestDto dto
    ) {
        service.answerQuestion(questionId, dto);
        return "Answer submitted";
    }

    // PUBLIC: VIEW Q&A
    @GetMapping
    public List<QuestionResponseDto> getQnA(
            @PathVariable Long productId
    ) {
        return service.getQnA(productId);
    }
}
