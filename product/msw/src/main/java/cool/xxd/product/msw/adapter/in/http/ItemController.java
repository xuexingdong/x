package cool.xxd.product.msw.adapter.in.http;

import cool.xxd.infra.http.Response;
import cool.xxd.product.msw.application.dto.request.BatchAddItemRequest;
import cool.xxd.product.msw.application.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("addItems")
    public Response<Void> addMobs(@Validated @RequestBody BatchAddItemRequest batchAddItemRequest) {
        var addItemCommands = ItemConverter.INSTANCE.request2command(batchAddItemRequest.getItems());
        itemService.addItems(addItemCommands);
        return Response.ok();
    }
}
