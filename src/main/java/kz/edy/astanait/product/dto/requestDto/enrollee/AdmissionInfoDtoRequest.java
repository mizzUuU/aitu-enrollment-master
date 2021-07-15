package kz.edy.astanait.product.dto.requestDto.enrollee;
import kz.edy.astanait.product.dto.requestDto.education.ENTDetailsDtoRequest;
import lombok.Data;

@Data
public class AdmissionInfoDtoRequest {
    private Long id;

    private Long academicDegree;

    private Long educationalProgram;

    private Long examType;

    private Long entCertificateId;

    private String combinedExamPointInformatics;

    private String combinedExamPointEnglish;

    private Long englishCertificateId;

    private Long englishCertificateType;

    private ENTDetailsDtoRequest entDetails;

    private String ikt;

    private String interviewPoints;

    private String creativeExamPoints;
}
