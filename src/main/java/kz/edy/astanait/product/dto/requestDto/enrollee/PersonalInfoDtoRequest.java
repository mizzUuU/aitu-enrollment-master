package kz.edy.astanait.product.dto.requestDto.enrollee;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PersonalInfoDtoRequest {

    private Long id;

    private Long image3x4Id;

    private String iin;

    private LocalDate dateOfBirth;

    private Boolean isMale;

    private Long nationality;

    private Long citizenship;

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
