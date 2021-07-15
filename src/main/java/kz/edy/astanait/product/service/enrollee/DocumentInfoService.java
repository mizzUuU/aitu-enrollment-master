package kz.edy.astanait.product.service.enrollee;

import kz.edy.astanait.product.dto.requestDto.enrollee.DocumentInfoDtoRequest;
import kz.edy.astanait.product.dto.responseDto.enrollee.AdmissionInfoDtoResponse;
import kz.edy.astanait.product.dto.responseDto.enrollee.DocumentInfoDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.DocumentIssuingAuthorityDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.FileTypeDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.IdentityDocumentTypeDtoResponse;
import kz.edy.astanait.product.exception.domain.AdmissionInfoNotFoundException;
import kz.edy.astanait.product.exception.domain.DocumentInfoNotFoundException;
import kz.edy.astanait.product.model.document.DocumentIssuingAuthority;
import kz.edy.astanait.product.model.document.FileType;
import kz.edy.astanait.product.model.document.IdentityDocumentType;
import kz.edy.astanait.product.model.document.UniversityFile;
import kz.edy.astanait.product.model.enrollee.AdmissionInfo;
import kz.edy.astanait.product.model.enrollee.DocumentInfo;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.repository.document.DocumentIssuingAuthorityRepository;
import kz.edy.astanait.product.repository.document.FileTypeRepository;
import kz.edy.astanait.product.repository.document.IdentityDocumentTypeRepository;
import kz.edy.astanait.product.repository.document.UniversityFileRepository;
import kz.edy.astanait.product.repository.entrollee.DocumentInfoRepository;
import kz.edy.astanait.product.repository.secretary.ConfirmBlocksRepository;
import kz.edy.astanait.product.utils.facade.enrollee.AdmissionInfoFacade;
import kz.edy.astanait.product.utils.facade.enrollee.DocumentInfoFacade;
import kz.edy.astanait.product.utils.facade.document.DocumentIssuingAuthorityFacade;
import kz.edy.astanait.product.utils.facade.document.FileTypeFacade;
import kz.edy.astanait.product.utils.facade.document.IdentityDocumentTypeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentInfoService {
    private final DocumentInfoRepository documentInfoRepository;
    private final UserRepository userRepository;
    private final DocumentIssuingAuthorityRepository documentIssuingAuthorityRepository;
    private final IdentityDocumentTypeRepository identityDocumentTypeRepository;
    private final UniversityFileRepository universityFileRepository;
    private final FileTypeRepository fileTypeRepository;
    private final ConfirmBlocksRepository confirmBlocksRepository;

    @Autowired
    public DocumentInfoService(DocumentInfoRepository documentInfoRepository, UserRepository userRepository,
                               DocumentIssuingAuthorityRepository documentIssuingAuthorityRepository,
                               IdentityDocumentTypeRepository identityDocumentTypeRepository, UniversityFileRepository universityFileRepository, FileTypeRepository fileTypeRepository, ConfirmBlocksRepository confirmBlocksRepository) {
        this.documentInfoRepository = documentInfoRepository;
        this.userRepository = userRepository;
        this.documentIssuingAuthorityRepository = documentIssuingAuthorityRepository;
        this.identityDocumentTypeRepository = identityDocumentTypeRepository;
        this.universityFileRepository = universityFileRepository;
        this.fileTypeRepository = fileTypeRepository;
        this.confirmBlocksRepository = confirmBlocksRepository;
    }

    public DocumentInfoDtoResponse getBLock2ByOne(Long id) {
        Optional<DocumentInfo> documentInfo = documentInfoRepository.findByUserId(id);
        if (documentInfo.isPresent()) {
            return new DocumentInfoFacade().documentDtoToDocumentResponse(documentInfo.get());
        }
        throw new DocumentInfoNotFoundException("Document info not found by user id: " + id);
    }

    public DocumentInfoDtoResponse getBlock2Documents(Principal principal) {
        Optional<DocumentInfo> document = documentInfoRepository.findByUserEmail(principal.getName());
        return document.map(value -> new DocumentInfoFacade().documentDtoToDocumentResponse(value)).orElse(null);
    }

    public void saveBlock2Documents(DocumentInfoDtoRequest documentsRequestDto, Principal principal) throws Exception {
        Optional<DocumentInfo> document = documentInfoRepository.findByUserEmail(principal.getName());
        if (document.isPresent()) {
            confirmBlocksRepository.findByUserEmail(principal.getName()).ifPresent(value -> {
                if (!value.isSecondBlock()) {
                    manipulateBlock2Documents(document.get(), documentsRequestDto, principal.getName());
                }});
        } else {
            DocumentInfo createDocumentInfo = new DocumentInfo();
            manipulateBlock2Documents(createDocumentInfo, documentsRequestDto, principal.getName());
        }
    }

    private void manipulateBlock2Documents(DocumentInfo documentInfo, DocumentInfoDtoRequest documentInfoDtoRequest, String email){
        userRepository.findUserByEmail(email).ifPresent(documentInfo::setUser);
        if (documentInfoDtoRequest.getIdentityDocumentTypeId() != null) {
            identityDocumentTypeRepository.findById(documentInfoDtoRequest.getIdentityDocumentTypeId()).ifPresent(documentInfo::setIdentityDocumentType);
        }
        if (documentInfoDtoRequest.getOtherDocumentType() != null) {
            documentInfo.setOtherDocumentType(documentInfoDtoRequest.getOtherDocumentType());
        }
        if (documentInfoDtoRequest.getIdentityDocumentNumber() != null) {
            documentInfo.setIdentityDocumentNumber(documentInfoDtoRequest.getIdentityDocumentNumber());
        }
        if (documentInfoDtoRequest.getIdentityDocumentIssueDate() != null) {
            documentInfo.setIdentityDocumentIssueDate(documentInfoDtoRequest.getIdentityDocumentIssueDate());
        }
        if (documentInfoDtoRequest.getIdentityDocumentValidityPeriod() != null) {
            documentInfo.setIdentityDocumentValidityPeriod(documentInfoDtoRequest.getIdentityDocumentValidityPeriod());
        }
        if (documentInfoDtoRequest.getDocumentIssuingAuthorityId() != null) {
            documentIssuingAuthorityRepository.findById(documentInfoDtoRequest.getDocumentIssuingAuthorityId()).ifPresent(documentInfo::setDocumentIssuingAuthority);
        }
        if (documentInfoDtoRequest.getIdentityDocumentScanFrontId() != null) {
            Optional<UniversityFile> universityFile = universityFileRepository.findById(documentInfoDtoRequest.getIdentityDocumentScanFrontId());
            if (universityFile.isPresent() && universityFile.get().getUser().getEmail().equals(email)) {
                documentInfo.setIdentityDocumentScanFront(universityFile.get());
            }
        }
        if (documentInfoDtoRequest.getIdentityDocumentScanBackId() != null) {
            Optional<UniversityFile> universityFile = universityFileRepository.findById(documentInfoDtoRequest.getIdentityDocumentScanBackId());
            if (universityFile.isPresent() && universityFile.get().getUser().getEmail().equals(email)) {
                documentInfo.setIdentityDocumentScanBack(universityFile.get());
            }
        }
        if (documentInfoDtoRequest.getForm086Id() != null) {
            Optional<UniversityFile> universityFile = universityFileRepository.findById(documentInfoDtoRequest.getForm086Id());
            if (universityFile.isPresent() && universityFile.get().getUser().getEmail().equals(email)) {
                documentInfo.setForm086(universityFile.get());
            }
        }
        documentInfoRepository.save(documentInfo);
    }


    public List<DocumentIssuingAuthorityDtoResponse> getDocumentIssuingAuthorities() {
        List<DocumentIssuingAuthority> documentIssuingAuthority = documentIssuingAuthorityRepository.findAll();
        List<DocumentIssuingAuthorityDtoResponse> documentIssuingAuthorityDtoResponses = new ArrayList<>();
        documentIssuingAuthority.forEach(value -> documentIssuingAuthorityDtoResponses.add(new DocumentIssuingAuthorityFacade().documentIssuingAuthorityToDocumentIssuingAuthorityDtoResponse(value)));
        return documentIssuingAuthorityDtoResponses;
    }

    public List<IdentityDocumentTypeDtoResponse> getIdentityDocumentTypes() {
        List<IdentityDocumentType> identityDocumentTypes = identityDocumentTypeRepository.findAll();
        List<IdentityDocumentTypeDtoResponse> identityDocumentTypeDtoResponses = new ArrayList<>();
        identityDocumentTypes.forEach(value -> identityDocumentTypeDtoResponses.add(new IdentityDocumentTypeFacade().identityDocumentTypeToIdentityDocumentTypeDtoResponse(value)));
        return identityDocumentTypeDtoResponses;
    }

    public List<FileTypeDtoResponse> getFileTypes() {
        List<FileType> fileTypes = fileTypeRepository.findAll();
        List<FileTypeDtoResponse> fileTypeDtoResponses = new ArrayList<>();
        fileTypes.forEach(value -> fileTypeDtoResponses.add(new FileTypeFacade().fileTypeToFileTypeDtoResponse(value)));
        return fileTypeDtoResponses;
    }
}
