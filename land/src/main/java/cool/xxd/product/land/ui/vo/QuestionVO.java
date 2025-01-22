package cool.xxd.product.land.ui.vo;

import lombok.Data;

@Data
public class QuestionVO {
    private Long questionId;
    private String content;
    private String chapterName;
    private String module;
    private Integer optionType;
    private String options;
    private String answer;
    private String materialIds;
    private Long paperId;
}
