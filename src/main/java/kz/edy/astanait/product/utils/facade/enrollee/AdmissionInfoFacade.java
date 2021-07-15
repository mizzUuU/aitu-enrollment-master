package kz.edy.astanait.product.utils.facade.enrollee;

import kz.edy.astanait.product.dto.responseDto.education.ENTDetailsDtoResponse;
import kz.edy.astanait.product.dto.responseDto.enrollee.AdmissionInfoDtoResponse;
import kz.edy.astanait.product.model.enrollee.AdmissionInfo;
import kz.edy.astanait.product.model.enrollee.DocumentInfo;
import kz.edy.astanait.product.repository.entrollee.DocumentInfoRepository;
import kz.edy.astanait.product.utils.facade.UserFacade;
import kz.edy.astanait.product.utils.facade.document.UniversityFileFacade;
import kz.edy.astanait.product.utils.facade.education.*;

import java.util.Optional;

public class AdmissionInfoFacade {

    private final DocumentInfoRepository documentInfoRepository;

    public AdmissionInfoFacade(DocumentInfoRepository documentInfoRepository) {
        this.documentInfoRepository = documentInfoRepository;
    }

    public AdmissionInfoDtoResponse admissionInfoToAdmissionInfoDtoResponse(AdmissionInfo admissionInfo) {
        AdmissionInfoDtoResponse admissionInfoDtoResponse = new AdmissionInfoDtoResponse();
        admissionInfoDtoResponse.setId(admissionInfo.getId());
        if (admissionInfo.getUser() != null) {
            admissionInfoDtoResponse.setUser(new UserFacade().userToUserDtoResponse(admissionInfo.getUser()));
        }
        if (admissionInfo.getAcademicDegree() != null) {
            admissionInfoDtoResponse.setAcademicDegree(new AcademicDegreeFacade().academicDegreeToAcademicDegreeDtoResponse(admissionInfo.getAcademicDegree()));
        }
        if (admissionInfo.getEducationalProgram() != null) {
            admissionInfoDtoResponse.setEducationalProgram(new EducationalProgramFacade().educationProgramToEducationProgramDtoResponse(admissionInfo.getEducationalProgram()));
        }
        if (admissionInfo.getExamType() != null) {
            admissionInfoDtoResponse.setExamType(new ExamTypeFacade().examTypeToExamTypeDtoResponse(admissionInfo.getExamType()));
        }
        if(admissionInfo.getCombinedExamPointInformatics() != null) {
            admissionInfoDtoResponse.setCombinedExamPointInformatics(admissionInfo.getCombinedExamPointInformatics());
        }
        if (admissionInfo.getCombinedExamPointEnglish() != null) {
            admissionInfoDtoResponse.setCombinedExamPointEnglish(admissionInfo.getCombinedExamPointEnglish());
        }
        if (admissionInfo.getEnglishCertificateType() != null) {
            admissionInfoDtoResponse.setEnglishCertificateType(new EnglishCertificateTypeFacade().englishCertificateTypeToEnglishCertificateTypeDtoResponse(admissionInfo.getEnglishCertificateType()));
        }
        if (admissionInfo.getEntDetails() != null) {
            ENTDetailsDtoResponse entDetailsDtoResponse = new ENTDetailsDtoResponse();
            if (admissionInfo.getEntDetails().getMathematicalLiteracyPoint() != null) {
                entDetailsDtoResponse.setMathematicalLiteracyPoint(admissionInfo.getEntDetails().getMathematicalLiteracyPoint());
            }
            if (admissionInfo.getEntDetails().getReadingLiteracyPoint() != null) {
                entDetailsDtoResponse.setReadingLiteracyPoint(admissionInfo.getEntDetails().getReadingLiteracyPoint());
            }
            if (admissionInfo.getEntDetails().getKazakhstanHistoryPoint() != null) {
                entDetailsDtoResponse.setKazakhstanHistoryPoint(admissionInfo.getEntDetails().getKazakhstanHistoryPoint());
            }
            if (admissionInfo.getEntDetails().getProfileSubject1Point() != null) {
                entDetailsDtoResponse.setProfileSubject1Point(admissionInfo.getEntDetails().getProfileSubject1Point());
            }
            if (admissionInfo.getEntDetails().getProfileSubject2Point() != null) {
                entDetailsDtoResponse.setProfileSubject2Point(admissionInfo.getEntDetails().getProfileSubject2Point());
            }
            admissionInfoDtoResponse.setEntDetailsDtoResponse(entDetailsDtoResponse);
        }
        if (admissionInfo.getUser() != null) {
            Optional<DocumentInfo> document = documentInfoRepository.findByUserEmail(admissionInfo.getUser().getEmail());
            if (document.isPresent()) {
                if (document.get().getEntCertificate() != null) {
                    admissionInfoDtoResponse.setEntCertificate(new UniversityFileFacade().universityFileToUniversityFileDtoResponse(document.get().getEntCertificate()));
                }
                if (document.get().getEnglishCertificate() != null) {
                    admissionInfoDtoResponse.setEnglishCertificate(new UniversityFileFacade().universityFileToUniversityFileDtoResponse(document.get().getEnglishCertificate()));
                }
            }
        }
        if (admissionInfo.getIkt() != null) {
            admissionInfoDtoResponse.setIkt(admissionInfo.getIkt());
        }
        if (admissionInfo.getInterviewPoints() != null) {
            admissionInfoDtoResponse.setInterviewPoints(admissionInfo.getInterviewPoints());
        }
        if (admissionInfo.getCreativeExamPoints() != null) {
            admissionInfoDtoResponse.setCreativeExamPoints(admissionInfo.getCreativeExamPoints());
        }
        return admissionInfoDtoResponse;
    }
}
