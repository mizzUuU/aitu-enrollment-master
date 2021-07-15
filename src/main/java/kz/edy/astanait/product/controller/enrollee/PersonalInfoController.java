package kz.edy.astanait.product.controller.enrollee;

import kz.edy.astanait.product.dto.requestDto.enrollee.PersonalInfoDtoRequest;
import kz.edy.astanait.product.dto.responseDto.enrollee.PersonalInfoDtoResponse;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.service.enrollee.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/v1/personal-info")
public class PersonalInfoController extends ExceptionHandling {

    private final PersonalInfoService personalInfoService;

    @Autowired
    public PersonalInfoController(PersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }

    @GetMapping("/")
    public ResponseEntity<PersonalInfoDtoResponse> getPersonalInfo(Principal principal) {
        PersonalInfoDtoResponse personalInfoDtoResponse = personalInfoService.getPersonalInfo(principal);
        return new ResponseEntity<>(personalInfoDtoResponse, OK);
    }

    @GetMapping("/get-by-one")
    public ResponseEntity<PersonalInfoDtoResponse> getPersonalInfoByOne(@RequestParam(name = "user_id") Long user_id) {
        PersonalInfoDtoResponse personalInfoDtoResponse = personalInfoService.getPersonalInfoByOne(user_id);
        return new ResponseEntity<>(personalInfoDtoResponse, OK);
    }

    @PostMapping("/save-profile-info")
    public ResponseEntity<HttpStatus> saveProfileInfo(@RequestBody PersonalInfoDtoRequest personalInfoDtoRequest, Principal principal) {
        personalInfoService.savePersonalInfo(personalInfoDtoRequest, principal);
        return new ResponseEntity<>(OK);
    }
}
