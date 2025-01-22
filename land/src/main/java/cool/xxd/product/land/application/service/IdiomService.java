package cool.xxd.product.land.application.service;

import cool.xxd.product.land.domain.aggregate.Idiom;

import java.util.Optional;

public interface IdiomService {
    Optional<Idiom> search(String keyword);
}
