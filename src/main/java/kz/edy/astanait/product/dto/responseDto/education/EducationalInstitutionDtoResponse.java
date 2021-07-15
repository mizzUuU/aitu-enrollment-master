package kz.edy.astanait.product.dto.responseDto.education;

import kz.edy.astanait.product.dto.responseDto.location.StreetDtoResponse;
import lombok.Data;
@Data
public class EducationalInstitutionDtoResponse {
    private Long id;

    private StreetDtoResponse street;

    private EducationInstitutionTypeDtoResponse institutionType;

    private String name;
}
