package kz.edy.astanait.product.utils.facade.education;

import kz.edy.astanait.product.dto.responseDto.education.EducationalInstitutionDtoResponse;
import kz.edy.astanait.product.model.education.EducationalInstitution;
import kz.edy.astanait.product.utils.facade.location.StreetFacade;

public class EducationalInstitutionFacade {

    public EducationalInstitutionDtoResponse educationalInstitutionToEducationalInstitutionDtoResponse(EducationalInstitution educationalInstitution) {
        EducationalInstitutionDtoResponse educationalInstitutionDtoResponse = new EducationalInstitutionDtoResponse();
        educationalInstitutionDtoResponse.setId(educationalInstitution.getId());
        if (educationalInstitution.getStreet() != null) {
            educationalInstitutionDtoResponse.setStreet(new StreetFacade().streetToStreetDtoResponse(educationalInstitution.getStreet()));
        }
        educationalInstitutionDtoResponse.setInstitutionType(new EducationInstitutionTypeFacade().educationInstitutionTypeToEducationInstitutionTypeDtoResponse(educationalInstitution.getInstitutionType()));
        educationalInstitutionDtoResponse.setName(educationalInstitution.getName());
        return educationalInstitutionDtoResponse;
    }
}
