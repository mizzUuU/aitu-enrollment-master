package kz.edy.astanait.product.service.enrollee;

import kz.edy.astanait.product.dto.requestDto.enrollee.AdmissionInfoDtoRequest;
import kz.edy.astanait.product.dto.responseDto.enrollee.AdmissionInfoDtoResponse;
import kz.edy.astanait.product.exception.domain.AdmissionInfoNotFoundException;
import kz.edy.astanait.product.model.education.ENTDetails;
import kz.edy.astanait.product.model.enrollee.AdmissionInfo;
import kz.edy.astanait.product.model.enrollee.DocumentInfo;
import kz.edy.astanait.product.model.document.UniversityFile;
import kz.edy.astanait.product.model.secretary.ConfirmBlocks;
import kz.edy.astanait.product.repository.entrollee.AdmissionInfoRepository;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.repository.entrollee.DocumentInfoRepository;
import kz.edy.astanait.product.repository.document.UniversityFileRepository;
import kz.edy.astanait.product.repository.education.*;
import kz.edy.astanait.product.repository.secretary.ConfirmBlocksRepository;
import kz.edy.astanait.product.utils.facade.enrollee.AdmissionInfoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class AdmissionInfoService {

    private final AdmissionInfoRepository admissionInfoRepository;
    private final UserRepository userRepository;
    private final AcademicDegreeRepository academicDegreeRepository;
    private final EducationalProgramRepository educationalProgramRepository;
    private final ExamTypeRepository examTypeRepository;
    private final EnglishCertificateTypeRepository englishCertificateTypeRepository;
    private final UniversityFileRepository universityFileRepository;
    private final DocumentInfoRepository documentInfoRepository;
    private final ENTDetailsRepository entDetailsRepository;
    private final ConfirmBlocksRepository confirmBlocksRepository;

    @Autowired
    public AdmissionInfoService(AdmissionInfoRepository admissionInfoRepository, UserRepository userRepository,
                                AcademicDegreeRepository academicDegreeRepository, EducationalProgramRepository educationalProgramRepository, ExamTypeRepository examTypeRepository,
                                EnglishCertificateTypeRepository englishCertificateTypeRepository,
                                UniversityFileRepository universityFileRepository, DocumentInfoRepository documentInfoRepository, ENTDetailsRepository entDetailsRepository, ConfirmBlocksRepository confirmBlocksRepository) {
        this.admissionInfoRepository = admissionInfoRepository;
        this.userRepository = userRepository;
        this.academicDegreeRepository = academicDegreeRepository;
        this.educationalProgramRepository = educationalProgramRepository;
        this.examTypeRepository = examTypeRepository;
        this.englishCertificateTypeRepository = englishCertificateTypeRepository;
        this.universityFileRepository = universityFileRepository;
        this.documentInfoRepository = documentInfoRepository;
        this.entDetailsRepository = entDetailsRepository;
        this.confirmBlocksRepository = confirmBlocksRepository;
    }

    public AdmissionInfoDtoResponse getAdmissionInfoByOne(Long id) {
        Optional<AdmissionInfo> admissionInfo = admissionInfoRepository.findByUserId(id);
        if (admissionInfo.isPresent()) {
            return new AdmissionInfoFacade(documentInfoRepository).admissionInfoToAdmissionInfoDtoResponse(admissionInfo.get());
        }
        throw new AdmissionInfoNotFoundException("Admission info not found by user id: " + id);
    }

    public AdmissionInfoDtoResponse getAdmissionInfo(Principal principal) {
        Optional<AdmissionInfo> admissionInfo = admissionInfoRepository.findByUserEmail(principal.getName());
        return admissionInfo.map(value -> new AdmissionInfoFacade(documentInfoRepository).admissionInfoToAdmissionInfoDtoResponse(value)).orElse(null);
    }

    public void saveAdmissionInfo(AdmissionInfoDtoRequest admissionInfoDtoRequest, Principal principal) {
        Optional<AdmissionInfo> admissionInfo = admissionInfoRepository.findByUserEmail(principal.getName());
        if (admissionInfo.isPresent()) {
            confirmBlocksRepository.findByUserEmail(principal.getName()).ifPresent(value -> {if(!value.isThirdBlock()) {
                manipulateAdmissionInfo(admissionInfo.get(), admissionInfoDtoRequest, principal.getName());
            }});
        } else {
            AdmissionInfo createAdmissionInfo = new AdmissionInfo();
            manipulateAdmissionInfo(createAdmissionInfo, admissionInfoDtoRequest, principal.getName());
        }
    }

    private void manipulateAdmissionInfo(AdmissionInfo admissionInfo, AdmissionInfoDtoRequest admissionInfoDtoRequest, String email) {
        userRepository.findUserByEmail(email).ifPresent(admissionInfo::setUser);
        if (admissionInfoDtoRequest.getAcademicDegree() != null) {
            academicDegreeRepository.findById(admissionInfoDtoRequest.getAcademicDegree()).ifPresent(admissionInfo::setAcademicDegree);
        }
        if (admissionInfoDtoRequest.getEducationalProgram() != null) {
            educationalProgramRepository.findById(admissionInfoDtoRequest.getEducationalProgram()).ifPresent(admissionInfo::setEducationalProgram);
        }
        if (admissionInfoDtoRequest.getExamType() != null) {
            examTypeRepository.findById(admissionInfoDtoRequest.getExamType()).ifPresent(admissionInfo::setExamType);
        }
        if (admissionInfoDtoRequest.getEnglishCertificateType() != null) {
            englishCertificateTypeRepository.findById(admissionInfoDtoRequest.getEnglishCertificateType()).ifPresent(admissionInfo::setEnglishCertificateType);
        }
        if (admissionInfoDtoRequest.getIkt() != null) {
            admissionInfo.setIkt(admissionInfoDtoRequest.getIkt());
        }
        if (admissionInfoDtoRequest.getEntDetails() != null) {
            if (admissionInfo.getEntDetails() != null) {
                if (admissionInfoDtoRequest.getEntDetails().getMathematicalLiteracyPoint() != null) {
                    admissionInfo.getEntDetails().setMathematicalLiteracyPoint(admissionInfoDtoRequest.getEntDetails().getMathematicalLiteracyPoint());
                }
                if (admissionInfoDtoRequest.getEntDetails().getReadingLiteracyPoint() != null) {
                    admissionInfo.getEntDetails().setReadingLiteracyPoint(admissionInfoDtoRequest.getEntDetails().getReadingLiteracyPoint());
                }
                if (admissionInfoDtoRequest.getEntDetails().getKazakhstanHistoryPoint() != null) {
                    admissionInfo.getEntDetails().setKazakhstanHistoryPoint(admissionInfoDtoRequest.getEntDetails().getKazakhstanHistoryPoint());
                }
                if (admissionInfoDtoRequest.getEntDetails().getProfileSubject1Point() != null) {
                    admissionInfo.getEntDetails().setProfileSubject1Point(admissionInfoDtoRequest.getEntDetails().getProfileSubject1Point());
                }
                if (admissionInfoDtoRequest.getEntDetails().getProfileSubject2Point() != null) {
                    admissionInfo.getEntDetails().setProfileSubject2Point(admissionInfoDtoRequest.getEntDetails().getProfileSubject2Point());
                }
            } else {
                ENTDetails entDetails = new ENTDetails();
                if (admissionInfoDtoRequest.getEntDetails().getMathematicalLiteracyPoint() != null) {
                    entDetails.setMathematicalLiteracyPoint(admissionInfoDtoRequest.getEntDetails().getMathematicalLiteracyPoint());
                }
                if (admissionInfoDtoRequest.getEntDetails().getReadingLiteracyPoint() != null) {
                    entDetails.setReadingLiteracyPoint(admissionInfoDtoRequest.getEntDetails().getReadingLiteracyPoint());
                }
                if (admissionInfoDtoRequest.getEntDetails().getKazakhstanHistoryPoint() != null) {
                    entDetails.setKazakhstanHistoryPoint(admissionInfoDtoRequest.getEntDetails().getKazakhstanHistoryPoint());
                }
                if (admissionInfoDtoRequest.getEntDetails().getProfileSubject1Point() != null) {
                    entDetails.setProfileSubject1Point(admissionInfoDtoRequest.getEntDetails().getProfileSubject1Point());
                }
                if (admissionInfoDtoRequest.getEntDetails().getProfileSubject2Point() != null) {
                    entDetails.setProfileSubject2Point(admissionInfoDtoRequest.getEntDetails().getProfileSubject2Point());
                }
                admissionInfo.setEntDetails(entDetailsRepository.save(entDetails));
            }
        }
        admissionInfoRepository.save(admissionInfo);

        if (admissionInfoDtoRequest.getEntCertificateId() != null) {
            Optional<UniversityFile> ENTCertificate = universityFileRepository.findById(admissionInfoDtoRequest.getEntCertificateId());
            if (ENTCertificate.isPresent() && ENTCertificate.get().getUser().getEmail().equals(email)) {
                Optional<DocumentInfo> optionalDocument = documentInfoRepository.findByUserEmail(email);
                DocumentInfo documentInfo;
                if (optionalDocument.isPresent()) {
                    documentInfo = optionalDocument.get();
                } else {
                    documentInfo = new DocumentInfo();
                    userRepository.findUserByEmail(email).ifPresent(documentInfo::setUser);
                }
                documentInfo.setEntCertificate(ENTCertificate.get());
                documentInfoRepository.save(documentInfo);
            }
        }
        if (admissionInfoDtoRequest.getEnglishCertificateId() != null) {
            Optional<UniversityFile> englishCertificate = universityFileRepository.findById(admissionInfoDtoRequest.getEnglishCertificateId());
            if (englishCertificate.isPresent() && englishCertificate.get().getUser().getEmail().equals(email)) {
                Optional<DocumentInfo> optionalDocument = documentInfoRepository.findByUserEmail(email);
                DocumentInfo documentInfo;
                if (optionalDocument.isPresent()) {
                    documentInfo = optionalDocument.get();
                } else {
                    documentInfo = new DocumentInfo();
                    userRepository.findUserByEmail(email).ifPresent(documentInfo::setUser);
                }
                documentInfo.setEnglishCertificate(englishCertificate.get());
                documentInfoRepository.save(documentInfo);
            }
        }
    }
}
