package kz.edy.astanait.product.dto.responseDto.education;

import lombok.Data;


@Data
public class EducationalProgramGroupDtoResponse {
    private Long id;

    private String name;

    private String educationalProgramCode;

    private SubjectDtoResponse subject1;

    private SubjectDtoResponse subject2;

    private Boolean isCreativeExam = false;

    private AcademicDegreeDtoResponse academicDegree;
}
