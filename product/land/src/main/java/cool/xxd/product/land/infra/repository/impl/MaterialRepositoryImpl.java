package cool.xxd.product.land.infra.repository.impl;

import cool.xxd.product.land.domain.aggregate.Material;
import cool.xxd.product.land.domain.repository.MaterialRepository;
import cool.xxd.product.land.infra.converter.MaterialConverter;
import cool.xxd.product.land.infra.mapper.MaterialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MaterialRepositoryImpl implements MaterialRepository {

    private final MaterialMapper materialMapper;

    @Override
    public void saveAll(List<Material> materials) {
        if (materials.isEmpty()) {
            return;
        }
        var materialDOList = MaterialConverter.INSTANCE.toTargetList(materials);
        materialMapper.insert(materialDOList);
    }
}
