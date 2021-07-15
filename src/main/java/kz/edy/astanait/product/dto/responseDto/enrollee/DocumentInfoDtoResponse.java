package kz.edy.astanait.product.dto.responseDto.enrollee;

import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.DocumentIssuingAuthorityDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.IdentityDocumentTypeDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.UniversityFileDtoResponse;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Date;

@Data
public class DocumentInfoDtoResponse {
    private Long id;

    private UserDtoResponse user;

    private IdentityDocumentTypeDtoResponse identityDocumentType;

    private String otherDocumentType;

    private String identityDocumentNumber;

    private LocalDate identityDocumentIssueDate;

    private DocumentIssuingAuthorityDtoResponse documentIssuingAuthority;

    private LocalDate identityDocumentValidityPeriod;

    private UniversityFileDtoResponse identityDocumentScanFront;

    private UniversityFileDtoResponse identityDocumentScanBack;

    private UniversityFileDtoResponse form086;
}
