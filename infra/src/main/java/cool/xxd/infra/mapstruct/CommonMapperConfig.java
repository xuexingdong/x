package cool.xxd.infra.mapstruct;


import org.mapstruct.MapperConfig;

@MapperConfig(uses = {
        CommonEnumMapper.class,
        ListStringMapper.class
})
public interface CommonMapperConfig {
}
