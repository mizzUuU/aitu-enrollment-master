package kz.edy.astanait.product.utils.facade.education;

import kz.edy.astanait.product.dto.responseDto.education.AcademicDegreeDtoResponse;
import kz.edy.astanait.product.model.education.AcademicDegree;

public class AcademicDegreeFacade {

    public AcademicDegreeDtoResponse academicDegreeToAcademicDegreeDtoResponse(AcademicDegree academicDegree) {
        AcademicDegreeDtoResponse academicDegreeDtoResponse = new AcademicDegreeDtoResponse();
        academicDegreeDtoResponse.setId(academicDegree.getId());
        academicDegreeDtoResponse.setName(academicDegree.getName());
        return academicDegreeDtoResponse;
    }
}
