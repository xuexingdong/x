package cool.xxd.product.databox.ui.http;

import cool.xxd.infra.X;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskDataController {

    @PostMapping("submitTask")
    public void submitTask(@RequestBody SubmitTaskRequest submitTaskRequest) {
    }
}
