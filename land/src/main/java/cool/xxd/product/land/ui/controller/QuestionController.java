package cool.xxd.product.land.ui.controller;

import cool.xxd.product.land.application.service.QuestionService;
import cool.xxd.product.land.ui.converter.QuestionConverter;
import cool.xxd.product.land.ui.vo.QuestionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/questions")
@Tag(name = "题目接口")
@RequiredArgsConstructor
@Validated
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "查询成语关联的题目")
    @GetMapping("/listByIdiom")
    public ResponseEntity<List<QuestionVO>> listByIdiom(@NotNull @RequestParam("idiom") String idiom) {
        var questions = questionService.search(idiom);
        var questionVOList = QuestionConverter.INSTANCE.toTargetList(questions);
        return ResponseEntity.ok(questionVOList);
    }
}
