package cool.xxd.product.land.ui.controller;

import cool.xxd.product.land.application.service.QuestionService;
import cool.xxd.product.land.domain.aggregate.Paper;
import cool.xxd.product.land.domain.aggregate.Question;
import cool.xxd.product.land.domain.repository.PaperRepository;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/questions")
@Tag(name = "题目接口")
@RequiredArgsConstructor
@Validated
public class QuestionController {

    private final QuestionService questionService;
    private final PaperRepository paperRepository;

    @Operation(summary = "查询成语关联的题目")
    @GetMapping("/listByIdiom")
    public ResponseEntity<List<QuestionVO>> listByIdiom(@NotNull @RequestParam("idiom") String idiom) {
        var questions = questionService.search(idiom);
        var paperIds = questions.stream()
                .map(Question::getPaperId).distinct().toList();
        var paperNameMap = paperRepository.findByPaperIds(paperIds).stream()
                .collect(Collectors.toMap(Paper::getId, Paper::getName));
        var questionVOList = QuestionConverter.INSTANCE.toTargetList(questions);
        for (var questionVO : questionVOList) {
            questionVO.setPaperName(paperNameMap.get(questionVO.getPaperId()));
        }
        return ResponseEntity.ok(questionVOList);
    }
}
