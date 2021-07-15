package kz.edy.astanait.product.utils.facade.enrollee;

import kz.edy.astanait.product.dto.responseDto.enrollee.EducationalInfoDtoResponse;
import kz.edy.astanait.product.model.enrollee.DocumentInfo;
import kz.edy.astanait.product.model.enrollee.EducationalInfo;
import kz.edy.astanait.product.repository.entrollee.DocumentInfoRepository;
import kz.edy.astanait.product.utils.facade.UserFacade;
import kz.edy.astanait.product.utils.facade.document.UniversityFileFacade;
import kz.edy.astanait.product.utils.facade.education.DistinctionMarkTypeFacade;
import kz.edy.astanait.product.utils.facade.education.EducationInstitutionTypeFacade;
import kz.edy.astanait.product.utils.facade.education.EducationalInstitutionFacade;
import kz.edy.astanait.product.utils.facade.location.CountryFacade;
import kz.edy.astanait.product.utils.facade.location.LocalityFacade;

import java.util.Optional;

public class EducationalInfoFacade {

    private final DocumentInfoRepository documentInfoRepository;

    public EducationalInfoFacade(DocumentInfoRepository documentInfoRepository) {
        this.documentInfoRepository = documentInfoRepository;
    }

    public EducationalInfoDtoResponse educationalInfoToEducationalInfoDtoResponse(EducationalInfo educationalInfo) {
        EducationalInfoDtoResponse educationalInfoDtoResponse = new EducationalInfoDtoResponse();
        educationalInfoDtoResponse.setId(educationalInfo.getId());
        if (educationalInfo.getUser() != null) {
            educationalInfoDtoResponse.setUser(new UserFacade().userToUserDtoResponse(educationalInfo.getUser()));
        }
        if (educationalInfo.getCountry() != null) {
            educationalInfoDtoResponse.setCountry(new CountryFacade().countryToCountryDtoResponse(educationalInfo.getCountry()));
        }
        if (educationalInfo.getKzGraduationLocality() != null) {
            educationalInfoDtoResponse.setKzGraduationLocality(new LocalityFacade().localityToLocalityDtoResponse(educationalInfo.getKzGraduationLocality()));
        }
        if (educationalInfo.getKzEducationalInstitution() != null) {
            educationalInfoDtoResponse.setKzEducationalInstitution(new EducationalInstitutionFacade().educationalInstitutionToEducationalInstitutionDtoResponse(educationalInfo.getKzEducationalInstitution()));
        }
        if (educationalInfo.getEducationalInstitution() != null) {
            educationalInfoDtoResponse.setEducationalInstitution(educationalInfo.getEducationalInstitution());
        }
        if (educationalInfo.getDistinctionMarkType() != null) {
            educationalInfoDtoResponse.setDistinctionMarkType(new DistinctionMarkTypeFacade().distinctionMarkTypeToDistinctionMarkTypeDtoResponse(educationalInfo.getDistinctionMarkType()));
        }
        if (educationalInfo.getKzGraduationCertificateSeries() != null) {
            educationalInfoDtoResponse.setKzGraduationCertificateSeries(educationalInfo.getKzGraduationCertificateSeries());
        }
        if (educationalInfo.getKzGraduationCertificateNumber() != null) {
            educationalInfoDtoResponse.setKzGraduationCertificateNumber(educationalInfo.getKzGraduationCertificateNumber());
        }
        if (educationalInfo.getKzGraduationCertificateAveragePoint() != null) {
            educationalInfoDtoResponse.setKzGraduationCertificateAveragePoint(educationalInfo.getKzGraduationCertificateAveragePoint());
        }
        if (educationalInfo.getGraduationCertificateIssueDate() != null) {
            educationalInfoDtoResponse.setGraduationCertificateIssueDate(educationalInfo.getGraduationCertificateIssueDate());
        }
        if (educationalInfo.getGraduationCertificateName() != null) {
            educationalInfoDtoResponse.setGraduationCertificateName(educationalInfo.getGraduationCertificateName());
        }
        if (educationalInfo.getNostrificationCertificateNumber() != null) {
            educationalInfoDtoResponse.setNostrificationCertificateNumber(educationalInfo.getNostrificationCertificateNumber());
        }
        if (educationalInfo.getNostrificationCertificateDate() != null) {
            educationalInfoDtoResponse.setNostrificationCertificateDate(educationalInfo.getNostrificationCertificateDate());
        }
        if (educationalInfo.getSpecialityName() != null) {
            educationalInfoDtoResponse.setSpecialityName(educationalInfo.getSpecialityName());
        }
        if (educationalInfo.getEducationInstitutionType() != null) {
            educationalInfoDtoResponse.setEducationInstitutionType(new EducationInstitutionTypeFacade().educationInstitutionTypeToEducationInstitutionTypeDtoResponse(educationalInfo.getEducationInstitutionType()));
        }
        if (educationalInfo.getUser() != null) {
            Optional<DocumentInfo> document = documentInfoRepository.findByUserEmail(educationalInfo.getUser().getEmail());
            if (document.isPresent()) {
                if (document.get().getGraduationCertificate() != null) {
                    educationalInfoDtoResponse.setGraduationCertificate(new UniversityFileFacade().universityFileToUniversityFileDtoResponse(document.get().getGraduationCertificate()));
                }
                if (document.get().getGraduationCertificateApplication() != null) {
                    educationalInfoDtoResponse.setGraduationCertificateApplication(new UniversityFileFacade().universityFileToUniversityFileDtoResponse(document.get().getGraduationCertificateApplication()));
                }
            }
        }
        return educationalInfoDtoResponse;
    }
}
