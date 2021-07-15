package kz.edy.astanait.product.controller.education;

import kz.edy.astanait.product.dto.responseDto.education.*;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.model.education.EducationalProgram;
import kz.edy.astanait.product.model.education.EducationalProgramGroup;
import kz.edy.astanait.product.repository.education.AcademicDegreeRepository;
import kz.edy.astanait.product.service.education.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/education")
public class EducationController extends ExceptionHandling {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }


    @GetMapping("/academic-degree")
    public ResponseEntity<List<AcademicDegreeDtoResponse>> getAllAcademicDegrees() {
        List<AcademicDegreeDtoResponse> academicDegreeDtoResponses = educationService.getAllAcademicDegrees();
        return new ResponseEntity<>(academicDegreeDtoResponses, OK);
    }

    @GetMapping("/education-program-group")
    public ResponseEntity<List<EducationalProgramGroupDtoResponse>> getAllEducationalProgramGroups() {
        List<EducationalProgramGroupDtoResponse> educationalProgramGroupDtoResponses = educationService.getAllEducationalProgramGroups();
        return new ResponseEntity<>(educationalProgramGroupDtoResponses, OK);
    }

    @GetMapping("/education-program")
    public ResponseEntity<List<EducationalProgramDtoResponse>> getAllEducationalPrograms() {
        List<EducationalProgramDtoResponse> educationalProgramDtoResponses = educationService.getAllEducationalPrograms();
        return new ResponseEntity<>(educationalProgramDtoResponses, OK);
    }

    @GetMapping("/exam-type")
    public ResponseEntity<List<ExamTypeDtoResponse>> getAllExamTypes() {
        List<ExamTypeDtoResponse> examTypeDtoResponses = educationService.getAllExamTypes();
        return new ResponseEntity<>(examTypeDtoResponses, OK);
    }


    @GetMapping("/english-certificate-type")
    public ResponseEntity<List<EnglishCertificateTypeDtoResponse>> getAllEnglishCertificateTypes() {
        List<EnglishCertificateTypeDtoResponse> englishCertificateTypeDtoResponses = educationService.getAllEnglishCertificateTypes();
        return new ResponseEntity<>(englishCertificateTypeDtoResponses, OK);
    }

    @GetMapping("/education-institutions")
    public ResponseEntity<List<EducationalInstitutionDtoResponse>> getAllEducationInstitutions() {
        List<EducationalInstitutionDtoResponse> educationalInstitutionDtoResponses = educationService.getAllEducationalInstitutions();
        return new ResponseEntity<>(educationalInstitutionDtoResponses, OK);
    }

    @GetMapping("/education-institutions-type")
    public ResponseEntity<List<EducationInstitutionTypeDtoResponse>> getAllEducationInstitutionsType() {
        List<EducationInstitutionTypeDtoResponse> educationInstitutionTypeDtoResponses = educationService.getAllEducationInstitutionsType();
        return new ResponseEntity<>(educationInstitutionTypeDtoResponses, OK);
    }

    @GetMapping("/distinction-mark-type")
    public ResponseEntity<List<DistinctionMarkTypeDtoResponse>> getAllDistinctionMarkTypes() {
        List<DistinctionMarkTypeDtoResponse> distinctionMarkTypeDtoResponses = educationService.getAllDistinctionMarkType();
        return new ResponseEntity<>(distinctionMarkTypeDtoResponses, OK);
    }


}
