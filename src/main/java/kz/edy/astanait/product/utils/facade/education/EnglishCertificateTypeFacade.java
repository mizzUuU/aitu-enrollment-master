package kz.edy.astanait.product.utils.facade.education;

import kz.edy.astanait.product.dto.responseDto.education.EnglishCertificateTypeDtoResponse;
import kz.edy.astanait.product.model.education.EnglishCertificateType;

public class EnglishCertificateTypeFacade {

    public EnglishCertificateTypeDtoResponse englishCertificateTypeToEnglishCertificateTypeDtoResponse(EnglishCertificateType englishCertificateType) {
        EnglishCertificateTypeDtoResponse englishCertificateTypeDtoResponse = new EnglishCertificateTypeDtoResponse();
        englishCertificateTypeDtoResponse.setId(englishCertificateType.getId());
        englishCertificateTypeDtoResponse.setName(englishCertificateType.getName());
        return englishCertificateTypeDtoResponse;
    }
}
