package kz.edy.astanait.product.dto.responseDto.enrollee;

import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.UniversityFileDtoResponse;
import kz.edy.astanait.product.dto.responseDto.education.*;
import lombok.Data;

@Data
public class AdmissionInfoDtoResponse {

    private Long id;

    private UserDtoResponse user;

    private AcademicDegreeDtoResponse academicDegree;

    private EducationalProgramDtoResponse educationalProgram;

    private ExamTypeDtoResponse examType;

    private UniversityFileDtoResponse entCertificate;

    private String combinedExamPointInformatics;

    private String combinedExamPointEnglish;

    private UniversityFileDtoResponse englishCertificate;

    private EnglishCertificateTypeDtoResponse englishCertificateType;

    private ENTDetailsDtoResponse entDetailsDtoResponse;

    private String ikt;

    private String interviewPoints;

    private String creativeExamPoints;
}
