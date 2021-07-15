package kz.edy.astanait.product.dto.responseDto.education;

import lombok.Data;

@Data
public class DistinctionMarkTypeDtoResponse {
    private Long id;
    private String name;
    private EducationInstitutionTypeDtoResponse institutionType;
}
