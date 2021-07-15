package kz.edy.astanait.product.utils.facade.education;

import kz.edy.astanait.product.dto.responseDto.education.EducationInstitutionTypeDtoResponse;
import kz.edy.astanait.product.model.education.EducationInstitutionType;

public class EducationInstitutionTypeFacade {

    public EducationInstitutionTypeDtoResponse educationInstitutionTypeToEducationInstitutionTypeDtoResponse(EducationInstitutionType educationInstitutionType) {
        EducationInstitutionTypeDtoResponse educationInstitutionTypeDtoResponse = new EducationInstitutionTypeDtoResponse();
        educationInstitutionTypeDtoResponse.setId(educationInstitutionType.getId());
        educationInstitutionTypeDtoResponse.setName(educationInstitutionType.getName());
        return educationInstitutionTypeDtoResponse;
    }
}
