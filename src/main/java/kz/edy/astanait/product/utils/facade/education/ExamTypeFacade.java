package kz.edy.astanait.product.utils.facade.education;

import kz.edy.astanait.product.dto.responseDto.education.ExamTypeDtoResponse;
import kz.edy.astanait.product.model.education.ExamType;
import lombok.Data;

@Data
public class ExamTypeFacade {

    public ExamTypeDtoResponse examTypeToExamTypeDtoResponse(ExamType examType) {
        ExamTypeDtoResponse examTypeDtoResponse = new ExamTypeDtoResponse();
        examTypeDtoResponse.setId(examType.getId());
        examTypeDtoResponse.setName(examType.getName());
        return examTypeDtoResponse;
    }
}
