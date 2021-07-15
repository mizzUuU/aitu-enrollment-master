package kz.edy.astanait.product.service.document;

import kz.edy.astanait.product.constant.FileTypeRequestParamConstant;
import kz.edy.astanait.product.exception.domain.UserNotFoundException;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.document.FileType;
import kz.edy.astanait.product.model.document.UniversityFile;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.repository.document.FileTypeRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import javax.persistence.NoResultException;
import java.io.*;
import java.security.Principal;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Value("${file.upload-dir}")
    private String STORAGE_PATH;

    private final FileTypeRepository fileTypeRepository;
    private final UserRepository userRepository;
    private final UniversityFileService universityFileService;

    @Autowired
    public FileService(FileTypeRepository fileTypeRepository, UniversityFileService universityFileService, UserRepository userRepository) {
        this.fileTypeRepository = fileTypeRepository;
        this.universityFileService = universityFileService;
        this.userRepository = userRepository;
    }

    private String createFile(String prePath, MultipartFile multipartFile, String renameFile) throws IOException {
        File file = new File(prePath);
        file.mkdirs();

        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        prePath = prePath + (renameFile + fileName.substring(fileName.lastIndexOf('.')));

        file = new File(prePath);
        file.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }

        return prePath;
    }

    private void deleteFileIfExists(Principal principal, FileType fileType) {
        String path = STORAGE_PATH + principal.getName() + File.separator + "Documents" + File.separator;
        File file = new File(path);
        if (file.exists()) {
            for (File inDirectoryFile : file.listFiles()) {
                if (inDirectoryFile.getName().startsWith(fileType.getName())) {
                    inDirectoryFile.delete();
                }
            }
        }
    }

    public UniversityFile saveDocuments(Principal principal,
                                        String requestParamType,
                                        MultipartFile multipartFile) throws IOException {
        Optional<FileType> fileType = fileTypeRepository.findByRequestParamType(requestParamType);
        if (fileType.isEmpty()) throw new NoResultException("No file type was found by param: " + requestParamType);
        String contentType = multipartFile.getContentType();
        if (Strings.isNotBlank(contentType)) {
            if (validateFileTypeAndContentType(requestParamType, contentType)) {
                deleteFileIfExists(principal, fileType.get());
                String prePath = STORAGE_PATH + principal.getName() + File.separator + "Documents" + File.separator;
                String fullPath = createFile(prePath, multipartFile, fileType.get().getName());

                Optional<User> optionalUser = userRepository.findUserByEmail(principal.getName());
                if (optionalUser.isPresent()) {
                    return universityFileService.saveUniversityFile(optionalUser.get(), fileType.get(), fullPath);
                }
                throw new UserNotFoundException("User not found by email: " + principal.getName());
            }
        }
        throw new UnsupportedMediaTypeStatusException("Unsupported content type!");
    }

    public List<Integer> getFileBytes(String path) throws IOException {
        List<Integer> bytes = new LinkedList<>();
        try (FileInputStream fis = new FileInputStream(path)) {
            int read;
            while ((read = fis.read()) != -1) {
                bytes.add(read);
            }
        }
        return bytes;
    }

    public String getBase64Image(String path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path)) {
            return Base64.getEncoder().encodeToString(fis.readAllBytes());
        }
    }

    public boolean isImage(UniversityFile universityFile) {
        String extension = universityFile.getPath().substring(universityFile.getPath().lastIndexOf('.') + 1);
        return extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg"); // else PDF
    }
    public boolean isValidUserForGetFile(UniversityFile universityFile, Principal principal) throws Exception {
        Optional<User> optionalUser = userRepository.findUserByEmail(principal.getName());
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getRole().getRoleName().equals("ROLE_USER")) {
                if(universityFile.getUser().getId().equals(optionalUser.get().getId())){
                    return true;
                }
                throw new Exception("Not allowed to get this file.");
            } else {
                return true;
            }
        } else {
            throw new UserNotFoundException("User not found by email: " + principal.getName());
        }
    }

    private boolean validateFileTypeAndContentType(String requestParamType, String contentType) {
        if (FileTypeRequestParamConstant.IMAGE_3x4.equals(requestParamType)) {
            return contentType.equalsIgnoreCase(MimeTypeUtils.IMAGE_JPEG_VALUE)
                    || contentType.equalsIgnoreCase(MimeTypeUtils.IMAGE_PNG_VALUE);
        } else if (FileTypeRequestParamConstant.IDENTITY_FRONT.equals(requestParamType)
                || FileTypeRequestParamConstant.IDENTITY_BACK.equals(requestParamType)
                || FileTypeRequestParamConstant.FORM_086.equals(requestParamType)
                || FileTypeRequestParamConstant.ENT_CERTIFICATE.equals(requestParamType)
                || FileTypeRequestParamConstant.ENGLISH_CERTIFICATE.equals(requestParamType)
                || FileTypeRequestParamConstant.GRADUATION_CERTIFICATE.equals(requestParamType)
                || FileTypeRequestParamConstant.GRADUATION_CERTIFICATE_APPLICATION.equals(requestParamType)) {
            return contentType.equalsIgnoreCase(MimeTypeUtils.IMAGE_JPEG_VALUE)
                    || contentType.equalsIgnoreCase(MimeTypeUtils.IMAGE_PNG_VALUE)
                    || contentType.equalsIgnoreCase("application/pdf");
        }
        throw new UnsupportedMediaTypeStatusException("Content type " + contentType + " is not supported for file type " + requestParamType);
    }
}
