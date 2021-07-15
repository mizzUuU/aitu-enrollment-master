package kz.edy.astanait.product.utils.facade.enrollee;

import kz.edy.astanait.product.dto.responseDto.enrollee.PersonalInfoDtoResponse;
import kz.edy.astanait.product.model.enrollee.PersonalInfo;
import kz.edy.astanait.product.utils.facade.UserFacade;
import kz.edy.astanait.product.utils.facade.document.UniversityFileFacade;
import kz.edy.astanait.product.utils.facade.location.CountryFacade;
import kz.edy.astanait.product.utils.facade.location.LocalityFacade;
import kz.edy.astanait.product.utils.facade.location.NationalityFacade;

public class PersonalInfoFacade {

    public PersonalInfoDtoResponse personalInfoToPersonalInfoDtoResponse(PersonalInfo personalInfo) {
        PersonalInfoDtoResponse personalInfoDtoResponse = new PersonalInfoDtoResponse();
        personalInfoDtoResponse.setId(personalInfo.getId());
        if (personalInfo.getUser() != null) {
            personalInfoDtoResponse.setUser(new UserFacade().userToUserDtoResponse(personalInfo.getUser()));
        }
        if (personalInfo.getImage3x4() != null) {
            personalInfoDtoResponse.setImage3x4(new UniversityFileFacade().universityFileToUniversityFileDtoResponse(personalInfo.getImage3x4()));
        }
        if (personalInfo.getIin() != null) {
            personalInfoDtoResponse.setIin(personalInfo.getIin());
        }
        if (personalInfo.getDateOfBirth() != null) {
            personalInfoDtoResponse.setDateOfBirth(personalInfo.getDateOfBirth());
        }
        if (personalInfo.getIsMale() != null) {
            personalInfoDtoResponse.setIsMale(personalInfo.getIsMale());
        }
        if (personalInfo.getNationality() != null) {
            personalInfoDtoResponse.setNationality(new NationalityFacade().nationalityToNationalityDtoResponse(personalInfo.getNationality()));
        }
        if(personalInfo.getCitizenship() != null) {
            personalInfoDtoResponse.setCitizenship(new CountryFacade().countryToCountryDtoResponse(personalInfo.getCitizenship()));
        }
        if (personalInfo.getResidenceAddress() != null) {
            personalInfoDtoResponse.setResidenceAddress(personalInfo.getResidenceAddress());
        }
        if (personalInfo.getLifeAddress() != null) {
            personalInfoDtoResponse.setLifeAddress(personalInfo.getLifeAddress());
        }
        if (personalInfo.getFirstParentName() != null) {
            personalInfoDtoResponse.setFirstParentName(personalInfo.getFirstParentName());
        }
        if (personalInfo.getFirstParentSurname() != null) {
            personalInfoDtoResponse.setFirstParentSurname(personalInfo.getFirstParentSurname());
        }
        if (personalInfo.getFirstParentPatronymic() != null) {
            personalInfoDtoResponse.setFirstParentPatronymic(personalInfo.getFirstParentPatronymic());
        }
        if (personalInfo.getFirstParentPhoneNumber() != null) {
            personalInfoDtoResponse.setFirstParentPhoneNumber(personalInfo.getFirstParentPhoneNumber());
        }
        if (personalInfo.getSecondParentName() != null) {
            personalInfoDtoResponse.setSecondParentName(personalInfo.getSecondParentName());
        }
        if (personalInfo.getSecondParentSurname() != null) {
            personalInfoDtoResponse.setSecondParentSurname(personalInfo.getSecondParentSurname());
        }
        if (personalInfo.getSecondParentPatronymic() != null) {
            personalInfoDtoResponse.setSecondParentPatronymic(personalInfo.getSecondParentPatronymic());
        }
        if (personalInfo.getSecondParentPhoneNumber() != null) {
            personalInfoDtoResponse.setSecondParentPhoneNumber(personalInfo.getSecondParentPhoneNumber());
        }
        return personalInfoDtoResponse;
    }


}
