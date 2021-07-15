package kz.edy.astanait.product.utils.facade.document;

import kz.edy.astanait.product.dto.responseDto.document.UniversityFileDtoResponse;
import kz.edy.astanait.product.model.document.UniversityFile;
import kz.edy.astanait.product.utils.facade.UserFacade;

public class UniversityFileFacade {

    public UniversityFileDtoResponse universityFileToUniversityFileDtoResponse(UniversityFile universityFile) {
        UniversityFileDtoResponse universityFileDtoResponse = new UniversityFileDtoResponse();
        universityFileDtoResponse.setId(universityFile.getId());
        universityFileDtoResponse.setPath(universityFile.getPath());
        if (universityFile.getFileType() != null) {
            universityFileDtoResponse.setFileType(new FileTypeFacade().fileTypeToFileTypeDtoResponse(universityFile.getFileType()));
        }
        universityFileDtoResponse.setUserDtoResponse(new UserFacade().userToUserDtoResponse(universityFile.getUser()));
        return universityFileDtoResponse;
    }
}
