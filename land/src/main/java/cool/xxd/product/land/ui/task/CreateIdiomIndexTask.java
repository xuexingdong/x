package cool.xxd.product.land.ui.task;

import cool.xxd.product.land.application.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateIdiomIndexTask {

    private final QuestionService questionService;

    @Scheduled(fixedRate = 60 * 1000)
    public void queryDepositOrderStatus() {
        questionService.indexIdioms();
    }
}
