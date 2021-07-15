package kz.edy.astanait.product.dto.responseDto.enrollee;

import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.UniversityFileDtoResponse;
import kz.edy.astanait.product.dto.responseDto.location.CountryDtoResponse;
import kz.edy.astanait.product.dto.responseDto.location.LocalityDtoResponse;
import kz.edy.astanait.product.dto.responseDto.location.NationalityDtoResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Data
public class PersonalInfoDtoResponse {
    private Long id;

    private UserDtoResponse user;

    private UniversityFileDtoResponse image3x4;

    private String iin;

    private LocalDate DateOfBirth;

    private Boolean isMale;

    private NationalityDtoResponse nationality;

    private CountryDtoResponse citizenship;

    private String residenceAddress;

    private String lifeAddress;

    private String firstParentName;

    private String firstParentSurname;

    private String firstParentPatronymic;

    private String firstParentPhoneNumber;

    private String secondParentName;

    private String secondParentSurname;

    private String secondParentPatronymic;

    private String secondParentPhoneNumber;
}
