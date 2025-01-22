package cool.xxd.product.land.domain.aggregate;

import cool.xxd.product.land.domain.enums.IndexStatus;
import cool.xxd.product.land.domain.enums.OptionType;
import cool.xxd.product.land.domain.enums.XingceModule;
import lombok.Data;

import java.util.List;

@Data
public class Question {
    private Long id;
    private String outQuestionId;
    private String content;
    private String chapterName;
    private XingceModule module;
    private OptionType optionType;
    private List<String> options;
    private String answer;
    private List<Long> materialIds;
    private Long paperId;
    private IndexStatus indexStatus;

    public void startIndex() {
        this.indexStatus = IndexStatus.INDEXING;
    }

    public void finishIndex() {
        this.indexStatus = IndexStatus.INDEXED;
    }
}
