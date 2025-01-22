package cool.xxd.product.land.ui.controller;

import cool.xxd.product.land.application.service.IdiomService;
import cool.xxd.product.land.application.service.QuestionService;
import cool.xxd.product.land.domain.command.handler.AddIdiomCommandHandler;
import cool.xxd.product.land.ui.converter.IdiomConverter;
import cool.xxd.product.land.ui.request.AddIdiomRequest;
import cool.xxd.product.land.ui.vo.IdiomCardVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/idioms")
@Tag(name = "成语接口")
@RequiredArgsConstructor
@Validated
public class IdiomController {

    private final IdiomService idiomService;
    private final AddIdiomCommandHandler addIdiomCommandHandler;
    private final QuestionService questionService;

    @Operation(summary = "添加成语")
    @PostMapping("/add")
    public ResponseEntity<Void> addIdiom(@Valid @RequestBody AddIdiomRequest addIdiomRequest) {
        var addIdiomCommand = IdiomConverter.INSTANCE.request2command(addIdiomRequest);
        addIdiomCommandHandler.handle(addIdiomCommand);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "搜索成语")
    @GetMapping("/search")
    public ResponseEntity<IdiomCardVO> search(@NotNull @RequestParam("keyword") String keyword) {
        var idiomOptional = idiomService.search(keyword);
        if (idiomOptional.isPresent()) {
            var idiom = idiomOptional.get();
            var relatedQuestionCount = questionService.countByIdiom(idiom);
            var idiomCardVO = IdiomConverter.INSTANCE.toVO(idiom);
            idiomCardVO.setEmotionText(idiom.getEmotion().getName());
            idiomCardVO.setRelatedQuestionCount(relatedQuestionCount);
            return ResponseEntity.ok(idiomCardVO);
        }
        return ResponseEntity.ok().build();
    }
}
