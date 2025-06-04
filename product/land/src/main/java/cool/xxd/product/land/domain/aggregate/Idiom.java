package cool.xxd.product.land.domain.aggregate;

import cool.xxd.product.land.domain.command.AddIdiomCommand;
import cool.xxd.product.land.domain.enums.IdiomEmotion;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Idiom {
    private Long id;
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
    // 最后更新时间
    private LocalDateTime lastUpdateTime;

    public void update(AddIdiomCommand command) {
        if (StringUtils.isNotEmpty(command.getPinyin())) {
            this.pinyin = command.getPinyin();
        }
        if (command.getEmotion() != null) {
            this.emotion = command.getEmotion();
        }
        if (command.getSynonyms() != null && !command.getSynonyms().isEmpty()) {
            this.synonyms = command.getSynonyms();
        }
        if (command.getAntonyms() != null && !command.getAntonyms().isEmpty()) {
            this.antonyms = command.getAntonyms();
        }
        if (StringUtils.isNotEmpty(command.getMeaning())) {
            this.meaning = command.getMeaning();
        }
        if (StringUtils.isNotEmpty(command.getOrigin())) {
            this.origin = command.getOrigin();
        }
        if (StringUtils.isNotEmpty(command.getUsage())) {
            this.usage = command.getUsage();
        }
        if (StringUtils.isNotEmpty(command.getExample())) {
            this.example = command.getExample();
        }
        if (StringUtils.isNotEmpty(command.getDistinction())) {
            this.distinction = command.getDistinction();
        }
        if (StringUtils.isNotEmpty(command.getStory())) {
            this.story = command.getStory();
        }
        if (StringUtils.isNotEmpty(command.getExplanation())) {
            this.explanation = command.getExplanation();
        }
        if (command.getExampleSentences() != null && !command.getExampleSentences().isEmpty()) {
            this.exampleSentences = command.getExampleSentences();
        }
        this.lastUpdateTime = LocalDateTime.now();
    }
}
