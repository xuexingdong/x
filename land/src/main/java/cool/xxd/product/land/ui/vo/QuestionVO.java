package cool.xxd.product.land.ui.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionVO {
    private Long id;
    private String content;
    private String chapterName;
    private Integer module;
    private Integer optionType;
    private List<String> options;
    private String answer;
    private List<Long> materialIds;
    private Long paperId;
}
