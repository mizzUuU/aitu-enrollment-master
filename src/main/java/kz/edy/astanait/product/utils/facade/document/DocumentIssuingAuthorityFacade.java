package kz.edy.astanait.product.utils.facade.document;

import kz.edy.astanait.product.dto.responseDto.document.DocumentIssuingAuthorityDtoResponse;
import kz.edy.astanait.product.model.document.DocumentIssuingAuthority;

public class DocumentIssuingAuthorityFacade {

    public DocumentIssuingAuthorityDtoResponse documentIssuingAuthorityToDocumentIssuingAuthorityDtoResponse(DocumentIssuingAuthority documentIssuingAuthority) {
        DocumentIssuingAuthorityDtoResponse documentIssuingAuthorityDtoResponse = new DocumentIssuingAuthorityDtoResponse();
        documentIssuingAuthorityDtoResponse.setId(documentIssuingAuthority.getId());
        documentIssuingAuthorityDtoResponse.setName(documentIssuingAuthority.getName());
        return documentIssuingAuthorityDtoResponse;
    }
}
