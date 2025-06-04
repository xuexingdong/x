package cool.xxd.product.msw.adapter.in.mcp;

import cool.xxd.infra.mapstruct.CommonEnumMapper;
import cool.xxd.product.msw.application.dto.response.MobQueryResponse;
import cool.xxd.product.msw.application.dto.response.MobResponse;
import cool.xxd.product.msw.domain.aggregate.Mob;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonEnumMapper.class})
public interface MobConverter {
    MobConverter INSTANCE = Mappers.getMapper(MobConverter.class);

    MobResponse domain2response(Mob mob);

    List<MobResponse> domain2response(List<Mob> mobs);

    MobQueryResponse domain2queryResponse(Mob mob);

    List<MobQueryResponse> domain2queryResponse(List<Mob> mobs);
}

