package kz.edy.astanait.product.dto.requestDto.enrollee;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class DocumentInfoDtoRequest {

    private Long id;

    private Long identityDocumentTypeId;

    private String otherDocumentType;

    private String identityDocumentNumber;

    private LocalDate identityDocumentIssueDate;

    private Long documentIssuingAuthorityId;

    private LocalDate identityDocumentValidityPeriod;

    private Long identityDocumentScanFrontId;

    private Long identityDocumentScanBackId;

    private Long form086Id;

}
