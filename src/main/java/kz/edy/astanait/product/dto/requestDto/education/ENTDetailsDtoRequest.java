package kz.edy.astanait.product.dto.requestDto.education;

import lombok.Data;

@Data
public class ENTDetailsDtoRequest {
    private Long id;
    private Integer mathematicalLiteracyPoint;
    private Integer readingLiteracyPoint;
    private Integer kazakhstanHistoryPoint;
    private Integer profileSubject1Point;
    private Integer profileSubject2Point;
}
