package kz.edy.astanait.product.service.secretary;

import kz.edy.astanait.product.dto.requestDto.secretary.MarkExamsDtoRequest;
import kz.edy.astanait.product.exception.domain.AdmissionInfoNotFoundException;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.enrollee.AdmissionInfo;
import kz.edy.astanait.product.repository.entrollee.AdmissionInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarkExamsService {

    private final AdmissionInfoRepository admissionInfoRepository;

    @Autowired
    public MarkExamsService(AdmissionInfoRepository admissionInfoRepository) {
        this.admissionInfoRepository = admissionInfoRepository;
    }

    public void markExams(MarkExamsDtoRequest markExamsDtoRequest) {
        Optional<AdmissionInfo> admissionInfo = admissionInfoRepository.findByUserId(markExamsDtoRequest.getUserId());
        if (admissionInfo.isPresent()) {
            if (markExamsDtoRequest.getCombinedExamPointInformatics() != null) {
                admissionInfo.get().setCombinedExamPointInformatics(markExamsDtoRequest.getCombinedExamPointInformatics());
            }
            if (markExamsDtoRequest.getCombinedExamPointEnglish() != null) {
                admissionInfo.get().setCombinedExamPointEnglish(markExamsDtoRequest.getCombinedExamPointEnglish());
            }
            if (markExamsDtoRequest.getInterviewPoints() != null) {
                admissionInfo.get().setInterviewPoints(markExamsDtoRequest.getInterviewPoints());
            }
            if (markExamsDtoRequest.getCreativeExamPoints() != null) {
                admissionInfo.get().setCreativeExamPoints(markExamsDtoRequest.getCreativeExamPoints());
            }
            admissionInfoRepository.save(admissionInfo.get());
        } else  {
            throw new AdmissionInfoNotFoundException("admission info not found by user's id: " + markExamsDtoRequest.getUserId());
        }
    }
}
