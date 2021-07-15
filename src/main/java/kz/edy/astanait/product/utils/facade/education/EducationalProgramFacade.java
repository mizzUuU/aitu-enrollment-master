package kz.edy.astanait.product.utils.facade.education;

import kz.edy.astanait.product.dto.responseDto.education.EducationalProgramDtoResponse;
import kz.edy.astanait.product.model.education.EducationalProgram;

public class EducationalProgramFacade {

    public EducationalProgramDtoResponse educationProgramToEducationProgramDtoResponse(EducationalProgram educationalProgram) {
        EducationalProgramDtoResponse educationalProgramDtoResponse = new EducationalProgramDtoResponse();
        educationalProgramDtoResponse.setId(educationalProgram.getId());
        educationalProgramDtoResponse.setName(educationalProgram.getName());
        if (educationalProgram.getEducationalProgramGroup() != null) {
            educationalProgramDtoResponse.setEducationalProgramGroup(new EducationalProgramGroupFacade().
                    educationalProgramGroupToEducationalProgramGroupDtoResponse(educationalProgram.getEducationalProgramGroup()));
        }
        return educationalProgramDtoResponse;
    }
}
