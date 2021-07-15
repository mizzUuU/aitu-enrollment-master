package kz.edy.astanait.product.utils.facade.education;

import kz.edy.astanait.product.dto.responseDto.education.EducationalProgramGroupDtoResponse;
import kz.edy.astanait.product.model.education.EducationalProgramGroup;

public class EducationalProgramGroupFacade {

    public EducationalProgramGroupDtoResponse educationalProgramGroupToEducationalProgramGroupDtoResponse(EducationalProgramGroup educationalProgramGroup) {
        EducationalProgramGroupDtoResponse educationalProgramGroupDtoResponse = new EducationalProgramGroupDtoResponse();
        educationalProgramGroupDtoResponse.setId(educationalProgramGroup.getId());
        educationalProgramGroupDtoResponse.setName(educationalProgramGroup.getName());
        educationalProgramGroupDtoResponse.setEducationalProgramCode(educationalProgramGroup.getEducationalProgramCode());
        if (educationalProgramGroup.getSubject1() != null) {
            educationalProgramGroupDtoResponse.setSubject1(new SubjectFacade().subjectToSubjectDtoResponse(educationalProgramGroup.getSubject1()));
        }
        if (educationalProgramGroup.getSubject2() != null) {
            educationalProgramGroupDtoResponse.setSubject2(new SubjectFacade().subjectToSubjectDtoResponse(educationalProgramGroup.getSubject2()));
        }
        educationalProgramGroupDtoResponse.setIsCreativeExam(educationalProgramGroup.getIsCreativeExam());
        if (educationalProgramGroup.getAcademicDegree() != null) {
            educationalProgramGroupDtoResponse.setAcademicDegree(new AcademicDegreeFacade().academicDegreeToAcademicDegreeDtoResponse(educationalProgramGroup.getAcademicDegree()));
        }
        return educationalProgramGroupDtoResponse;
    }
}
