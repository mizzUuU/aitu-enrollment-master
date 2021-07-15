package kz.edy.astanait.product.service.secretary;

import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.repository.entrollee.AdmissionInfoRepository;
import kz.edy.astanait.product.repository.entrollee.DocumentInfoRepository;
import kz.edy.astanait.product.repository.entrollee.EducationalInfoRepository;
import kz.edy.astanait.product.repository.entrollee.PersonalInfoRepository;
import kz.edy.astanait.product.utils.facade.UserFacade;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFilterService {

    private static final int pageSize = 20;

    private final PersonalInfoRepository personalInfoRepository;
    private final EducationalInfoRepository educationalInfoRepository;
    private final UserRepository userRepository;
    private final AdmissionInfoRepository admissionInfoRepository;
    private final DocumentInfoRepository documentInfoRepository;

    @Autowired
    public UserFilterService(PersonalInfoRepository personalInfoRepository, EducationalInfoRepository educationalInfoRepository, UserRepository userRepository, AdmissionInfoRepository admissionInfoRepository, DocumentInfoRepository documentInfoRepository) {
        this.personalInfoRepository = personalInfoRepository;
        this.educationalInfoRepository = educationalInfoRepository;
        this.userRepository = userRepository;
        this.admissionInfoRepository = admissionInfoRepository;
        this.documentInfoRepository = documentInfoRepository;
    }

    public HashMap<String, Object> selectAll(Integer EntFrom, Integer EntTo, Integer AETEnglishFrom, Integer AETEnglishTo, boolean fullness, boolean englishCertification, Integer page) {
        List<UserDtoResponse> userDtoResponses = userRepository.findAll().stream().filter(value -> value.getRole().getRoleName().equals("ROLE_USER")).map(value -> new UserFacade().userToUserDtoResponse(value)).collect(Collectors.toList());
        PagedListHolder<UserDtoResponse> pagedListHolder = new PagedListHolder<>(selectByAllFilters(userDtoResponses, EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification));
        pagedListHolder.setPageSize(pageSize);
        pagedListHolder.setPage(page);
        return pageConvertToMap(pagedListHolder);
    }

    public List<UserDtoResponse> selectAllForExcel(Integer EntFrom, Integer EntTo, Integer AETEnglishFrom, Integer AETEnglishTo, boolean fullness, boolean englishCertification) {
        List<UserDtoResponse> userDtoResponses = userRepository.findAll().stream().filter(value -> value.getRole().getRoleName().equals("ROLE_USER")).map(value -> new UserFacade().userToUserDtoResponse(value)).collect(Collectors.toList());
        return selectByAllFilters(userDtoResponses, EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification);
    }

    public HashMap<String, Object> selectFilteredIIN(String iin, Integer EntFrom, Integer EntTo, Integer AETEnglishFrom, Integer AETEnglishTo, boolean fullness, boolean englishCertification, Integer page) {
        PagedListHolder<UserDtoResponse> pagedListHolder = new PagedListHolder<>(selectByAllFilters(selectByIIN(iin), EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification));
        pagedListHolder.setPageSize(pageSize);
        pagedListHolder.setPage(page);
        return pageConvertToMap(pagedListHolder);
    }

    public List<UserDtoResponse> selectFilteredIINForExcel(String iin, Integer EntFrom, Integer EntTo, Integer AETEnglishFrom, Integer AETEnglishTo, boolean fullness, boolean englishCertification) {
        return selectByAllFilters(selectByIIN(iin), EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification);
    }

    public HashMap<String, Object> selectFilteredLocality(String locality, Integer EntFrom, Integer EntTo, Integer AETEnglishFrom, Integer AETEnglishTo, boolean fullness, boolean englishCertification, Integer page) {
        PagedListHolder<UserDtoResponse> pagedListHolder = new PagedListHolder<>(selectByAllFilters(selectByLocality(locality), EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification));
        pagedListHolder.setPageSize(pageSize);
        pagedListHolder.setPage(page);
        return pageConvertToMap(pagedListHolder);
    }

    public List<UserDtoResponse> selectFilteredLocalityForExcel(String locality, Integer EntFrom, Integer EntTo, Integer AETEnglishFrom, Integer AETEnglishTo, boolean fullness, boolean englishCertification) {
        return selectByAllFilters(selectByLocality(locality), EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification);
    }

    public HashMap<String, Object> selectFilteredFIO(String FIO, Integer EntFrom, Integer EntTo, Integer AETEnglishFrom, Integer AETEnglishTo, boolean fullness, boolean englishCertification, Integer page) {
        PagedListHolder<UserDtoResponse> pagedListHolder =
                new PagedListHolder<>(selectByAllFilters(selectByFIO(FIO), EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification)
                        .stream().filter(value -> value.getRole().getRoleName().equals("ROLE_USER")).collect(Collectors.toList()));
        pagedListHolder.setPageSize(pageSize);
        pagedListHolder.setPage(page);
        return pageConvertToMap(pagedListHolder);
    }

    public List<UserDtoResponse> selectFilteredFIOForExcel(String FIO, Integer EntFrom, Integer EntTo, Integer AETEnglishFrom, Integer AETEnglishTo, boolean fullness, boolean englishCertification) {
        return selectByAllFilters(selectByFIO(FIO), EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification)
                        .stream().filter(value -> value.getRole().getRoleName().equals("ROLE_USER")).collect(Collectors.toList());

    }

    private HashMap<String, Object> pageConvertToMap(PagedListHolder<UserDtoResponse> pagedListHolder) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", pagedListHolder.getPageList());
        map.put("number_of_pages", pagedListHolder.getPageCount());
        map.put("current_page", pagedListHolder.getPage());
        map.put("total_number", pagedListHolder.getSource().size());
        return map;
    }

    private List<UserDtoResponse> selectByAllFilters(List<UserDtoResponse> userDtoResponses, Integer EntFrom, Integer EntTo,
                                                     Integer AETEnglishFrom, Integer AETEnglishTo, boolean fullness, boolean englishCertification) {
        if (EntFrom != null) {
            userDtoResponses.retainAll(selectByENTFrom(EntFrom));
        }
        if (EntTo != null) {
            userDtoResponses.retainAll(selectByENTTo(EntTo));
        }
        if (AETEnglishFrom != null) {
            userDtoResponses.retainAll(selectByAETEnglishFrom(AETEnglishFrom));
        }
        if (AETEnglishTo != null) {
            userDtoResponses.retainAll(selectByAETEnglishTo(AETEnglishTo));
        }
        if (fullness) {
            userDtoResponses.retainAll(checkFirstBlock());
            userDtoResponses.retainAll(checkSecondBlock());
            userDtoResponses.retainAll(checkThirdBlock());
            userDtoResponses.retainAll(checkForthBlock());
        }
        if (englishCertification) {
            userDtoResponses.retainAll(checkEnglishCertification());
        }
        return userDtoResponses;
    }

    private List<UserDtoResponse> selectByIIN(String iin) {
        return personalInfoRepository.findByIinStartingWith(iin).stream().map(value -> new UserFacade()
                .userToUserDtoResponse(value.getUser()))
                .collect(Collectors.toList());
    }

    private List<UserDtoResponse> selectByLocality(String locality) {
        return educationalInfoRepository.findByKzGraduationLocality_Name(locality).stream().map(value -> new UserFacade()
                .userToUserDtoResponse(value.getUser()))
                .collect(Collectors.toList());
    }

    private List<UserDtoResponse> selectByFIO(String FIO) {
        String[] FIOArray = FIO.split(" ");
        if (FIOArray.length >= 3) {
            return userRepository.findByNameContainsAndSurnameContainsAndPatronymicContains(FIOArray[0], FIOArray[1], FIOArray[2]).stream().map(value -> new UserFacade()
                    .userToUserDtoResponse(value))
                    .collect(Collectors.toList());
        }
        return userRepository.findByNameContainsAndSurnameContains(FIOArray[0], FIOArray[1]).stream().map(value ->
                new UserFacade().userToUserDtoResponse(value)).collect(Collectors.toList());
    }

    private List<UserDtoResponse> selectByENTFrom(Integer score) {
       return admissionInfoRepository.findAll().stream().filter(value -> value.getEntDetails() != null && (value.getEntDetails().getMathematicalLiteracyPoint() == null ? 0 : value.getEntDetails().getMathematicalLiteracyPoint()) +
               (value.getEntDetails().getReadingLiteracyPoint() == null ? 0 : value.getEntDetails().getReadingLiteracyPoint()) +
               (value.getEntDetails().getKazakhstanHistoryPoint() == null ? 0 : value.getEntDetails().getKazakhstanHistoryPoint()) + (value.getEntDetails().getProfileSubject1Point() == null ? 0 : value.getEntDetails().getProfileSubject1Point()) +
               (value.getEntDetails().getProfileSubject2Point() == null ? 0 : value.getEntDetails().getProfileSubject2Point()) >= score).map(value -> new UserFacade().userToUserDtoResponse(value.getUser())).collect(Collectors.toList());
    }

    private List<UserDtoResponse> selectByENTTo(Integer score) {
        return admissionInfoRepository.findAll().stream().filter(value -> value.getEntDetails() != null && (value.getEntDetails().getMathematicalLiteracyPoint() == null ? 0 : value.getEntDetails().getMathematicalLiteracyPoint()) +
                (value.getEntDetails().getReadingLiteracyPoint() == null ? 0 : value.getEntDetails().getReadingLiteracyPoint()) +
                (value.getEntDetails().getKazakhstanHistoryPoint() == null ? 0 : value.getEntDetails().getKazakhstanHistoryPoint()) + (value.getEntDetails().getProfileSubject1Point() == null ? 0 : value.getEntDetails().getProfileSubject1Point()) +
                (value.getEntDetails().getProfileSubject2Point() == null ? 0 : value.getEntDetails().getProfileSubject2Point()) <= score).map(value -> new UserFacade().userToUserDtoResponse(value.getUser())).collect(Collectors.toList());
    }

    private List<UserDtoResponse> selectByAETEnglishFrom(Integer score) {
        return admissionInfoRepository.findAll().stream().filter(value -> ((Strings.isNotEmpty(value.getCombinedExamPointEnglish()) && value.getCombinedExamPointEnglish().matches("[-+]?\\d+")
        && Strings.isNotEmpty(value.getCombinedExamPointInformatics()) && value.getCombinedExamPointInformatics().matches("[-+]?\\d+") && Integer.parseInt(value.getCombinedExamPointEnglish()) + Integer.parseInt(value.getCombinedExamPointInformatics()) >= score))
                || ((Strings.isNotEmpty(value.getCombinedExamPointEnglish()) && value.getCombinedExamPointEnglish().matches("[-+]?\\d+") && Integer.parseInt(value.getCombinedExamPointEnglish()) >= score))
                || ((Strings.isNotEmpty(value.getCombinedExamPointInformatics()) && value.getCombinedExamPointInformatics().matches("[-+]?\\d+") && Integer.parseInt(value.getCombinedExamPointInformatics()) >= score)))
                .map(value -> new UserFacade().userToUserDtoResponse(value.getUser())).collect(Collectors.toList());
    }

    private List<UserDtoResponse> selectByAETEnglishTo(Integer score) {
        return admissionInfoRepository.findAll().stream().filter(value -> ((Strings.isNotEmpty(value.getCombinedExamPointEnglish()) && value.getCombinedExamPointEnglish().matches("[-+]?\\d+")
                && Strings.isNotEmpty(value.getCombinedExamPointInformatics()) && value.getCombinedExamPointInformatics().matches("[-+]?\\d+") && Integer.parseInt(value.getCombinedExamPointEnglish()) + Integer.parseInt(value.getCombinedExamPointInformatics()) <= score))
                || ((Strings.isNotEmpty(value.getCombinedExamPointEnglish()) && value.getCombinedExamPointEnglish().matches("[-+]?\\d+") && Integer.parseInt(value.getCombinedExamPointEnglish()) <= score))
                || ((Strings.isNotEmpty(value.getCombinedExamPointInformatics()) && value.getCombinedExamPointInformatics().matches("[-+]?\\d+") && Integer.parseInt(value.getCombinedExamPointInformatics()) <= score)))
                .map(value -> new UserFacade().userToUserDtoResponse(value.getUser())).collect(Collectors.toList());
    }

    private List<UserDtoResponse> checkFirstBlock() {
        return personalInfoRepository.findAll().stream().filter(value -> value.getImage3x4() != null && value.getIin() != null
        && value.getDateOfBirth() != null && value.getIsMale() != null && value.getNationality() != null && value.getCitizenship() != null
        && value.getResidenceAddress() != null && value.getLifeAddress() != null && value.getFirstParentName() != null
        && value.getFirstParentSurname() != null && value.getFirstParentPhoneNumber() != null).map(value -> new UserFacade().userToUserDtoResponse(value.getUser()))
                .collect(Collectors.toList());
    }

    private List<UserDtoResponse> checkSecondBlock() {
        return documentInfoRepository.findAll().stream().filter(value -> value.getIdentityDocumentType() != null && value.getIdentityDocumentNumber() != null
        && value.getIdentityDocumentIssueDate() != null && value.getIdentityDocumentValidityPeriod() != null && value.getDocumentIssuingAuthority() != null
        && value.getIdentityDocumentScanFront() != null && value.getIdentityDocumentScanBack() != null && value.getForm086() != null)
                .map(value -> new UserFacade().userToUserDtoResponse(value.getUser())).collect(Collectors.toList());
    }

    private List<UserDtoResponse> checkThirdBlock() {
        return admissionInfoRepository.findAll().stream().filter(value -> value.getAcademicDegree() != null && value.getEducationalProgram() != null
        && value.getExamType() != null && value.getEnglishCertificateType() != null).map(value -> new UserFacade().userToUserDtoResponse(value.getUser()))
                .collect(Collectors.toList());
    }

    private List<UserDtoResponse> checkForthBlock() {
        return educationalInfoRepository.findAll().stream().filter(value -> value.getEducationalInstitution() != null).map(value ->
                new UserFacade().userToUserDtoResponse(value.getUser())).collect(Collectors.toList());
    }

    private List<UserDtoResponse> checkEnglishCertification() {
        return documentInfoRepository.findAll().stream().filter(value -> value.getEnglishCertificate() != null).map(value -> new UserFacade().userToUserDtoResponse(value.getUser()))
                .collect(Collectors.toList());
    }

}
