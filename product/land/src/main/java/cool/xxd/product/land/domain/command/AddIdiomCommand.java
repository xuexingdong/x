package cool.xxd.product.land.domain.command;

import cool.xxd.infra.ddd.Command;
import cool.xxd.product.land.domain.enums.IdiomEmotion;
import lombok.Data;

import java.util.List;

@Data
public class AddIdiomCommand implements Command {

    private String word;
    private String pinyin;
    // 感情
    private IdiomEmotion emotion;
    // 近义词
    private List<String> synonyms;
    // 反义词
    private List<String> antonyms;
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
    private List<String> exampleSentences;

    private List<Long> questionIds;
}
