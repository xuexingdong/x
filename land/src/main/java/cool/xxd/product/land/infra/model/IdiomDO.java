package cool.xxd.product.land.infra.model;

import com.baomidou.mybatisplus.annotation.TableName;
import cool.xxd.infra.mybatis.XBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("land_idiom")
public class IdiomDO extends XBaseDO {
    private String word;
    private String pinyin;
    // 感情
    private Integer emotion;
    // 近义词
    private String synonyms;
    // 反义词
    private String antonyms;
    // 意思
    private String meaning;
    // 出处
    private String origin;
    // 用法
    private String usage;
    // 例子
    private String example;
    // 辨析
    private String distinction;
    // 故事
    private String story;
    // 释义
    private String explanation;
    // 例句
    private String exampleSentences;
    // 最后更新时间
    private LocalDateTime lastUpdateTime;
}
