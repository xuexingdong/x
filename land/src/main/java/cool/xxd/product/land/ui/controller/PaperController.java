package cool.xxd.product.land.ui.controller;

import cool.xxd.product.land.domain.command.handler.AddPaperCommandHandler;
import cool.xxd.product.land.ui.converter.PaperConverter;
import cool.xxd.product.land.ui.request.AddPaperRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/papers")
@Tag(name = "试卷接口")
@RequiredArgsConstructor
@Validated
public class PaperController {

    private final AddPaperCommandHandler addPaperCommandHandler;

    @Operation(summary = "添加试卷")
    @PostMapping("/add")
    public ResponseEntity<Void> addPaper(@Valid @RequestBody AddPaperRequest addPaperRequest) {
        var addPaperCommand = PaperConverter.INSTANCE.request2command(addPaperRequest);
        addPaperCommandHandler.handle(addPaperCommand);
        return ResponseEntity.ok().build();
    }
}
