package kz.edy.astanait.product.service.enrollee;

import kz.edy.astanait.product.dto.requestDto.enrollee.PersonalInfoDtoRequest;
import kz.edy.astanait.product.dto.responseDto.enrollee.DocumentInfoDtoResponse;
import kz.edy.astanait.product.dto.responseDto.enrollee.PersonalInfoDtoResponse;
import kz.edy.astanait.product.exception.domain.DocumentInfoNotFoundException;
import kz.edy.astanait.product.exception.domain.PersonalInfoNotFoundException;
import kz.edy.astanait.product.model.enrollee.DocumentInfo;
import kz.edy.astanait.product.model.enrollee.PersonalInfo;
import kz.edy.astanait.product.model.document.UniversityFile;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.repository.document.UniversityFileRepository;
import kz.edy.astanait.product.repository.location.CountryRepository;
import kz.edy.astanait.product.repository.location.NationalityRepository;
import kz.edy.astanait.product.repository.entrollee.PersonalInfoRepository;
import kz.edy.astanait.product.repository.secretary.ConfirmBlocksRepository;
import kz.edy.astanait.product.utils.facade.enrollee.DocumentInfoFacade;
import kz.edy.astanait.product.utils.facade.enrollee.PersonalInfoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;
    private final NationalityRepository nationalityRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final UniversityFileRepository universityFileRepository;
    private final ConfirmBlocksRepository confirmBlocksRepository;

    @Autowired
    public PersonalInfoService(PersonalInfoRepository personalInfoRepository, NationalityRepository nationalityRepository, CountryRepository countryRepository, UserRepository userRepository, UniversityFileRepository universityFileRepository, ConfirmBlocksRepository confirmBlocksRepository) {
        this.personalInfoRepository = personalInfoRepository;
        this.confirmBlocksRepository = confirmBlocksRepository;
        this.nationalityRepository = nationalityRepository;
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
        this.universityFileRepository = universityFileRepository;
    }

    public PersonalInfoDtoResponse getPersonalInfoByOne(Long id) {
        Optional<PersonalInfo> personalInfo = personalInfoRepository.findByUserId(id);
        if (personalInfo.isPresent()) {
            return new PersonalInfoFacade().personalInfoToPersonalInfoDtoResponse(personalInfo.get());
        }
        throw new PersonalInfoNotFoundException("Personal info not found by user id: " + id);
    }

    public PersonalInfoDtoResponse getPersonalInfo(Principal principal) {
        Optional<PersonalInfo> personalInfo = personalInfoRepository.findByUserEmail(principal.getName());
        return personalInfo.map(value -> new PersonalInfoFacade().personalInfoToPersonalInfoDtoResponse(value)).orElse(null);
    }

    public void savePersonalInfo(PersonalInfoDtoRequest personalInfoDtoRequest, Principal principal) {
        Optional<PersonalInfo> personalInfo = personalInfoRepository.findByUserEmail(principal.getName());
        if (personalInfo.isPresent()) {
            confirmBlocksRepository.findByUserEmail(principal.getName()).ifPresent(value -> {if(!value.isFirstBlock()) {
                manipulatePersonalInfo(personalInfo.get(), personalInfoDtoRequest, principal.getName());
            }});
        } else {
            PersonalInfo createPersonalInfo = new PersonalInfo();
            manipulatePersonalInfo(createPersonalInfo, personalInfoDtoRequest, principal.getName());
        }
    }

    private void manipulatePersonalInfo(PersonalInfo personalInfo, PersonalInfoDtoRequest personalInfoDtoRequest, String email) {
        userRepository.findUserByEmail(email).ifPresent(personalInfo::setUser);
        if (personalInfoDtoRequest.getImage3x4Id() != null) {
            Optional<UniversityFile> universityFile = universityFileRepository.findById(personalInfoDtoRequest.getImage3x4Id());
            if (universityFile.isPresent()) {
                if (universityFile.get().getUser().getEmail().equals(email)) {
                    personalInfo.setImage3x4(universityFile.get());
                }
            }
        }
        if (personalInfoDtoRequest.getIin() != null) {
            personalInfo.setIin(personalInfoDtoRequest.getIin());
        }
        if (personalInfoDtoRequest.getDateOfBirth() != null) {
            personalInfo.setDateOfBirth(personalInfoDtoRequest.getDateOfBirth());
        }
        if (personalInfoDtoRequest.getIsMale() != null) {
            personalInfo.setIsMale(personalInfoDtoRequest.getIsMale());
        }
        if (personalInfoDtoRequest.getNationality() != null) {
            nationalityRepository.findById(personalInfoDtoRequest.getNationality()).ifPresent(personalInfo::setNationality);
        }
        if (personalInfoDtoRequest.getCitizenship() != null) {
            countryRepository.findById(personalInfoDtoRequest.getCitizenship()).ifPresent(personalInfo::setCitizenship);
        }
        if (personalInfoDtoRequest.getResidenceAddress() != null) {
            personalInfo.setResidenceAddress(personalInfoDtoRequest.getResidenceAddress());
        }
        if (personalInfoDtoRequest.getLifeAddress() != null) {
            personalInfo.setLifeAddress(personalInfoDtoRequest.getLifeAddress());
        }
        if (personalInfoDtoRequest.getFirstParentName() != null) {
            personalInfo.setFirstParentName(personalInfoDtoRequest.getFirstParentName());
        }
        if (personalInfoDtoRequest.getFirstParentSurname() != null) {
            personalInfo.setFirstParentSurname(personalInfoDtoRequest.getFirstParentSurname());
        }
        if (personalInfoDtoRequest.getFirstParentPatronymic() != null) {
            personalInfo.setFirstParentPatronymic(personalInfoDtoRequest.getFirstParentPatronymic());
        }
        if (personalInfoDtoRequest.getFirstParentPhoneNumber() != null) {
            personalInfo.setFirstParentPhoneNumber(personalInfoDtoRequest.getFirstParentPhoneNumber());
        }
        if (personalInfoDtoRequest.getSecondParentName() != null) {
            personalInfo.setSecondParentName(personalInfoDtoRequest.getSecondParentName());
        }
        if (personalInfoDtoRequest.getSecondParentSurname() != null) {
            personalInfo.setSecondParentSurname(personalInfoDtoRequest.getSecondParentSurname());
        }
        if (personalInfoDtoRequest.getSecondParentPatronymic() != null) {
            personalInfo.setSecondParentPatronymic(personalInfoDtoRequest.getSecondParentPatronymic());
        }
        if (personalInfoDtoRequest.getSecondParentPhoneNumber() != null) {
            personalInfo.setSecondParentPhoneNumber(personalInfoDtoRequest.getSecondParentPhoneNumber());
        }
        personalInfoRepository.save(personalInfo);
    }
}
