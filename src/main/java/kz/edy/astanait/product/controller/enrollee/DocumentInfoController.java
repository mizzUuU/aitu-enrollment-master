package kz.edy.astanait.product.controller.enrollee;

import kz.edy.astanait.product.dto.requestDto.enrollee.DocumentInfoDtoRequest;
import kz.edy.astanait.product.dto.responseDto.enrollee.DocumentInfoDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.DocumentIssuingAuthorityDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.FileTypeDtoResponse;
import kz.edy.astanait.product.dto.responseDto.document.IdentityDocumentTypeDtoResponse;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.service.enrollee.DocumentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentInfoController extends ExceptionHandling {

    private final DocumentInfoService documentInfoService;

    @Autowired
    public DocumentInfoController(DocumentInfoService documentInfoService) {
        this.documentInfoService = documentInfoService;
    }

    @GetMapping("/")
    public ResponseEntity<DocumentInfoDtoResponse> getBlock2Documents(Principal principal) {
        DocumentInfoDtoResponse documentInfoDtoResponse = documentInfoService.getBlock2Documents(principal);
        return new ResponseEntity<>(documentInfoDtoResponse, OK);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<DocumentInfoDtoResponse> getBlock2DocumentsByOne(@RequestParam(name = "user_id") Long user_id) {
        DocumentInfoDtoResponse documentInfoDtoResponse = documentInfoService.getBLock2ByOne(user_id);
        return new ResponseEntity<>(documentInfoDtoResponse, OK);
    }

    @PostMapping("/save-document/block/2")
    public ResponseEntity<HttpStatus> saveDocuments(@RequestBody DocumentInfoDtoRequest documentInfoDtoRequest,
                                                    Principal principal) throws Exception {
        documentInfoService.saveBlock2Documents(documentInfoDtoRequest, principal);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/document-issuing-authorities")
    public ResponseEntity<List<DocumentIssuingAuthorityDtoResponse>> getDocumentIssuingAuthorities(){
        List<DocumentIssuingAuthorityDtoResponse> documentIssuingAuthorityDtoResponses = documentInfoService.getDocumentIssuingAuthorities();
        return new ResponseEntity<>(documentIssuingAuthorityDtoResponses, OK);
    }

    @GetMapping("/identity-document-types")
    public ResponseEntity<List<IdentityDocumentTypeDtoResponse>> getIdentityDocumentTypes(){
        List<IdentityDocumentTypeDtoResponse> identityDocumentTypeDtoResponses = documentInfoService.getIdentityDocumentTypes();
        return new ResponseEntity<>(identityDocumentTypeDtoResponses, OK);
    }

    @GetMapping("/file-types")
    public ResponseEntity<List<FileTypeDtoResponse>> getFileTypes(){
        List<FileTypeDtoResponse> fileTypeDtoResponses = documentInfoService.getFileTypes();
        return new ResponseEntity<>(fileTypeDtoResponses, OK);
    }
}
