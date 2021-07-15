package kz.edy.astanait.product.utils.facade.enrollee;

import kz.edy.astanait.product.dto.responseDto.enrollee.DocumentInfoDtoResponse;
import kz.edy.astanait.product.model.enrollee.DocumentInfo;
import kz.edy.astanait.product.utils.facade.UserFacade;
import kz.edy.astanait.product.utils.facade.document.DocumentIssuingAuthorityFacade;
import kz.edy.astanait.product.utils.facade.document.IdentityDocumentTypeFacade;
import kz.edy.astanait.product.utils.facade.document.UniversityFileFacade;

public class DocumentInfoFacade {

    public DocumentInfoDtoResponse documentDtoToDocumentResponse(DocumentInfo documentInfo) {
        DocumentInfoDtoResponse documentInfoDtoResponse = new DocumentInfoDtoResponse();
        documentInfoDtoResponse.setId(documentInfo.getId());
        if (documentInfo.getUser() != null) {
            documentInfoDtoResponse.setUser(new UserFacade().userToUserDtoResponse(documentInfo.getUser()));
        }
        if (documentInfo.getIdentityDocumentType() != null) {
            documentInfoDtoResponse.setIdentityDocumentType(new IdentityDocumentTypeFacade().identityDocumentTypeToIdentityDocumentTypeDtoResponse(documentInfo.getIdentityDocumentType()));
        }
        if (documentInfo.getOtherDocumentType() != null) {
            documentInfoDtoResponse.setOtherDocumentType(documentInfo.getOtherDocumentType());
        }
        if (documentInfo.getIdentityDocumentNumber() != null) {
            documentInfoDtoResponse.setIdentityDocumentNumber(documentInfo.getIdentityDocumentNumber());
        }
        if (documentInfo.getIdentityDocumentIssueDate() != null) {
            documentInfoDtoResponse.setIdentityDocumentIssueDate(documentInfo.getIdentityDocumentIssueDate());
        }
        if (documentInfo.getDocumentIssuingAuthority() != null) {
            documentInfoDtoResponse.setDocumentIssuingAuthority(new DocumentIssuingAuthorityFacade().documentIssuingAuthorityToDocumentIssuingAuthorityDtoResponse(documentInfo.getDocumentIssuingAuthority()));
        }
        if (documentInfo.getIdentityDocumentValidityPeriod() != null) {
            documentInfoDtoResponse.setIdentityDocumentValidityPeriod(documentInfo.getIdentityDocumentValidityPeriod());
        }
        if (documentInfo.getIdentityDocumentScanFront() != null) {
            documentInfoDtoResponse.setIdentityDocumentScanFront(new UniversityFileFacade().universityFileToUniversityFileDtoResponse(documentInfo.getIdentityDocumentScanFront()));
        }
        if (documentInfo.getIdentityDocumentScanBack() != null) {
            documentInfoDtoResponse.setIdentityDocumentScanBack(new UniversityFileFacade().universityFileToUniversityFileDtoResponse(documentInfo.getIdentityDocumentScanBack()));
        }
        if (documentInfo.getForm086() != null) {
            documentInfoDtoResponse.setForm086(new UniversityFileFacade().universityFileToUniversityFileDtoResponse(documentInfo.getForm086()));
        }

        return documentInfoDtoResponse;
    }
}
