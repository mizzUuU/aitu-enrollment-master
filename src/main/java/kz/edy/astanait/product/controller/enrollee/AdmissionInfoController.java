package kz.edy.astanait.product.controller.enrollee;

import kz.edy.astanait.product.dto.requestDto.enrollee.AdmissionInfoDtoRequest;
import kz.edy.astanait.product.dto.responseDto.enrollee.AdmissionInfoDtoResponse;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.service.enrollee.AdmissionInfoService;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/v1/admission-info")
public class AdmissionInfoController extends ExceptionHandling {

    private final AdmissionInfoService admissionInfoService;

    public AdmissionInfoController(AdmissionInfoService admissionInfoService) {
        this.admissionInfoService = admissionInfoService;
    }

    @GetMapping("/")
    public ResponseEntity<AdmissionInfoDtoResponse> getAdmissionInfo(Principal principal) {
        AdmissionInfoDtoResponse admissionInfoDtoResponse = admissionInfoService.getAdmissionInfo(principal);
        return new ResponseEntity<>(admissionInfoDtoResponse, OK);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<AdmissionInfoDtoResponse> getAdmissionInfoByOne(@RequestParam(name = "user_id") Long user_id) {
        AdmissionInfoDtoResponse admissionInfoDtoResponse = admissionInfoService.getAdmissionInfoByOne(user_id);
        return new ResponseEntity<>(admissionInfoDtoResponse, OK);
    }

    @PostMapping("/save-admission-info")
    public ResponseEntity<HttpStatus> saveAdmissionInfo(@RequestBody AdmissionInfoDtoRequest admissionInfoDtoRequest, Principal principal) {
        admissionInfoService.saveAdmissionInfo(admissionInfoDtoRequest, principal);
        return new ResponseEntity<>(OK);
    }
}
