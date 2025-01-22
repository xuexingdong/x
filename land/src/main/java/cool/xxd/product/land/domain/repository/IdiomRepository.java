package cool.xxd.product.land.domain.repository;

import cool.xxd.infra.ddd.BaseRepository;
import cool.xxd.product.land.domain.aggregate.Idiom;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IdiomRepository extends BaseRepository<Idiom, Long> {
    Optional<Idiom> findByWord(String word);

    List<Idiom> findByWords(Collection<String> words);
}
