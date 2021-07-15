package kz.edy.astanait.product.dto.requestDto.secretary;

import lombok.Data;


@Data
public class ExcelDtoRequest {
  private Integer page;
  private String type;
  private String keyword;
  private Integer EntFrom;
  private Integer EntTo;
  private Integer AETEnglishFrom;
  private Integer AETEnglishTo;
  private boolean fullness;
  private boolean englishCertification;
}
