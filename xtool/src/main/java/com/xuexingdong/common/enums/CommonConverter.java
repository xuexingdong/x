package cool.xxd.mapstruct.enums;

import cool.xxd.mapstruct.mapper.BaseDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CommonConverter<Entity, DataObject ddextends BaseDO> {

    DataObject domain2do(Entity entity);

    List<DataObject> domain2do(List<Entity> entities);
}
