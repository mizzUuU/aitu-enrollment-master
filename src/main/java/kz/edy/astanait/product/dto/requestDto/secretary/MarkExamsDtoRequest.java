package kz.edy.astanait.product.dto.requestDto.secretary;

import lombok.Data;

@Data
public class MarkExamsDtoRequest {
    private Long userId;
    private String combinedExamPointInformatics;
    private String combinedExamPointEnglish;
    private String interviewPoints;
    private String creativeExamPoints;
}
