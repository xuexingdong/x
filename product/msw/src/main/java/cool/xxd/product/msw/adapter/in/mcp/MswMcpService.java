package cool.xxd.product.msw.adapter.in.mcp;

import cool.xxd.product.msw.application.dto.request.QueryItemRequest;
import cool.xxd.product.msw.application.dto.request.QueryMobRequest;
import cool.xxd.product.msw.application.dto.response.ItemQueryResponse;
import cool.xxd.product.msw.application.dto.response.MobQueryResponse;
import cool.xxd.product.msw.application.service.ItemService;
import cool.xxd.product.msw.application.service.MobService;
import cool.xxd.product.msw.domain.query.ItemQuery;
import cool.xxd.product.msw.domain.query.MobQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MswMcpService {

    private final MobService mobService;

    private final ItemService itemService;

    @Tool(description = "查询怪物")
    public List<MobQueryResponse> queryMobs(QueryMobRequest queryMobRequest) {
        var mobQuery = new MobQuery();
        mobQuery.setName(queryMobRequest.getName());
        var mobs = mobService.queryMobs(mobQuery);
        var mobItemsMap = mobService.matchItems(mobs);
        return mobs.stream()
                .map(mob -> {
                    var mobQueryResponse = MobConverter.INSTANCE.domain2queryResponse(mob);
                    mobQueryResponse.setItems(ItemConverter.INSTANCE.domain2response(mobItemsMap.getOrDefault(mob.getCode(), List.of())));
                    return mobQueryResponse;
                })
                .toList();
    }

    @Tool(description = "查询物品")
    public List<ItemQueryResponse> queryItems(QueryItemRequest queryItemRequest) {
        var itemQuery = new ItemQuery();
        itemQuery.setName(queryItemRequest.getName());
        var items = itemService.queryItems(itemQuery);
        var itemMobsMap = itemService.matchMobs(items);
        return items.stream()
                .map(item -> {
                    var itemQueryResponse = ItemConverter.INSTANCE.domain2queryResponse(item);
                    itemQueryResponse.setMobs(MobConverter.INSTANCE.domain2response(itemMobsMap.getOrDefault(item.getCode(), List.of())));
                    return itemQueryResponse;
                })
                .toList();
    }
}
