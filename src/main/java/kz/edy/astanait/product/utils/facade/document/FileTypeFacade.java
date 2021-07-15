package kz.edy.astanait.product.utils.facade.document;

import kz.edy.astanait.product.dto.responseDto.document.FileTypeDtoResponse;
import kz.edy.astanait.product.model.document.FileType;

public class FileTypeFacade {

    public FileTypeDtoResponse fileTypeToFileTypeDtoResponse(FileType fileType) {
        FileTypeDtoResponse fileTypeDtoResponse = new FileTypeDtoResponse();
        fileTypeDtoResponse.setId(fileType.getId());
        fileTypeDtoResponse.setName(fileType.getName());
        return fileTypeDtoResponse;
    }
}
