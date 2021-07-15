package kz.edy.astanait.product.controller.document;

import kz.edy.astanait.product.dto.requestDto.document.UniversityFileDtoRequest;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.model.document.UniversityFile;
import kz.edy.astanait.product.service.document.FileService;
import kz.edy.astanait.product.service.document.UniversityFileService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/file")
public class FileController extends ExceptionHandling {

    private final FileService fileService;
    private final UniversityFileService universityFileService;

    @Autowired
    public FileController(FileService fileService, UniversityFileService universityFileService) {
        this.fileService = fileService;
        this.universityFileService = universityFileService;
    }


    @PostMapping("/upload-document")
    public ResponseEntity<?> uploadDocument(@RequestParam("type") String requestParamType,
                                            MultipartFile documentFile,
                                            Principal principal) throws IOException {
        UniversityFile universityFile = fileService.saveDocuments(principal, requestParamType, documentFile);
        return new ResponseEntity<>(universityFile.getId(), OK);
    }

    @GetMapping("/get-document")
    public ResponseEntity<?> getDocument(@RequestParam("id") Long universityFileId, Principal principal) throws Exception {
        UniversityFile universityFile = universityFileService.findUniversityFileById(universityFileId);
        fileService.isValidUserForGetFile(universityFile, principal);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Extension", universityFile.getPath().substring(universityFile.getPath().lastIndexOf('.') + 1));
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "text/plain");
        return new ResponseEntity<>(fileService.getBase64Image(universityFile.getPath()), httpHeaders, HttpStatus.OK);
    }
}
