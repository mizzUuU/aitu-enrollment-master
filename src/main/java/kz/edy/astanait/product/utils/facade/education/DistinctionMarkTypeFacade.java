package kz.edy.astanait.product.utils.facade.education;

import kz.edy.astanait.product.dto.responseDto.education.DistinctionMarkTypeDtoResponse;
import kz.edy.astanait.product.dto.responseDto.education.EducationInstitutionTypeDtoResponse;
import kz.edy.astanait.product.model.education.DistinctionMarkType;

public class DistinctionMarkTypeFacade {

    public DistinctionMarkTypeDtoResponse distinctionMarkTypeToDistinctionMarkTypeDtoResponse(DistinctionMarkType distinctionMarkType) {
        DistinctionMarkTypeDtoResponse distinctionMarkTypeDtoResponse = new DistinctionMarkTypeDtoResponse();
        distinctionMarkTypeDtoResponse.setId(distinctionMarkType.getId());
        distinctionMarkTypeDtoResponse.setName(distinctionMarkType.getName());
        distinctionMarkTypeDtoResponse.setInstitutionType(new EducationInstitutionTypeFacade().educationInstitutionTypeToEducationInstitutionTypeDtoResponse(distinctionMarkType.getInstitutionType()));
        return distinctionMarkTypeDtoResponse;
    }
}
