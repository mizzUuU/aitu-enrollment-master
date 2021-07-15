package kz.edy.astanait.product.dto.responseDto.enrollee;

import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.UniversityFileDtoResponse;
import kz.edy.astanait.product.dto.responseDto.education.DistinctionMarkTypeDtoResponse;
import kz.edy.astanait.product.dto.responseDto.education.EducationInstitutionTypeDtoResponse;
import kz.edy.astanait.product.dto.responseDto.education.EducationalInstitutionDtoResponse;
import kz.edy.astanait.product.dto.responseDto.location.CountryDtoResponse;
import kz.edy.astanait.product.dto.responseDto.location.LocalityDtoResponse;
import kz.edy.astanait.product.dto.responseDto.location.RegionDtoResponse;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationalInfoDtoResponse {
    private Long id;

    private UserDtoResponse user;

    private CountryDtoResponse country;

    private LocalityDtoResponse kzGraduationLocality;

    private EducationalInstitutionDtoResponse kzEducationalInstitution;

    private String educationalInstitution;

    private DistinctionMarkTypeDtoResponse distinctionMarkType;

    private String kzGraduationCertificateSeries;

    private String kzGraduationCertificateNumber;

    private String kzGraduationCertificateAveragePoint;

    private LocalDate graduationCertificateIssueDate;

    private String graduationCertificateName;

    private String nostrificationCertificateNumber;

    private LocalDate nostrificationCertificateDate;

    private UniversityFileDtoResponse graduationCertificate;

    private UniversityFileDtoResponse graduationCertificateApplication;

    private String specialityName;

    private EducationInstitutionTypeDtoResponse educationInstitutionType;
}
