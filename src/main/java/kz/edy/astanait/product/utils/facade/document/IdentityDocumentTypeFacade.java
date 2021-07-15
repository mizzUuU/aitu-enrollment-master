package kz.edy.astanait.product.utils.facade.document;

import kz.edy.astanait.product.dto.responseDto.document.IdentityDocumentTypeDtoResponse;
import kz.edy.astanait.product.model.document.IdentityDocumentType;

public class IdentityDocumentTypeFacade {

    public IdentityDocumentTypeDtoResponse identityDocumentTypeToIdentityDocumentTypeDtoResponse(IdentityDocumentType identityDocumentType) {
        IdentityDocumentTypeDtoResponse identityDocumentTypeDtoResponse = new IdentityDocumentTypeDtoResponse();
        identityDocumentTypeDtoResponse.setId(identityDocumentType.getId());
        identityDocumentTypeDtoResponse.setName(identityDocumentType.getName());
        return identityDocumentTypeDtoResponse;
    }
}
