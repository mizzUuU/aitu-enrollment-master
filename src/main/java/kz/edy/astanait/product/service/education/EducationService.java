package kz.edy.astanait.product.service.education;

import kz.edy.astanait.product.dto.responseDto.education.*;
import kz.edy.astanait.product.model.education.*;
import kz.edy.astanait.product.repository.education.*;
import kz.edy.astanait.product.utils.facade.education.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EducationService {

   private final AcademicDegreeRepository academicDegreeRepository;
   private final EducationalProgramGroupRepository educationalProgramGroupRepository;
   private final EducationalProgramRepository educationalProgramRepository;
   private final ExamTypeRepository examTypeRepository;
   private final EnglishCertificateTypeRepository englishCertificateTypeRepository;
   private final EducationalInstitutionRepository educationalInstitutionRepository;
   private final DistinctionMarkTypeRepository distinctionMarkTypeRepository;
   private final EducationInstitutionTypeRepository educationInstitutionTypeRepository;

   @Autowired
    public EducationService(AcademicDegreeRepository academicDegreeRepository, EducationalProgramGroupRepository educationalProgramGroupRepository,
                            EducationalProgramRepository educationalProgramRepository, ExamTypeRepository examTypeRepository,
                            EnglishCertificateTypeRepository englishCertificateTypeRepository, EducationalInstitutionRepository educationalInstitutionRepository, DistinctionMarkTypeRepository distinctionMarkTypeRepository, EducationInstitutionTypeRepository educationInstitutionTypeRepository) {
        this.academicDegreeRepository = academicDegreeRepository;
        this.educationalProgramGroupRepository = educationalProgramGroupRepository;
        this.educationalProgramRepository = educationalProgramRepository;
        this.examTypeRepository = examTypeRepository;
        this.englishCertificateTypeRepository = englishCertificateTypeRepository;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.distinctionMarkTypeRepository = distinctionMarkTypeRepository;
        this.educationInstitutionTypeRepository = educationInstitutionTypeRepository;
   }

    public List<AcademicDegreeDtoResponse> getAllAcademicDegrees() {
        List<AcademicDegree> academicDegrees = academicDegreeRepository.findAll();
        List<AcademicDegreeDtoResponse> academicDegreeDtoResponses = new ArrayList<>();

        academicDegrees.forEach(value -> academicDegreeDtoResponses.add(new AcademicDegreeFacade().academicDegreeToAcademicDegreeDtoResponse(value)));
        return academicDegreeDtoResponses;
    }

    public List<EducationalProgramGroupDtoResponse> getAllEducationalProgramGroups() {
        List<EducationalProgramGroup> educationalProgramGroups = educationalProgramGroupRepository.findAll();
        List<EducationalProgramGroupDtoResponse> educationalProgramGroupDtoResponses = new ArrayList<>();

        educationalProgramGroups.forEach(value -> educationalProgramGroupDtoResponses.add(new EducationalProgramGroupFacade().educationalProgramGroupToEducationalProgramGroupDtoResponse(value)));
        return educationalProgramGroupDtoResponses;
    }

    public List<EducationalProgramDtoResponse> getAllEducationalPrograms() {
        List<EducationalProgram> educationalPrograms = educationalProgramRepository.findAll();
        List<EducationalProgramDtoResponse> educationalProgramGroupDtoResponses = new ArrayList<>();

        educationalPrograms.forEach(value -> educationalProgramGroupDtoResponses.add(new EducationalProgramFacade().educationProgramToEducationProgramDtoResponse(value)));
        return educationalProgramGroupDtoResponses;
    }

    public List<ExamTypeDtoResponse> getAllExamTypes() {
        List<ExamType> examTypes = examTypeRepository.findAll();
        List<ExamTypeDtoResponse> examTypeDtoResponses = new ArrayList<>();

        examTypes.forEach(value -> examTypeDtoResponses.add(new ExamTypeFacade().examTypeToExamTypeDtoResponse(value)));
        return examTypeDtoResponses;
    }


    public List<EnglishCertificateTypeDtoResponse> getAllEnglishCertificateTypes() {
        List<EnglishCertificateType> englishCertificateTypes = englishCertificateTypeRepository.findAll();
        List<EnglishCertificateTypeDtoResponse> englishCertificateTypeDtoResponses = new ArrayList<>();

        englishCertificateTypes.forEach(value -> englishCertificateTypeDtoResponses.add(new EnglishCertificateTypeFacade().englishCertificateTypeToEnglishCertificateTypeDtoResponse(value)));
        return englishCertificateTypeDtoResponses;
    }

    public List<EducationalInstitutionDtoResponse> getAllEducationalInstitutions() {
        List<EducationalInstitution> educationalInstitutions = educationalInstitutionRepository.findAll();
        List<EducationalInstitutionDtoResponse> educationalInstitutionDtoResponses = new ArrayList<>();

        educationalInstitutions.forEach(value -> educationalInstitutionDtoResponses.add(new EducationalInstitutionFacade().educationalInstitutionToEducationalInstitutionDtoResponse(value)));
        return educationalInstitutionDtoResponses;
    }

    public List<EducationInstitutionTypeDtoResponse> getAllEducationInstitutionsType() {
        List<EducationInstitutionType> educationInstitutionTypes = educationInstitutionTypeRepository.findAll();
        List<EducationInstitutionTypeDtoResponse> educationInstitutionTypeDtoResponses = new ArrayList<>();

        educationInstitutionTypes.forEach(value -> educationInstitutionTypeDtoResponses.add(new EducationInstitutionTypeFacade().educationInstitutionTypeToEducationInstitutionTypeDtoResponse(value)));
        return educationInstitutionTypeDtoResponses;
    }

    public List<DistinctionMarkTypeDtoResponse> getAllDistinctionMarkType() {
        List<DistinctionMarkType> distinctionMarkTypes = distinctionMarkTypeRepository.findAll();
        List<DistinctionMarkTypeDtoResponse> distinctionMarkTypeDtoResponses = new ArrayList<>();

        distinctionMarkTypes.forEach(value -> distinctionMarkTypeDtoResponses.add(new DistinctionMarkTypeFacade().distinctionMarkTypeToDistinctionMarkTypeDtoResponse(value)));
        return distinctionMarkTypeDtoResponses;
    }

}
