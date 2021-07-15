package kz.edy.astanait.product.service.enrollee;

import kz.edy.astanait.product.dto.requestDto.enrollee.EducationalInfoDtoRequest;
import kz.edy.astanait.product.dto.responseDto.enrollee.EducationalInfoDtoResponse;
import kz.edy.astanait.product.exception.domain.EducationInfoNotFoundException;
import kz.edy.astanait.product.model.document.UniversityFile;
import kz.edy.astanait.product.model.enrollee.DocumentInfo;
import kz.edy.astanait.product.model.enrollee.EducationalInfo;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.repository.document.UniversityFileRepository;
import kz.edy.astanait.product.repository.education.DistinctionMarkTypeRepository;
import kz.edy.astanait.product.repository.education.EducationInstitutionTypeRepository;
import kz.edy.astanait.product.repository.education.EducationalInstitutionRepository;
import kz.edy.astanait.product.repository.entrollee.DocumentInfoRepository;
import kz.edy.astanait.product.repository.entrollee.EducationalInfoRepository;
import kz.edy.astanait.product.repository.location.CountryRepository;
import kz.edy.astanait.product.repository.location.LocalityRepository;
import kz.edy.astanait.product.repository.secretary.ConfirmBlocksRepository;
import kz.edy.astanait.product.utils.facade.enrollee.EducationalInfoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class EducationalInfoService {

    private final EducationalInfoRepository educationalInfoRepository;
    private final DocumentInfoRepository documentInfoRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final LocalityRepository localityRepository;
    private final EducationalInstitutionRepository educationalInstitutionRepository;
    private final DistinctionMarkTypeRepository distinctionMarkTypeRepository;
    private final EducationInstitutionTypeRepository educationInstitutionTypeRepository;
    private final UniversityFileRepository universityFileRepository;
    private final ConfirmBlocksRepository confirmBlocksRepository;

    @Autowired
    public EducationalInfoService(EducationalInfoRepository educationalInfoRepository, DocumentInfoRepository documentInfoRepository, UserRepository userRepository,
                                  CountryRepository countryRepository, LocalityRepository localityRepository, EducationalInstitutionRepository educationalInstitutionRepository, DistinctionMarkTypeRepository distinctionMarkTypeRepository, EducationInstitutionTypeRepository educationInstitutionTypeRepository,
                                  UniversityFileRepository universityFileRepository, ConfirmBlocksRepository confirmBlocksRepository) {
        this.educationalInfoRepository = educationalInfoRepository;
        this.documentInfoRepository = documentInfoRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.localityRepository = localityRepository;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.distinctionMarkTypeRepository = distinctionMarkTypeRepository;
        this.educationInstitutionTypeRepository = educationInstitutionTypeRepository;
        this.universityFileRepository = universityFileRepository;
        this.confirmBlocksRepository = confirmBlocksRepository;
    }

    public EducationalInfoDtoResponse getEducationInfoByOne(Long id) {
        Optional<EducationalInfo> educationalInfo = educationalInfoRepository.findByUserId(id);
        if (educationalInfo.isPresent()) {
            return new EducationalInfoFacade(documentInfoRepository).educationalInfoToEducationalInfoDtoResponse(educationalInfo.get());
        }
        throw new EducationInfoNotFoundException("Education info not found by user id: " + id);
    }

    public EducationalInfoDtoResponse getEducationalInfo(Principal principal) {
        Optional<EducationalInfo> educationalInfoList = educationalInfoRepository.findByUserEmail(principal.getName());
        return educationalInfoList.map(value -> new EducationalInfoFacade(documentInfoRepository).educationalInfoToEducationalInfoDtoResponse(value)).orElse(null);
    }

    public void saveEducationalInfo(EducationalInfoDtoRequest educationalInfoDtoRequest, Principal principal) {
        Optional<EducationalInfo> educationalInfo = educationalInfoRepository.findByUserEmail(principal.getName());
        if (educationalInfo.isPresent()) {
            confirmBlocksRepository.findByUserEmail(principal.getName()).ifPresent(value -> {if(!value.isForthBlock()){
                manipulateEducationalInfo(educationalInfo.get(), educationalInfoDtoRequest, principal.getName());
            }});
        }
        else {
            EducationalInfo createEducationalInfo = new EducationalInfo();
            manipulateEducationalInfo(createEducationalInfo, educationalInfoDtoRequest, principal.getName());
        }
    }

    private void manipulateEducationalInfo(EducationalInfo educationalInfo, EducationalInfoDtoRequest educationalInfoDtoRequest, String email) {
        userRepository.findUserByEmail(email).ifPresent(educationalInfo::setUser);
        if (educationalInfoDtoRequest.getCountry() != null) {
            countryRepository.findById(educationalInfoDtoRequest.getCountry()).ifPresent(educationalInfo::setCountry);
        }
        if (educationalInfoDtoRequest.getKzGraduationLocality() != null) {
            localityRepository.findById(educationalInfoDtoRequest.getKzGraduationLocality()).ifPresent(educationalInfo::setKzGraduationLocality);
        }
        if (educationalInfoDtoRequest.getKzEducationalInstitution() != null) {
            educationalInstitutionRepository.findById(educationalInfoDtoRequest.getKzEducationalInstitution()).ifPresent(educationalInfo::setKzEducationalInstitution);
        }
        if (educationalInfoDtoRequest.getEducationalInstitution() != null) {
            educationalInfo.setEducationalInstitution(educationalInfoDtoRequest.getEducationalInstitution());
        }
        if (educationalInfoDtoRequest.getDistinctionMarkType() != null) {
            distinctionMarkTypeRepository.findById(educationalInfoDtoRequest.getDistinctionMarkType()).ifPresent(educationalInfo::setDistinctionMarkType);
        }
        if (educationalInfoDtoRequest.getKzGraduationCertificateSeries() != null) {
            educationalInfo.setKzGraduationCertificateSeries(educationalInfoDtoRequest.getKzGraduationCertificateSeries());
        }
        if (educationalInfoDtoRequest.getKzGraduationCertificateNumber() != null) {
            educationalInfo.setKzGraduationCertificateNumber(educationalInfoDtoRequest.getKzGraduationCertificateNumber());
        }
        if (educationalInfoDtoRequest.getKzGraduationCertificateAveragePoint() != null) {
            educationalInfo.setKzGraduationCertificateAveragePoint(educationalInfoDtoRequest.getKzGraduationCertificateAveragePoint());
        }
        if (educationalInfoDtoRequest.getGraduationCertificateIssueDate() != null) {
            educationalInfo.setGraduationCertificateIssueDate(educationalInfoDtoRequest.getGraduationCertificateIssueDate());
        }
        if (educationalInfoDtoRequest.getGraduationCertificateName() != null) {
            educationalInfo.setGraduationCertificateName(educationalInfoDtoRequest.getGraduationCertificateName());
        }
        if (educationalInfoDtoRequest.getNostrificationCertificateNumber() != null) {
            educationalInfo.setNostrificationCertificateNumber(educationalInfoDtoRequest.getNostrificationCertificateNumber());
        }
        if (educationalInfoDtoRequest.getNostrificationCertificateDate() != null) {
            educationalInfo.setNostrificationCertificateDate(educationalInfoDtoRequest.getNostrificationCertificateDate());
        }
        if (educationalInfoDtoRequest.getSpecialityName() != null) {
            educationalInfo.setSpecialityName(educationalInfoDtoRequest.getSpecialityName());
        }
        if (educationalInfoDtoRequest.getEducationInstitutionType() != null) {
            educationInstitutionTypeRepository.findById(educationalInfoDtoRequest.getEducationInstitutionType()).ifPresent(educationalInfo::setEducationInstitutionType);
        }
        educationalInfoRepository.save(educationalInfo);

        if (educationalInfoDtoRequest.getGraduationCertificate() != null) {
            Optional<UniversityFile> universityFile = universityFileRepository.findById(educationalInfoDtoRequest.getGraduationCertificate());
            if (universityFile.isPresent() && universityFile.get().getUser().getEmail().equals(email)) {
                Optional<DocumentInfo> optionalDocument = documentInfoRepository.findByUserEmail(email);
                DocumentInfo documentInfo;
                if (optionalDocument.isPresent()) {
                    documentInfo = optionalDocument.get();
                } else {
                    documentInfo = new DocumentInfo();
                    userRepository.findUserByEmail(email).ifPresent(documentInfo::setUser);
                }
                documentInfo.setGraduationCertificate(universityFile.get());
                documentInfoRepository.save(documentInfo);
            }
        }

        if (educationalInfoDtoRequest.getGraduationCertificateApplication() != null) {
            Optional<UniversityFile> universityFile = universityFileRepository.findById(educationalInfoDtoRequest.getGraduationCertificateApplication());
            if (universityFile.isPresent() && universityFile.get().getUser().getEmail().equals(email)) {
                Optional<DocumentInfo> optionalDocument = documentInfoRepository.findByUserEmail(email);
                DocumentInfo documentInfo;
                if (optionalDocument.isPresent()) {
                    documentInfo = optionalDocument.get();
                } else {
                    documentInfo = new DocumentInfo();
                    userRepository.findUserByEmail(email).ifPresent(documentInfo::setUser);
                }
                documentInfo.setGraduationCertificateApplication(universityFile.get());
                documentInfoRepository.save(documentInfo);
            }
        }
    }
}
