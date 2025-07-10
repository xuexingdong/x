package cool.xxd.product.msw.adapter.in.http;

import cool.xxd.infra.http.Response;
import cool.xxd.product.msw.application.dto.request.BatchAddMobRequest;
import cool.xxd.product.msw.application.service.MobService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mobItem")
@RequiredArgsConstructor
public class MobItemController {

    private final MobService mobService;

    @PostMapping("addMobs")
    public Response<Void> addMobs(@Validated @RequestBody BatchAddMobRequest batchAddMobRequest) {
        var addMobCommands = MobConverter.INSTANCE.request2command(batchAddMobRequest.getMobs());
        mobService.addMobs(addMobCommands);
        return Response.ok();
    }
}
