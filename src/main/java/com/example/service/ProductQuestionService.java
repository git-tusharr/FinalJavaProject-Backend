package com.example.service;

import com.example.dto.*;
import com.example.model.*;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQuestionService {

    private final ProductQuestionRepository questionRepo;
    private final ProductAnswerRepository answerRepo;

    // ASK QUESTION
    public void askQuestion(Long productId, QuestionRequestDto dto) {

        ProductQuestion q = new ProductQuestion();
        q.setProductId(productId);
        q.setQuestion(dto.getQuestion());
        q.setIsAnswered(false);

        questionRepo.save(q);
    }

    // ANSWER QUESTION (ADMIN)
    public void answerQuestion(Long questionId, AnswerRequestDto dto) {

        ProductAnswer ans = new ProductAnswer();
        ans.setQuestionId(questionId);
        ans.setAnswer(dto.getAnswer());

        answerRepo.save(ans);

        ProductQuestion q = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        q.setIsAnswered(true);
        questionRepo.save(q);
    }

    // GET ALL Q&A FOR PRODUCT
    public List<QuestionResponseDto> getQnA(Long productId) {

        List<ProductQuestion> questions =
                questionRepo.findByProductId(productId);

        List<QuestionResponseDto> response = new ArrayList<>();

        for (ProductQuestion q : questions) {

            QuestionResponseDto dto = new QuestionResponseDto();
            dto.setId(q.getId());
            dto.setQuestion(q.getQuestion());

            answerRepo.findByQuestionId(q.getId())
                    .ifPresent(a -> dto.setAnswer(a.getAnswer()));

            response.add(dto);
        }

        return response;
    }
}
