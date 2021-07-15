package kz.edy.astanait.product.utils.facade.education;

import kz.edy.astanait.product.dto.responseDto.education.SubjectDtoResponse;
import kz.edy.astanait.product.model.education.Subject;

public class SubjectFacade {

    public SubjectDtoResponse subjectToSubjectDtoResponse(Subject subject) {
        SubjectDtoResponse subjectDtoResponse = new SubjectDtoResponse();
        subjectDtoResponse.setId(subject.getId());
        subjectDtoResponse.setTitle(subject.getTitle());
        return subjectDtoResponse;
    }
}
