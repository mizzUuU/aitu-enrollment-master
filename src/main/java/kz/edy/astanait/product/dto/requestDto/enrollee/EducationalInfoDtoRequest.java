package kz.edy.astanait.product.dto.requestDto.enrollee;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class EducationalInfoDtoRequest {
    private Long id;

    private Long country;

    private Long kzGraduationLocality;

    private Long kzEducationalInstitution;

    private String educationalInstitution;

    private Long distinctionMarkType;

    private String kzGraduationCertificateSeries;

    private String kzGraduationCertificateNumber;

    private String kzGraduationCertificateAveragePoint;

    private LocalDate graduationCertificateIssueDate;

    private String graduationCertificateName;

    private String nostrificationCertificateNumber;

    private LocalDate nostrificationCertificateDate;

    private Long graduationCertificate;

    private Long graduationCertificateApplication;

    private String specialityName;

    private Long educationInstitutionType;

}
