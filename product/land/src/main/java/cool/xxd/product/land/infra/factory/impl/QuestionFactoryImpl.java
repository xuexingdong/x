package cool.xxd.product.land.infra.factory.impl;

import cool.xxd.infra.X;
import cool.xxd.product.land.domain.aggregate.Material;
import cool.xxd.product.land.domain.aggregate.Paper;
import cool.xxd.product.land.domain.aggregate.Question;
import cool.xxd.product.land.domain.command.AddPaperCommand;
import cool.xxd.product.land.domain.enums.XingceModule;
import cool.xxd.product.land.domain.factory.QuestionFactory;
import cool.xxd.product.land.infra.model.QuestionDO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestionFactoryImpl implements QuestionFactory {

    @Override
    public List<Question> create(Paper paper, List<Material> materials, AddPaperCommand addPaperCommand) {
        if (addPaperCommand.getQuestions() == null) {
            return List.of();
        }
        var outQuestionIdChapterNameDict = getQuestionChapterNameDict(addPaperCommand);
        return addPaperCommand.getQuestions().stream().map(addQuestionCommand -> {
            var chapterName = outQuestionIdChapterNameDict.get(addQuestionCommand.getOutQuestionId());
            List<Long> materialIds;
            if (addQuestionCommand.getMaterialIndexes() == null) {
                materialIds = List.of();
            } else {
                materialIds = addQuestionCommand.getMaterialIndexes().stream()
                        .map(index -> materials.get(index).getId()).toList();
            }
            var id = X.id.nextId(QuestionDO.class);
            var question = new Question();
            question.setId(id);
            question.setOutQuestionId(addQuestionCommand.getOutQuestionId());
            question.setContent(addQuestionCommand.getContent());
            question.setChapterName(chapterName);
            question.setModule(XingceModule.fromName(chapterName));
            question.setOptionType(addQuestionCommand.getOptionType());
            question.setOptions(addQuestionCommand.getOptions());
            question.setAnswer(addQuestionCommand.getAnswer());
            question.setMaterialIds(materialIds);
            question.setPaperId(paper.getId());
            return question;
        }).toList();
    }

    public static Map<String, String> getQuestionChapterNameDict(AddPaperCommand addPaperCommand) {
        var chapters = addPaperCommand.getChapters();
        var questionIds = addPaperCommand.getQuestions().stream().map(AddPaperCommand.AddQuestionCommand::getOutQuestionId).toList().toArray(new String[0]);
        var questionToChapter = new HashMap<String, String>();
        int currentQuestionIndex = 0;
        for (var chapter : chapters) {
            for (int i = 0; i < chapter.getQuestionCount(); i++) {
                questionToChapter.put(questionIds[currentQuestionIndex], chapter.getName());
                currentQuestionIndex++;
            }
        }
        return questionToChapter;
    }
}
