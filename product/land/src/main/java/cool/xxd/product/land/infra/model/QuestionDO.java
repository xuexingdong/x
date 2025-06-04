package cool.xxd.product.land.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("land_question")
public class QuestionDO extends XBaseDO {
    private String outQuestionId;
    private String content;
    private String chapterName;
    private Integer module;
    private Integer optionType;
    private String options;
    private String answer;
    private String materialIds;
    private Long paperId;
    private String indexStatus;
}
