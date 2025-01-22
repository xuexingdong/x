package cool.xxd.product.land.application.service;

import cool.xxd.product.land.domain.aggregate.Idiom;
import cool.xxd.product.land.domain.aggregate.Question;

import java.util.List;

public interface QuestionService {

    List<Question> search(String idiom);

    void indexIdioms();

    Integer countByIdiom(Idiom idiom);
}
