package kz.edy.astanait.product.controller.enrollee;
import kz.edy.astanait.product.dto.requestDto.enrollee.EducationalInfoDtoRequest;
import kz.edy.astanait.product.dto.responseDto.enrollee.EducationalInfoDtoResponse;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.service.enrollee.EducationalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/educational-info")
public class EducationalInfoController extends ExceptionHandling {

    private final EducationalInfoService educationalInfoService;

    @Autowired
    public EducationalInfoController(EducationalInfoService educationalInfoService) {
        this.educationalInfoService = educationalInfoService;
    }

    @GetMapping("/")
    public ResponseEntity<EducationalInfoDtoResponse> getEducationalInfo(Principal principal) {
        EducationalInfoDtoResponse educationalInfoDtoResponse = educationalInfoService.getEducationalInfo(principal);
        return new ResponseEntity<>(educationalInfoDtoResponse, OK);
    }

    @GetMapping("/get-by-one")
    public ResponseEntity<EducationalInfoDtoResponse> getEducationalInfoByOne(@RequestParam(name = "user_id") Long user_id) {
        EducationalInfoDtoResponse educationalInfoDtoResponse = educationalInfoService.getEducationInfoByOne(user_id);
        return new ResponseEntity<>(educationalInfoDtoResponse, OK);
    }

    @PostMapping("/save-educational-info")
    public ResponseEntity<HttpStatus> saveEducationalInfo(@RequestBody EducationalInfoDtoRequest educationalInfoDtoRequest, Principal principal) {
        educationalInfoService.saveEducationalInfo(educationalInfoDtoRequest, principal);
        return new ResponseEntity<>(OK);
    }
}
