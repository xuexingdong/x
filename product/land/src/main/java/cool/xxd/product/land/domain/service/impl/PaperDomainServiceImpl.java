package cool.xxd.product.land.domain.service.impl;

import cool.xxd.product.land.domain.command.AddPaperCommand;
import cool.xxd.product.land.domain.factory.MaterialFactory;
import cool.xxd.product.land.domain.factory.PaperFactory;
import cool.xxd.product.land.domain.factory.QuestionFactory;
import cool.xxd.product.land.domain.repository.MaterialRepository;
import cool.xxd.product.land.domain.repository.PaperRepository;
import cool.xxd.product.land.domain.repository.QuestionRepository;
import cool.xxd.product.land.domain.service.PaperDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaperDomainServiceImpl implements PaperDomainService {

    private final PaperFactory paperFactory;
    private final PaperRepository paperRepository;

    private final MaterialFactory materialFactory;
    private final MaterialRepository materialRepository;

    private final QuestionFactory questionFactory;
    private final QuestionRepository questionRepository;

    @Transactional
    @Override
    public void addPaper(AddPaperCommand addPaperCommand) {
        var paperOptional = paperRepository.findByOutPaperId(addPaperCommand.getOutPaperId());
        if (paperOptional.isPresent()) {
            log.info("outPaperId-{} already exists", addPaperCommand.getOutPaperId());
            return;
        }
        var paper = paperFactory.create(addPaperCommand);
        paperRepository.save(paper);

        var materials = materialFactory.create(paper, addPaperCommand);
        materialRepository.saveAll(materials);

        var questions = questionFactory.create(paper, materials, addPaperCommand);
        questionRepository.saveAll(questions);
    }
}
