package cool.xxd.product.land.ui.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AddPaperRequest {
    @NotBlank
    private String outPaperId;
    private String name;
    private Integer questionCount;
    @Valid
    private List<ChapterRequest> chapters;
    @Valid
    private List<MaterialRequest> materials;
    @Valid
    private List<QuestionRequest> questions;

    @Data
    public static class ChapterRequest {
        @NotBlank
        private String name;
        private Integer questionCount;
    }

    @Data
    public static class MaterialRequest {
        @NotBlank
        private String outMaterialId;
        private String content;
    }

    @Data
    public static class QuestionRequest {
        @NotBlank
        private String outQuestionId;
        private String content;
        private Integer optionType;
        private List<String> options;
        private String answer;
        private List<Integer> materialIndexes;
    }
}
