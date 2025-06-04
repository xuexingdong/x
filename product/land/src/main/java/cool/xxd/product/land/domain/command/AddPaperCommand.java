package cool.xxd.product.land.domain.command;

import cool.xxd.infra.ddd.Command;
import cool.xxd.product.land.domain.enums.OptionType;
import lombok.Data;

import java.util.List;

@Data
public class AddPaperCommand implements Command {
    private String outPaperId;
    private String name;
    private Integer questionCount;
    private List<ChapterCommand> chapters;
    private List<AddMaterialCommand> materials;
    private List<AddQuestionCommand> questions;

    @Data
    public static class ChapterCommand {
        private String name;
        private Integer questionCount;
    }

    @Data
    public static class AddMaterialCommand {
        private String outMaterialId;
        private String content;
    }

    @Data
    public static class AddQuestionCommand {
        private String outQuestionId;
        private String content;
        private OptionType optionType;
        private List<String> options;
        private String answer;
        private List<Integer> materialIndexes;
    }
}
